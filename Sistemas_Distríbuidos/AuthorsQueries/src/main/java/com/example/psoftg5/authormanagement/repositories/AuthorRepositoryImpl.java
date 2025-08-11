package com.example.psoftg5.authormanagement.repositories;

import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.authormanagement.service.AuthorRepository;
import com.example.psoftg5.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorDBRepository dbRepository;
    @Override
    public List<Author> getListAuthorByName(String name){
        List<Author> authorsDB = dbRepository.findAuthorsByName(name);
            return authorsDB;
    }

    @Override
    public Optional<Author> findAuthorByAuthorNumber(long authorNumber) {
        Optional<Author> authorsDB = dbRepository.findAuthorByAuthorNumber(authorNumber);

        if (authorsDB.isPresent()) {
            return Optional.of(authorsDB.get());
        }
        throw new NotFoundException(Author.class, authorNumber);
    }
}
