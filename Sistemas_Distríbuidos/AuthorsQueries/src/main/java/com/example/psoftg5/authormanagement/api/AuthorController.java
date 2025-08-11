package com.example.psoftg5.authormanagement.api;

import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.authormanagement.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Authors")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService service;
    private final AuthorDTOMapper authorMapper;

    @Operation(summary = "Get an author´s detail by their author number")
    @GetMapping("/get")
    public ResponseEntity<AuthorDTO> getAuthorDetails(@RequestParam("authorNumber") Long authorNumber) {
        Optional<Author> optionalAuthor = service.findAuthorByAuthorNumber(authorNumber);
        if (optionalAuthor.isPresent()) {
            AuthorDTO authorDTO = authorMapper.toAuthorView(optionalAuthor.get());
            return ResponseEntity.ok().body(authorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Search authors by name")
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> searchAuthorsByName(@RequestParam("name") String name) {
        List<Author> authors = service.findAuthorsByName(name);
        List<AuthorDTO> authorDTOs = authorMapper.toAuthorView(authors);
        if (!authorDTOs.isEmpty()) {
            return ResponseEntity.ok().body(authorDTOs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ArrayList<>());
        }
    }
}
