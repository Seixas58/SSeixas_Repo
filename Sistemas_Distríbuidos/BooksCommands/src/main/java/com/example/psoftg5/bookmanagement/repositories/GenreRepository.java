package com.example.psoftg5.bookmanagement.repositories;

import com.example.psoftg5.bookmanagement.model.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, String> {
    Optional<Genre> findByGenreName(String genreName);
}
