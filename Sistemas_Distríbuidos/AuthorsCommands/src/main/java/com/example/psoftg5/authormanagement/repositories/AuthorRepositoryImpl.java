package com.example.psoftg5.authormanagement.repositories;

import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.authormanagement.service.AuthorRepository;
import com.example.psoftg5.exceptions.DuplicatedDataException;
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

    /*Override
    public Optional<Author> findAuthorByAuthorNumber(long authorNumber)  {
        Optional<Author> authorsDB = dbRepository.findAuthorByAuthorNumber(authorNumber);

        if (internal || authorsDB.isPresent()) {
            return authorsDB;
        }
        return httpRepository.getAuthorByAuthorNumber(authorNumber);
    }*/

    @Override
    public Set<Author> createAuthorHash(List<String> authorNames) {
        Set<Author> authorsSet = new HashSet<>();

        for (String authorName : authorNames) {
            List<Author> existingAuthors = dbRepository.findAuthorsByName(authorName);

            if (existingAuthors.isEmpty()) {
                // Criar novo autor se não existir no banco de dados
                Author newAuthor = new Author();
                newAuthor.setName(authorName);
                authorsSet.add(dbRepository.save(newAuthor));
            } else {
                // Lançar exceção para autores duplicados
                throw new DuplicatedDataException(Author.class, authorName);
            }
        }

        return authorsSet;
    }
}
