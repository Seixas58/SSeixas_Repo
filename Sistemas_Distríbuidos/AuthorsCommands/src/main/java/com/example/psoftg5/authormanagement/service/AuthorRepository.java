package com.example.psoftg5.authormanagement.service;

import com.example.psoftg5.authormanagement.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository {
    Set<Author> createAuthorHash(List<String> authorNames);
}
