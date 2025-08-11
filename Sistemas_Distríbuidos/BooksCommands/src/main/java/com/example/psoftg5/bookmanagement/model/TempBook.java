package com.example.psoftg5.bookmanagement.model;

import com.example.psoftg5.bookmanagement.api.CreateAuthorRequest;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class TempBook {
    @Id
    private String isbn;

    private String title;
    private String description;
    private String coverPhoto;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<TempAuthor> authors;

    private String genre;

    public TempBook(){}

    public TempBook(String isbn, String title, String description, String genre, Set<TempAuthor> authors, String coverPhoto){
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

    public String getGenres() {
        return genre;
    }

    public Set<TempAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<TempAuthor> authors) {
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

    public void setGenres(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "TempBook{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", coverPhoto='" + coverPhoto + '\'' +
                ", authors=" + authors +
                ", genre=" + genre +
                '}';
    }
}
