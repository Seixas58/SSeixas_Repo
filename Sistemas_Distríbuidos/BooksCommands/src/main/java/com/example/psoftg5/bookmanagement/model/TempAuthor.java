package com.example.psoftg5.bookmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class TempAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,  updatable = true)
    @Size(min = 1, max = 150)
    private String name;

    @Column(nullable = false)
    @Size(min = 1, max = 4096)
    private String shortBio;


    public TempAuthor() {

    }
    public TempAuthor(String name, String shortBio) {
        this.name = name;
        this.shortBio = shortBio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }
}
