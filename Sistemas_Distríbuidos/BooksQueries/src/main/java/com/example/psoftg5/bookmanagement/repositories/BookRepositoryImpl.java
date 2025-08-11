package com.example.psoftg5.bookmanagement.repositories;

import com.example.psoftg5.bookmanagement.api.BookDTOMapper;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.bookmanagement.service.BookRepository;
import com.example.psoftg5.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookDBRepository dbRepository;
    private final BookDTOMapper bookDTOMapper;

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        Optional<Book> book = dbRepository.findByIsbn(isbn);

        if (book.isPresent()){
            return Optional.of(book.get());
        }
        throw new NotFoundException(Book.class, isbn);
    }

    @Override
    public Optional<Book> findBookByTitle(String title) {
        Optional<Book> book = dbRepository.findByTitle(title);
        if (book.isPresent()){
            return Optional.of(book.get());
        }
        throw new NotFoundException(Book.class, title);
    }

    @Override
    public List<Book> findBooksByGenre(Genre genre) {
        List<Book> books = dbRepository.findByGenre(genre);
        if (!books.isEmpty()) {
            return books;
        }
        throw new NotFoundException(Book.class, genre.getGenreName());
    }


   /* @Override
    public List<Book> getAllBooks() {
        return (List<Book>) dbRepository.findAll();
    }*/

   /* @Override
    public String getBooksByName(String title, boolean internal) throws Exception {
        List<Book> booksDB = dbRepository.findByTitleStartingWith(title);

        List<BookDTO> bookDTOList = booksDB.stream()
                .map(book -> bookDTOMapper.toDTO(book))
                .collect(Collectors.toList());

        if (internal) {
            return new Gson().toJson(bookDTOList);
        }

        List<String> booksDBString = bookDTOList.stream()
                .map(bookDTO -> new Gson().toJson(bookDTO))
                .collect(Collectors.toList());

        String booksHTTP = httpRepository.getBookByTitle(title);
        if (booksHTTP != null) {
            JsonArray externalBooks = JsonParser.parseString(booksHTTP).getAsJsonArray();
            for (JsonElement book : externalBooks) {
                booksDBString.add(book.toString());
            }
        }

        return "[" + String.join(",", booksDBString) + "]";
    }*/
}
