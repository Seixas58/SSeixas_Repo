package com.example.library2024.dto;

import com.example.library2024.model.Author;
import com.example.library2024.model.Book;
import com.example.library2024.model.Cover;
import com.example.library2024.model.Library;
import com.example.library2024.model.Review;

import java.util.ArrayList;

public class Mapper {

    public static ArrayList<Author> authorsDTO2authors(ArrayList<AuthorDTO> authorDTOS)throws NullPointerException {
        ArrayList<Author> authors = new ArrayList<>();
        for (AuthorDTO dto: authorDTOS) {
            Author author = new Author(null,dto.getBio(),dto.getBirthDate(),dto.getDeathDate(),0,dto.getName());
            authors.add(author);
        }
        return authors;
    }

    public static Book bookDTO2Book(BookDTO obj, ArrayList<AuthorDTO> authorDTOS) throws NullPointerException{
        ArrayList<Author> authors = new ArrayList<>();
        if (authorDTOS != null) {
            authors = authorsDTO2authors(authorDTOS);
        }
        Cover cover = new Cover(obj.getLargeUrl(), obj.getMediumUrl(), obj.getSmallUrl());
        Book book = new Book(authors, cover, obj.getByStatement(), obj.getDescription(), obj.getIsbn(), obj.getPublishDate(), obj.getTitle(), obj.getNumberOfPages());
        return book;
    }

    public static ArrayList<Book> booksDTO2books(ArrayList<BookDTO> bookDTOs) throws NullPointerException {
        ArrayList<Book> books = new ArrayList<>();
        for (BookDTO dto : bookDTOs) {
            Book book = bookDTO2Book(dto, null);
            books.add(book);
        }
        return books;
    }

    public static Library libraryDTO2library(LibraryDTO dto) throws NullPointerException {
        if (dto == null) {
            throw new NullPointerException("LibraryDTO object is null");
        }

        return new Library(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getOpenDays(),
                dto.getCloseTime(),
                dto.getOpenTime()
        );
    }

    public static ArrayList<Library> librariesDTO2libraries(ArrayList<LibraryDTO> libraryDTOs) {
        ArrayList<Library> libraries = new ArrayList<>();
        for (LibraryDTO dto : libraryDTOs) {
            Library library = libraryDTO2library(dto);
            libraries.add(library);
        }
        return libraries;
    }
    public static Review reviewDTO2Review(ReviewDTO dto) {
        return new Review(dto.getReviewerName(), dto.getReviewText());
    }

    public static ArrayList<Review> reviewsDTO2Reviews(ArrayList<ReviewDTO> reviewDTOs) {
        ArrayList<Review> reviews = new ArrayList<>();
        for (ReviewDTO dto : reviewDTOs) {
            reviews.add(reviewDTO2Review(dto));
        }
        return reviews;
    }
}
