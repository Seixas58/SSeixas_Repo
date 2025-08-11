package com.example.psoftg5.bookmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Genre {
    @Id
    @Getter
    private String genreName;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Book> books = new ArrayList<>();

    protected Genre() {}

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return genreName ;
    }
}
