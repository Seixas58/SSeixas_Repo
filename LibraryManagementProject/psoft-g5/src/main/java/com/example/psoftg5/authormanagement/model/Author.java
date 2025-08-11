package com.example.psoftg5.authormanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Authors")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorNumber;

    @Column(nullable = false, unique = true, updatable = true)
    @Size(min = 1, max = 150)
    private String name;

    @Column(nullable = false)
    @Size(min = 1, max = 4096)
    private String shortBio;

    private String photoURL;

    public Author(){}
    public Author(String name, String shortBio, String photoURL) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (shortBio == null || shortBio.isEmpty()) {
            throw new IllegalArgumentException("Short bio cannot be null or empty");
        }
        this.name = name;
        this.shortBio = shortBio;
        this.photoURL = photoURL;
    }


    public String getName(){
        return name;
    }

    public Long getAuthorNumber(){
        return authorNumber;
    }

    public String getShortBio(){
        return shortBio;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setShortBio(String shortBio){
        this.shortBio = shortBio;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
