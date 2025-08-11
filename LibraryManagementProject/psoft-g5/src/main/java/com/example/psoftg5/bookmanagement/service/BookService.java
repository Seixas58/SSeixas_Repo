package com.example.psoftg5.bookmanagement.service;

import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.authormanagement.repositories.AuthorRepository;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private static final Pattern IMAGE_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(jpe?g|png))$)");
    public Book registerBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto) {
        Set<Author> authorsSet = new HashSet<>();

        for (String i : authors) {
            List<Author> jpa = authorRepository.findAuthorsByName(i);
            if (jpa.isEmpty()) {
                continue;
            }
            for (Author entity : jpa) {
                authorsSet.add(entity);
            }
        }
        if (authorsSet.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Does Not Exist");
        }
        Book book = new Book(isbn, title, description, genre, authorsSet, coverPhoto);
        return bookRepository.save(book);
    }

    public Book updateBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto) {
        Set<Author> authorsSet = new HashSet<>();

        for (String i : authors) {
            List<Author> jpa = authorRepository.findAuthorsByName(i);
            if (jpa.isEmpty()) {
                continue;
            }
            for (Author entity : jpa) {
                authorsSet.add(entity);
            }
        }
        if (authorsSet.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author Does Not Exist");
        }
        Optional<Book> existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(title);
            book.setDescription(description);
            book.setGenres(genre);
            book.setAuthors(authorsSet);
            book.setCoverPhoto(coverPhoto);
            return bookRepository.save(book);
        }
        return null;
    }

    public Book getBookByIsbn(String isbn) {
        Optional<Book> existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook.isPresent()) {
            return existingBook.get();
        }
        return null;
    }

    public List<Book> searchBooksByGenre(Genre genreName) {
        return bookRepository.findByGenre(genreName);
    }

    public List<Book> searchBooksByTitleStartingWith(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }
    private void validateCoverPhoto(String coverPhoto) {
        if (coverPhoto == null || coverPhoto.isEmpty() || !IMAGE_PATTERN.matcher(coverPhoto).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cover photo must be a valid JPEG or PNG image URL.");
        }

        try {
            if (getFileSize(coverPhoto) > 20 * 1024) { // 20 KB
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cover photo must be at most 20 KB.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error reading cover photo size.");
        }
    }

    private long getFileSize(String coverPhotoUrl) throws IOException {
        URL url = new URL(coverPhotoUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("HEAD");
        conn.getInputStream();
        return conn.getContentLengthLong();
    }
    public List<String> getTopGenres() {
        // Converte Iterable para List
        List<Book> books = StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        // Contar a ocorrência de cada gênero
        Map<Genre, Long> genreCounts = books.stream()
                .collect(Collectors.groupingBy(Book::getGenres, Collectors.counting()));

        // Ordenar os gêneros pelo número de livros
        List<String> topGenres = genreCounts.entrySet().stream()
                .sorted(Map.Entry.<Genre, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> entry.getKey().getGenreName())
                .collect(Collectors.toList());

        return topGenres;
    }
}

