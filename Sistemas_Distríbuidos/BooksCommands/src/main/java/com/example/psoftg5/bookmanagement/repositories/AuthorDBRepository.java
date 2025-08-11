package com.example.psoftg5.bookmanagement.repositories;

import com.example.psoftg5.bookmanagement.model.Author;
import com.example.psoftg5.bookmanagement.model.TempAuthor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorDBRepository extends CrudRepository<Author, Long> {

    //Author findByAuthorNumber(long authorNumber);


    List<Author> findAuthorsByName(String name);
    @Query("SELECT a FROM Author a WHERE a.name = :name and a.shortBio = :bio")
    Optional<Author> findAuthorByNameAndShortBio(String name, String bio);
    //Optional<Author> findAuthorByAuthorNumber(long authorNumber);
}
