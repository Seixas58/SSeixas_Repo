package com.example.psoftg5.authormanagement.repositories;

import com.example.psoftg5.authormanagement.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorDBRepository extends CrudRepository<Author, Long> {

    //Author findByAuthorNumber(long authorNumber);


    List<Author> findAuthorsByName(String name);

    //Optional<Author> findAuthorByAuthorNumber(long authorNumber);
}
