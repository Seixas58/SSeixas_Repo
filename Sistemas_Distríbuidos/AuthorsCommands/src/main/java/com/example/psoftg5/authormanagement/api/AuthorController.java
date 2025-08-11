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

    /*@Operation(summary = "Edit a specific author by authorNumber")
    @PutMapping("/update/{authorNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable("authorNumber") long authorNumber,
            @Valid @RequestBody UpdateAuthorRequest request) {
        final var updateAuthor = service.updateAuthor(authorNumber);
        return ResponseEntity.ok().body(authorMapper.toAuthorView(updateAuthor));
    }*/
}
