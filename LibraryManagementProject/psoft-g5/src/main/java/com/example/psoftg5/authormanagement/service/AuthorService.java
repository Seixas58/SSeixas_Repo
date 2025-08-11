package com.example.psoftg5.authormanagement.service;

import com.example.psoftg5.authormanagement.api.AuthorDTOMapper;
import com.example.psoftg5.authormanagement.api.UpdateAuthorRequest;
import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.authormanagement.repositories.AuthorRepository;
import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.authormanagement.api.CreateAuthorRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorDTOMapper mapper;

    public AuthorService(AuthorRepository repository, AuthorDTOMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Author updateAuthor(Long authorNumber, @Valid UpdateAuthorRequest request) {
        Optional<Author> existingAuthor = Optional.ofNullable(repository.findByAuthorNumber(authorNumber));

        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            author.setName(request.getName());
            author.setShortBio(request.getShortBio());
            author.setPhotoURL(request.getPhotoURL());
            return repository.save(author);
        } else {
            throw new NotFoundException(Author.class, "Author not found");
        }
    }

    public Optional<Author> findAuthorByAuthorNumber(final long authorNumber) {
        return repository.findAuthorByAuthorNumber(authorNumber);
    }

    public Author create(Author author) {
        return repository.save(author);
    }

    public Author create(final @Valid CreateAuthorRequest resource) {
        final Author author = mapper.create(resource);
        return repository.save(author);
    }

    public List<Author> findAuthorsByName(String name) {
        return repository.findAuthorsByName(name);
    }
}
