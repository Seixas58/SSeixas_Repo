package com.example.psoftg5.recommendationmanagement.repository;

import com.example.psoftg5.recommendationmanagement.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("RecoCommAuthorRepo")
public interface AuthorDBRepository extends CrudRepository<Author, Long> {

    List<Author> findAuthorsByName(String name);

    Optional<Author> findAuthorByAuthorNumber(long authorNumber);
}
