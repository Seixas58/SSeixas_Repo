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

    @Operation(summary = "Create an author")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody final CreateAuthorRequest resource) {
        final var author = service.create(resource);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(author.getName())
                .pathSegment(author.getPhotoURL())
                .build()
                .toUri();
        return ResponseEntity.created(uri).body(authorMapper.toAuthorView(author));
    }

    @Operation(summary = "Edit a specific author by authorNumber")
    @PutMapping("/update/{authorNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable("authorNumber") long authorNumber,
            @Valid @RequestBody UpdateAuthorRequest request) {
        final var updateAuthor = service.updateAuthor(authorNumber, request);
        return ResponseEntity.ok().body(authorMapper.toAuthorView(updateAuthor));
    }

    @Operation(summary = "Get an author´s detail by his author number")
    @GetMapping("/get")
    public ResponseEntity<AuthorDTO> getAuthorDetails(@RequestParam("authorNumber") Long authorNumber) {
        Optional<Author> author = service.findAuthorByAuthorNumber(authorNumber);
        if (author != null) {
            AuthorDTO authorDTO = authorMapper.toAuthorView(author);
            return ResponseEntity.ok().body(authorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Search authors by name")
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> searchAuthorsByName(@RequestParam("name") String name) {
        List<Author> authors = service.findAuthorsByName(name);
        if (!authors.isEmpty()) {
            List<AuthorDTO> authorDTOs = authors.stream()
                    .map(authorMapper::toAuthorView)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(authorDTOs);
        } else {
            return ResponseEntity.ok().body(Collections.emptyList());
        }
    }

}
