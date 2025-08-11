package com.example.psoftg5.authormanagement.service;

import com.example.psoftg5.authormanagement.api.AuthorDTOMapper;
import com.example.psoftg5.authormanagement.api.NewAuthorAndBookRequest;
import com.example.psoftg5.authormanagement.api.UpdateAuthorRequest;
import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.authormanagement.repositories.AuthorDBRepository;
import com.example.psoftg5.authormanagement.repositories.Rabbit.Sender;
import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.authormanagement.api.CreateAuthorRequest;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorDBRepository repository;
    private final AuthorDTOMapper mapper;
    private final AuthorRepository authorRepository;
    private final Sender sender;


   /* public Author updateAuthor(long authorNumber, @Valid UpdateAuthorRequest request) {


        Optional<Author> existingAuthor = authorRepository.findAuthorByAuthorNumber(authorNumber);

        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            author.setName(request.getName());
            author.setShortBio(request.getShortBio());
            author.setPhotoURL(request.getPhotoURL());
            return repository.save(author);
        } else {
            throw new NotFoundException(Author.class, "Author not found");
        }
    }*/

    public Author create(final @Valid CreateAuthorRequest resource) {
        final Author author = mapper.create(resource);
        sender.sendCreatedAuthor(author);
        return repository.save(author);
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

    public void saveAuthors(final String json) {
        Gson gson = new Gson();
        NewAuthorAndBookRequest request = gson.fromJson(json, NewAuthorAndBookRequest.class);

        List<CreateAuthorRequest> authorsRequest = request.getAuthors();
        List<Author> authors = new ArrayList<>();
        for (CreateAuthorRequest auth : authorsRequest) {
            authors.add(new Author(auth.getName(), auth.getShortBio(), auth.getPhotoURL()));
        }

        for (Author author : authors) {
            try {
                repository.save(author);
            } catch (Exception e) {
                return;
            }
        }

        for (Author author : authors) {
            sender.sendCreatedAuthor(author);
        }

        sender.createdBonusAuthors(request.getIsbn());
    }
}
