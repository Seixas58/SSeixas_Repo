package com.example.psoftg5.bookmanagement.api;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.bookmanagement.model.TempBook;
import com.example.psoftg5.bookmanagement.service.BookService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> registerBook(@Valid @RequestBody CreateBookRequest resource) {
        final var book = bookService.registerBook(resource);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(book.getIsbn())
                .build()
                .toUri();
        return ResponseEntity.created(uri).body(bookDTOMapper.toDTO(book));
    }

    @PostMapping("/create/all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> registerBookAll(@Valid @RequestBody CreateBookAllRequest resource) {
        TempBook tempBook = bookService.saveBookAndAuthor(resource);
        return ResponseEntity.accepted().body(tempBook);
    }

   /* @PutMapping("/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody UpdateBookRequest updateBookRequest, @RequestParam(required = false) String type) throws Exception {
        Optional<Object> bookOpt = bookService.findByIsbn(isbn, true);
        if (type != null && type.equalsIgnoreCase("internal") && bookOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (bookOpt.isPresent() && bookOpt.get() instanceof Book) {
            final var book = bookService.updateBook(
                    isbn,
                    updateBookRequest.getTitle(),
                    updateBookRequest.getDescription(),
                    updateBookRequest.getGenre(),
                    updateBookRequest.getAuthors(),
                    updateBookRequest.getCoverPhoto(),
                    true
            );
            if (book.isPresent()) {
                return new ResponseEntity<>(bookDTOMapper.toDTO((Book) book.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        final var book = bookService.updateBook(
                isbn,
                updateBookRequest.getTitle(),
                updateBookRequest.getDescription(),
                updateBookRequest.getGenre(),
                updateBookRequest.getAuthors(),
                updateBookRequest.getCoverPhoto(),
                false
        );
        if (book.isPresent()) {
            final HttpHeaders httpHeaders= new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>((String) book.get(), httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

}
