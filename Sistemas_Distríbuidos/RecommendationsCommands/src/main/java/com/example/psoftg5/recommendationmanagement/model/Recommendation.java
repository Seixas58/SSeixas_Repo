package com.example.psoftg5.recommendationmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@Entity
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    private String isbn;

    @NotNull
    @NotBlank
    private String username;

    @Size(max = 2048)
    private String description;

    @Min(0)
    @Max(5)
    private int rating;


    public Recommendation() {}

    public Recommendation(String isbn, String username, String description, int rating) {
        this.isbn = isbn;
        this.username = username;
        this.description = description;
        this.rating = rating;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
