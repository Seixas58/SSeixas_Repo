package com.example.psoftg5.recommendationmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity(name = "RecoQueriesLending")
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendingNumber;

    private String ISBN;

    @Column(unique = false, updatable = false, nullable = false)
    @Email
    @Getter
    @Setter
    @NotNull
    @NotBlank
    private String username;


    public Lending() {
    }

    public Lending(String ISBN, String username) {
        this.ISBN = ISBN;
        this.username = username;

    }

    public Long getLendingNumber() {
        return lendingNumber;
    }

    public void setLendingNumber(Long lendingNumber) {
        this.lendingNumber = lendingNumber;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }


    public String getUser() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }


}

