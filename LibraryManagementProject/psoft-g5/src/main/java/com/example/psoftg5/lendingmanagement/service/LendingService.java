package com.example.psoftg5.lendingmanagement.service;

import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.repositories.BookRepository;
import com.example.psoftg5.exceptions.ConflictException;
import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.lendingmanagement.model.Lending;
import com.example.psoftg5.lendingmanagement.repository.LendingRepository;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LendingService {

    @Autowired
    private LendingRepository lendingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final int NUMBER_OF_DAYS_TILL_RETURN = 14;

    public Lending lendBook(String bookTitle, Long readerNumber, LocalDate lendingDate) {
        User user = userRepository.findByReaderNumber(readerNumber)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + readerNumber));

        List<Lending> userLendings = lendingRepository.findAllByUser(user);
        if (userLendings.stream().anyMatch(Lending::isOverdue)) {
            throw new ConflictException("User has overdue books");
        }

        if (userLendings.size() >= 3) {
            throw new ConflictException("User already has 3 books lent");
        }

        Book book = bookRepository.findByTitle(bookTitle)
                .orElseThrow(() -> new NotFoundException("Book not found with title: " + bookTitle));

        Lending lending = new Lending(book, user, lendingDate, lendingDate.plusDays(NUMBER_OF_DAYS_TILL_RETURN));
        lending = lendingRepository.save(lending);

        user.incrementBorrowCount();
        userRepository.save(user);

        return lending;
    }

    public Lending returnBook(Long lendingNumber, LocalDate returnDate) {
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
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())) // Ordem decrescente
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
    }
}
