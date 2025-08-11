package com.example.psoftg5.bookmanagement.service;


import com.example.psoftg5.bookmanagement.api.BookDTOMapper;
import com.example.psoftg5.bookmanagement.api.CreateBookAllRequest;
import com.example.psoftg5.bookmanagement.api.CreateBookRequest;
import com.example.psoftg5.bookmanagement.api.NewAuthorAndBookRequest;
import com.example.psoftg5.bookmanagement.model.*;
import com.example.psoftg5.bookmanagement.repositories.*;
import com.example.psoftg5.bookmanagement.repositories.Rabbit.BookSender;
import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDBRepository bookRepository;
    private final AuthorDBRepository authorRepository;
    private final BookRepository repository;
    private final BookDTOMapper mapper;

    private static final Pattern IMAGE_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(jpe?g|png))$)");
    private final BookSender bookSender;
    private final TempAuthorRepository tempAuthorRepository;
    private final TempBookRepository tempBookRepository;
    private final AuthorDBRepository authorDBRepository;
    private final GenreRepository genreRepository;
    private final BookDBRepository bookDBRepository;

    public Book registerBook(final CreateBookRequest resource) {
        final Book book = mapper.registerBook(resource);
        bookSender.sendCreatedBook(book);
        return repository.registerBook(book);
    }

    /*public Optional<Object> updateBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto, boolean internal) throws Exception {
        return repository.updateBook(isbn, title, description, genre, authors, coverPhoto, internal);
    }*/


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
    public TempBook saveBookAndAuthor(final CreateBookAllRequest resource){
        Optional<Book> book = bookDBRepository.findByIsbn(resource.getIsbn());
        if (book.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book already exists.");
        }
        TempBook tempBook = repository.saveTempBook(resource);
        NewAuthorAndBookRequest request = new NewAuthorAndBookRequest(resource.getIsbn(), resource.getAuthors());
        bookSender.sendCreatedAuthor(request);
        return tempBook;
    }


    public Book saveTempBook(final String isbn) {
        Gson gson = new Gson();
        Optional<TempBook> optionalTempBook = tempBookRepository.findBooksByIsbn(isbn);

        if (optionalTempBook.isEmpty()) {
            return null;
        }

        TempBook tempBook = optionalTempBook.get();
        List<String> authors = new ArrayList<>();
        Set<Author> authorSet = new HashSet<>();
        for (TempAuthor tempAuthor : tempBook.getAuthors()) {
            try {
                authorSet.add(authorDBRepository.findAuthorByNameAndShortBio(tempAuthor.getName(), tempAuthor.getShortBio()).get());
                authors.add(tempAuthor.getName());
            } catch (Exception e) {
                return null;
            }
        }
        Genre genre = new Genre();
        Optional<Genre> optGenre = genreRepository.findByGenreName(tempBook.getGenres());

        if (optGenre.isEmpty()) {
            Genre newGenre = new Genre(tempBook.getGenres());
            genre = genreRepository.save(newGenre);
            bookSender.sendCreatedGenre(newGenre);
        } else {
            genre = optGenre.get();
        }

        CreateBookRequest request = new CreateBookRequest(tempBook.getIsbn(),tempBook.getTitle(), tempBook.getDescription(), genre, authors,tempBook.getCoverPhoto());


        Book book = new Book(tempBook.getIsbn(), tempBook.getTitle(), tempBook.getDescription(), genre, authorSet, tempBook.getDescription());
        book = bookRepository.save(book);
        bookSender.sendTempBook(tempBook);

        tempBookRepository.delete(tempBook);
        tempAuthorRepository.deleteAll(tempBook.getAuthors());

        return book;
    }
}

