package com.example.psoftg5.bookmanagement.model;



import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Book {

    @Id
    private String isbn;

    private String title;
    private String description;
    private String coverPhoto;

    @ManyToMany
    private Set<Author> authors;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genreName")
    private Genre genre;

    public Book(){}

    public Book(String isbn, String title, String description, Genre genre, Set<Author> authors, String coverPhoto){
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.genre = genre;
        this.coverPhoto = coverPhoto;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getCoverPhoto(){
        return coverPhoto;
    }

    public Genre getGenres() {
        return genre;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setCoverPhoto(String coverPhoto){
        this.coverPhoto = coverPhoto;
    }

    public void setGenres(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", coverPhoto='" + coverPhoto + '\'' +
                ", authors=" + authors +
                ", genre=" + genre +
                '}';
    }
}
