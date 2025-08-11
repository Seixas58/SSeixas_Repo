package com.example.psoftg5.bookmanagement.repositories;


import com.example.psoftg5.bookmanagement.api.*;
import com.example.psoftg5.bookmanagement.model.*;
import com.example.psoftg5.bookmanagement.service.BookRepository;
import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.example.psoftg5.exceptions.NotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookDBRepository dbRepository;
    private final AuthorDBRepository authorRepository;
    private final BookDTOMapper bookDTOMapper;
    private final TempBookRepository tempBookRepository;
    private final TempAuthorRepository tempAuthorRepository;

    @Override
    public Optional<Object> findBookByIsbn(String isbn) {
        Optional<Book> book = dbRepository.findByIsbn(isbn);

        if (book.isPresent()){
            return Optional.of(book.get());
        }
        throw new NotFoundException(Book.class, isbn);
    }

   /* @Override
    public Optional<Object> updateBook(String isbn, String title, String description, Genre genre, List<String> authors, String coverPhoto, boolean internal) throws Exception {
        if (internal) {
            Set<Author> authorsSet = authorRepository.createAuthorHash(authors);

            Optional<Book> existingBook = dbRepository.findByIsbn(isbn);

            if (existingBook.isPresent()) {
                Book book = existingBook.get();
                book.setTitle(title);
                book.setDescription(description);
                book.setGenres(genre);
                book.setAuthors(authorsSet);
                book.setCoverPhoto(coverPhoto);
                return Optional.of(dbRepository.save(book));
            }
        }
        String book = httpRepository.updateBook(isbn, new UpdateBookRequest(title, description, genre,authors,coverPhoto));

        if (book != null) {
            return Optional.of(book);
        }

        return Optional.empty();
    }*/

    @Override
    public Book registerBook(Book book){
        Optional<Book> bookDB = dbRepository.findByIsbn(book.getIsbn());
        if (bookDB.isEmpty()){
            return dbRepository.save(book);
        }

        throw new DuplicatedDataException(Book.class,book.getIsbn());
    }

    @Override
    public TempBook saveTempBook(CreateBookAllRequest bookAllRequest) {

        List<CreateAuthorRequest> authorsRequest = bookAllRequest.getAuthors();
        Set<TempAuthor> authorSet = new HashSet<>();
        for (CreateAuthorRequest request: authorsRequest){
            TempAuthor tempAuthor = new TempAuthor(request.getName(), request.getShortBio());
            authorSet.add(tempAuthorRepository.save(tempAuthor));
        }

        TempBook tempBook = new TempBook(bookAllRequest.getIsbn(), bookAllRequest.getTitle(), bookAllRequest.getDescription(), bookAllRequest.getGenre(), authorSet, bookAllRequest.getCoverPhoto());

        return tempBookRepository.save(tempBook);
    }

    /*@Override
    public List<Book> getAllBooks() {
        return (List<Book>) dbRepository.findAll();
    }*/
}
