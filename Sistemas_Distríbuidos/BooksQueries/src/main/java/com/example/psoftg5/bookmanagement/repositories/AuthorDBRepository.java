package com.example.psoftg5.bookmanagement.repositories;

import com.example.psoftg5.bookmanagement.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorDBRepository extends CrudRepository<Author, Long> {

    List<Author> getListAuthorByName(String name);


    @Query("SELECT a FROM Author a WHERE a.name = :name and a.shortBio = :bio")
    Optional<Author> findAuthorByNameAndShortBio(String name, String bio);

    List<Author> findAuthorsByName(String name);
}

