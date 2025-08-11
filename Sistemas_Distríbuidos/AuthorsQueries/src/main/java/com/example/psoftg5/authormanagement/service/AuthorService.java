package com.example.psoftg5.authormanagement.service;

import com.example.psoftg5.authormanagement.api.AuthorDTOMapper;
import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.authormanagement.repositories.AuthorDBRepository;
import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.example.psoftg5.exceptions.NotFoundException;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorDBRepository repository;
    private final AuthorDTOMapper mapper;
    private final AuthorRepository authorRepository;


    public Optional<Author> findAuthorByAuthorNumber(final long authorNumber)  {

        return authorRepository.findAuthorByAuthorNumber(authorNumber);
    }

    public List<Author> findAuthorsByName(String name) {

        return authorRepository.getListAuthorByName(name);
    }

    public Author saveAuthor(final String authorJson) {
        Gson gson = new Gson();
        Author author = gson.fromJson(authorJson, Author.class);

        try {
            return repository.save(author);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }
}
