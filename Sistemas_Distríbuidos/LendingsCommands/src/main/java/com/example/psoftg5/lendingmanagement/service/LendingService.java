package com.example.psoftg5.lendingmanagement.service;


import com.example.psoftg5.exceptions.ConflictException;
import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.lendingmanagement.api.BookDTO;
import com.example.psoftg5.lendingmanagement.api.CreateLendingRequest;
import com.example.psoftg5.lendingmanagement.model.Author;
import com.example.psoftg5.lendingmanagement.model.Book;
import com.example.psoftg5.lendingmanagement.model.Lending;
import com.example.psoftg5.lendingmanagement.model.User;
import com.example.psoftg5.lendingmanagement.repository.AuthorDBRepository;
import com.example.psoftg5.lendingmanagement.repository.BookDBRepository;
import com.example.psoftg5.lendingmanagement.repository.LendingRepository;
import com.example.psoftg5.lendingmanagement.repository.UserDBRepository;
import com.example.psoftg5.lendingmanagement.repository.rabbit.LendingSender;
import com.example.psoftg5.utils.Utils;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.psoftg5.exceptions.DuplicatedDataException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LendingService {

    @Autowired
    private LendingRepository lendingRepository;

   /* @Autowired
    private UserRepository userRepository;*/

    @Autowired
    private BookDBRepository bookRepository;
    @Autowired
    private AuthorDBRepository authorRepository;

    @Autowired
    private UserDBRepository userRepository;

    @Autowired
    private LendingSender sender;




    private static final int NUMBER_OF_DAYS_TILL_RETURN = 14;

    public Lending lendBook(String username, CreateLendingRequest body) {

        List<Lending> userLendings = lendingRepository.findAllByUsername(username);
        userLendings = userLendings.stream()
                .filter(lending -> lending.getReturnDate() == null || lending.getReturnDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        if (userLendings.size() >= 3) {
            throw new ConflictException("User already has 3 books lent");
        }

        Optional<Book> book = bookRepository.findByIsbn(body.getIsbn());
        if (book.isEmpty()) {
            throw new EntityNotFoundException("Book not found with ISBN: " + body.getIsbn());
        }

        Lending lending = new Lending();
        lending.setISBN(body.getIsbn());
        lending.setUsername(username);  // Associa o username (readerNumber)
        lending.setLendingDate(body.getLendingDate());
        lending.setReturnDate(body.getLendingDate().plusDays(NUMBER_OF_DAYS_TILL_RETURN));

        long lendingNumber = System.currentTimeMillis();  // Ou alguma outra lógica para gerar o número
        lending.setLendingNumber(lendingNumber);

        lending = lendingRepository.save(lending);
        sender.sendLending(lending);
        return lending;
    }
    public Book saveBook(final String bookJson) {
        Gson gson = new Gson();
        Book book = gson.fromJson(bookJson, Book.class);


        try {
            return bookRepository.save(book);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }
    public Author saveAuthor(final String authorJson) {
        Gson gson = new Gson();
        Author author = gson.fromJson(authorJson, Author.class);

        try {
            return authorRepository.save(author);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }

    public User saveUser(final String userJson) {
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);

        try {
            return userRepository.save(user);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }

    public Lending saveLending(final String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, Utils.createLocalDateTypeAdapter())
                .create();
        Lending lending = gson.fromJson(json, Lending.class);

        try {
            return lendingRepository.save(lending);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }

    /*public Lending returnBook(Long lendingNumber, LocalDate returnDate) {
        Lending lending = lendingRepository.findByLendingNumber(lendingNumber)
                .orElseThrow(() -> new NotFoundException("Lending not found with number: " + lendingNumber));

        lending.setReturnDate(returnDate);

        return lendingRepository.save(lending);
    }

    public Lending getLendingDetails(Long lendingNumber) {
        return lendingRepository.findByLendingNumber(lendingNumber)
                .orElseThrow(() -> new NotFoundException("Lending not found with number: " + lendingNumber));
    }

    public List<String> getTop5LentBookTitles() {
        List<Lending> allLendings = (List<Lending>) lendingRepository.findAll();

        Map<String, Long> lendingsByBookTitle = allLendings.stream()
                .collect(Collectors.groupingBy(l -> l.getBook().getTitle(), Collectors.counting()));

        List<String> top5BookTitles = lendingsByBookTitle.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return top5BookTitles;
    }

    public List<User> getTop5Readers() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        List<User> top5Readers = allUsers.stream()
                .sorted((u1, u2) -> Long.compare(u2.getBorrowCount(), u1.getBorrowCount()))
                .limit(5)
                .collect(Collectors.toList());

        return top5Readers;
    }
    public double getAverageLendingDuration() {
        List<Lending> allLendings = (List<Lending>) lendingRepository.findAll();

        long totalDuration = allLendings.stream()
                .mapToLong(l -> ChronoUnit.DAYS.between(l.getLendingDate(), l.getReturnDate()))
                .sum();

        double averageDuration = (double) totalDuration / allLendings.size();

        return averageDuration;
    }
    public Map<String, Double> getAverageLendingPerGenre(int month, int year) {
        List<Lending> lendings = lendingRepository.findAllByMonthAndYear(month, year);

        Map<String, Long> lendingsByGenre = lendings.stream()
                .collect(Collectors.groupingBy(l -> l.getBook().getGenres().getGenreName(), Collectors.counting()));

        long totalLendings = lendings.size();

        return lendingsByGenre.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / totalLendings
                ));
    }
    public Map<YearMonth, Map<String, Long>> getLendingsPerMonthByGenre() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(12).withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        List<Lending> lendings = lendingRepository.findAllBetweenDates(startDate, endDate);

        return IntStream.range(0, 12)
                .mapToObj(i -> YearMonth.from(now.minusMonths(i)))
                .collect(Collectors.toMap(
                        yearMonth -> yearMonth,
                        yearMonth -> lendings.stream()
                                .filter(l -> YearMonth.from(l.getLendingDate()).equals(yearMonth))
                                .collect(Collectors.groupingBy(
                                        l -> l.getBook().getGenres().getGenreName(),
                                        Collectors.counting()
                                ))
                ));
    }
    public Map<User, Long> getMonthlyLendingPerReader(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = lendingRepository.findLendingCountPerUserBetweenDates(startDate, endDate);
        return results.stream().collect(Collectors.toMap(
                result -> (User) result[0],
                result -> (Long) result[1]
        ));
    }*/
}
