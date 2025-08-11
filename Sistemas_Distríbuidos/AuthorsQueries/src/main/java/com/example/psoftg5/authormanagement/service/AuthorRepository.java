package com.example.psoftg5.authormanagement.service;

import com.example.psoftg5.authormanagement.model.Author;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository {
    List<Author> getListAuthorByName(String name);
    Optional<Author> findAuthorByAuthorNumber(long authorNumber);

}
