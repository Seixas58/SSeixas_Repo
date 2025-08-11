package com.example.psoftg5.bookmanagement.repositories;

import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends CrudRepository<Book, String> {

   List<Book> findByGenre(Genre genre);


   @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
   Optional<Book> findByIsbn(String isbn);

   Optional<Book> findByTitle(String title);

   @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
   List<Book> findByTitleStartingWith(@Param("title") String title);

}

