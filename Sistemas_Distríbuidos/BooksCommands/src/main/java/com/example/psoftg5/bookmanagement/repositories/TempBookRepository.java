package com.example.psoftg5.bookmanagement.repositories;

import com.example.psoftg5.bookmanagement.model.TempAuthor;
import com.example.psoftg5.bookmanagement.model.TempBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TempBookRepository extends CrudRepository<TempBook, String> {

    @Query("SELECT b FROM TempBook b JOIN b.authors a WHERE a = :author")
    List<TempBook> findBooksByAuthor(@Param("author") TempAuthor author);

    Optional<TempBook> findBooksByIsbn(String isbn);
}
