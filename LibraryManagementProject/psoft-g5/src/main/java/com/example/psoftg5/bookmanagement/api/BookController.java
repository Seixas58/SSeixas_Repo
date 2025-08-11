package com.example.psoftg5.bookmanagement.api;
import com.example.psoftg5.authormanagement.api.AuthorDTOMapper;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.bookmanagement.service.BookService;
import com.example.psoftg5.usermanagement.model.AuthorityRole;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final  BookDTOMapper bookDTOMapper;
    private final AuthorDTOMapper authorDTOMapper;


    @PostMapping("/create")
    @RolesAllowed(AuthorityRole.LIBRARIAN)
    public ResponseEntity<BookDTO> registerBook(@RequestBody CreateBookRequest createBookRequest) {
        final var book = bookService.registerBook(
                createBookRequest.getIsbn(),
                createBookRequest.getTitle(),
                createBookRequest.getDescription(),
                createBookRequest.getGenre(),
                createBookRequest.getAuthors(),
                createBookRequest.getCoverPhoto()
        );

        BookDTO registeredBookDTO = bookDTOMapper.toDTO(book);

        return new ResponseEntity<>(registeredBookDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String isbn, @RequestBody UpdateBookRequest updateBookRequest) {
        final var book = bookService.updateBook(
                isbn,
                updateBookRequest.getTitle(),
                updateBookRequest.getDescription(),
                updateBookRequest.getGenre(),
                updateBookRequest.getAuthors(),
                updateBookRequest.getCoverPhoto()
        );

        if (book != null) {
            return new ResponseEntity<>(bookDTOMapper.toDTO(book), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<BookDTO> getBookByIsbn(@RequestParam String isbn) {
        final var book = bookService.getBookByIsbn(isbn);
        BookDTO bookDTO = bookDTOMapper.toDTO(book);
        if (bookDTO != null) {
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooksByGenre(@RequestParam String genreName) {
        List<Book> books = bookService.searchBooksByGenre(new Genre(genreName));

        return new ResponseEntity<>(bookDTOMapper.toList(books), HttpStatus.OK);
    }
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.searchBooksByTitleStartingWith(title);
        if (!books.isEmpty()) {
            return new ResponseEntity<>(bookDTOMapper.toList(books), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/top")
    public ResponseEntity<List<String>> getTopGenres() {
        List<String> topGenres = bookService.getTopGenres();
        return new ResponseEntity<>(topGenres, HttpStatus.OK);
    }
}
