package com.example.psoftg5.lendingmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendingNumber;

    @ManyToOne
    private Book book;

    @Column(unique = false, updatable = false, nullable = false)
    @Email
    @Getter
    @Setter
    @NotNull
    @NotBlank
    private String username;

    private LocalDate lendingDate;
    private LocalDate returnDate;

    @Transient
    private boolean overdue;

    @Transient
    private long daysInOverdue;

    public Lending() {
    }

    public Lending(Book book, String username, LocalDate lendingDate, LocalDate returnDate) {
        this.book = book;
        this.username = username;
        this.lendingDate = lendingDate;
        this.returnDate = returnDate;
    }

    public Long getLendingNumber() {
        return lendingNumber;
    }

    public void setLendingNumber(Long lendingNumber) {
        this.lendingNumber = lendingNumber;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        updateOverdueStatus();
    }

    public boolean isOverdue() {
        return overdue;
    }

    public long getNumberOfDaysInOverdue() {
        return daysInOverdue;
    }

    public double getFineAmount() {
        return daysInOverdue * 1.0; // Supondo uma multa de 1 unidade monetária por dia de atraso
    }

    private void updateOverdueStatus() {
        LocalDate dueDate = lendingDate.plusDays(14);
        if (returnDate != null && returnDate.isAfter(dueDate)) {
            overdue = true;
            daysInOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
        } else {
            overdue = false;
            daysInOverdue = 0;
        }
    }
}

