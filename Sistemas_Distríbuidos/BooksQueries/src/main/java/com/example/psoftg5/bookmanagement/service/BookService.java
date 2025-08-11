package com.example.psoftg5.bookmanagement.service;

import com.example.psoftg5.bookmanagement.api.CreateBookRequest;
import com.example.psoftg5.bookmanagement.model.Author;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.bookmanagement.repositories.AuthorDBRepository;
import com.example.psoftg5.bookmanagement.repositories.BookDBRepository;
import com.example.psoftg5.bookmanagement.repositories.GenreRepository;
import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDBRepository bookRepository;
    private final BookRepository repository;
    private final AuthorDBRepository authorRepository;
    private final GenreRepository genreRepository;
    private final AuthorDBRepository authorDBRepository;


    private static final Pattern IMAGE_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(jpe?g|png))$)");

    public Optional<Book> findByIsbn(String isbn) {

        return repository.findBookByIsbn(isbn);
    }

    public Optional<Book> findByTitle(String title) {
        return repository.findBookByTitle(title);
    }

    public List<Book> searchBooksByGenre(Genre genreName) {
        return bookRepository.findByGenre(genreName);
    }

   /* public String searchBooksByTitleStartingWith(String title, boolean internal) throws Exception {
        return repository.getBooksByName(title, internal);
    }*/


    public List<String> getTop5Genres() {
        List<Book> books = StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Map<Genre, Long> genreCounts = books.stream()
                .collect(Collectors.groupingBy(Book::getGenres, Collectors.counting()));

        List<String> top5Genres = genreCounts.entrySet().stream()
                .sorted(Map.Entry.<Genre, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> entry.getKey().getGenreName())
                .collect(Collectors.toList());

        return top5Genres;
    }

    public Book saveBook(final String bookJson) {
        Gson gson = new Gson();
        Book book = gson.fromJson(bookJson, Book.class);


        Set<Author> authors = new HashSet<>();
        for (Author auth: book.getAuthors()) {
            Optional<Author> opt = authorDBRepository.findAuthorByNameAndShortBio(auth.getName(), auth.getShortBio());
            opt.ifPresent(authors::add);
        }

        book.setAuthors(authors);

        try {
            return bookRepository.save(book);
        } catch (DuplicatedDataException e) {
            return null;
        }

    }

    public Genre saveGenre(final String json) {
        Gson gson = new Gson();
        Genre genre = gson.fromJson(json, Genre.class);

        try {
            return genreRepository.save(genre);
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

}

