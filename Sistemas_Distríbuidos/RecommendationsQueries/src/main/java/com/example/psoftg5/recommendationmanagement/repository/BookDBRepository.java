package com.example.psoftg5.recommendationmanagement.repository;

import com.example.psoftg5.recommendationmanagement.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("RecoQueriesBookRepo")
public interface BookDBRepository extends CrudRepository<Book, String> {

   //List<Book> findByGenre(Genre genre);


   @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
   Optional<Book> findByIsbn(String isbn);

   //Optional<Book> findByTitle(String title);

  /* @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
   List<Book> findByTitleStartingWith(@Param("title") String title);*/

}

