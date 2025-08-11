package com.example.psoftg5.bookmanagement.service;


import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findBookByIsbn(String isbn);

    Optional<Book> findBookByTitle(String title);

    List<Book> findBooksByGenre(Genre genre);

   // Optional<Object> updateBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto, boolean internal) throws Exception;

    //Optional<Book> registerBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto) throws Exception;

   // List<Book> getAllBooks();

    //String getBooksByName(String name, boolean internal) throws Exception;
}
