package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;

import com.example.psoftg5.bookmanagement.service.BookService;

import com.example.psoftg5.exceptions.NotFoundException;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final  BookDTOMapper bookDTOMapper;
    private final AuthorDTOMapper authorDTOMapper;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> getBookByIsbn(@RequestParam String isbn) {
        Optional<Book> book = bookService.findByIsbn(isbn);
        BookDTO bookDTO = bookDTOMapper.toDTO(book.get());
        return ResponseEntity.ok().body(bookDTO);

    }

    @GetMapping("/search/title")
    public ResponseEntity<BookDTO> getBooksByTitle(@RequestParam String title) {
        Optional<Book> book = bookService.findByTitle(title);
        BookDTO bookDTO = bookDTOMapper.toDTO(book.get());
        return ResponseEntity.ok().body(bookDTO);
    }

    @GetMapping("/search/ByGenre")
    public ResponseEntity<List<?>> searchBooksByGenre(@RequestParam String genreName) {
        List<Book> books = bookService.searchBooksByGenre(new Genre(genreName));

        return new ResponseEntity<>(bookDTOMapper.toList(books), HttpStatus.OK);
    }

    @GetMapping("/top5genres")
    public ResponseEntity<List<String>> getTopGenres() {
        List<String> topGenres = bookService.getTop5Genres();
        return new ResponseEntity<>(topGenres, HttpStatus.OK);
    }
}
