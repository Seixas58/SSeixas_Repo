package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.bookmanagement.api.BookDTO;
import com.example.psoftg5.bookmanagement.model.Author;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import com.example.psoftg5.bookmanagement.repositories.AuthorDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
@RequiredArgsConstructor
public class BookDTOMapper {

    private final AuthorDTOMapper mapper;
    @Autowired
    private final AuthorDBRepository authorDBRepository;

    public BookDTO toDTO(Book book) {
        ArrayList<String> genres = new ArrayList<>();

        genres.add(book.getGenres().toString());

        BookDTO dto = new BookDTO();
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setGenres(genres);
        dto.setAuthors(mapper.toAuthorView(book.getAuthors()));
        dto.setCoverPhoto(book.getCoverPhoto());
        return dto;
    }

    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setGenres(new Genre(dto.getGenres().get(0)));
        //book.setAuthors(mapper.toAuthorView(dto.getAuthors()));
        book.setCoverPhoto(dto.getCoverPhoto());

        return book;
    }

    public List<BookDTO> toList(List<Book> books) {
        List<BookDTO> dtos = new ArrayList<>();
        for (Book entity : books) {
            dtos.add(toDTO(entity));
        }
        return dtos;
    }


    public Book registerBook(CreateBookRequest resource) {
        if (resource == null) {
            return null;
        }

        List<String> authorNames = resource.getAuthors();
        if (authorNames == null || authorNames.isEmpty()) {
            throw new IllegalArgumentException("The authors list cannot be null or empty.");
        }

        String isbn = resource.getIsbn();
        String title = resource.getTitle();
        String description = resource.getDescription();
        Genre genre = resource.getGenre();
        List<String> authors = resource.getAuthors();
        String coverPhoto = resource.getCoverPhoto();

        Set<Author> authorSet = new HashSet<>();

        for (String name : authorNames) {
            List<Author> authorsByName = authorDBRepository.findAuthorsByName(name);
            if (authorsByName.isEmpty()) {
                throw new IllegalArgumentException("No author found with name: " + name);
            }
            authorSet.addAll(authorsByName);
        }

        return new Book(isbn, title, description, genre, authorSet, coverPhoto);
    }
}
