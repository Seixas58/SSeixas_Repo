package com.example.psoftg5.bookmanagement.repositories;

import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.TempAuthor;
import com.example.psoftg5.bookmanagement.model.TempBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempAuthorRepository extends CrudRepository<TempAuthor, Long> {
    @Query("SELECT a FROM TempAuthor a WHERE a.name = :name and a.shortBio = :bio")
    Optional<TempAuthor> findTempAuthorByNameAndShortBio(String name, String bio);
}
