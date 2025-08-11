package com.example.psoftg5.bookmanagement.service;


import com.example.psoftg5.bookmanagement.api.CreateBookAllRequest;
import com.example.psoftg5.bookmanagement.model.Author;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.bookmanagement.model.TempBook;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Object> findBookByIsbn(String isbn);

   // Optional<Object> updateBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto, boolean internal) throws Exception;

    //Optional<Book> registerBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto);

    Book registerBook(Book book);

    TempBook saveTempBook(CreateBookAllRequest bookAllRequest);
    //Author save(Author author);

    //List<Book> getAllBooks();

}
