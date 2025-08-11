package com.example.psoftg5.lendingmanagement.model;

import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.usermanagement.model.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendingNumber;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    private LocalDate lendingDate;
    private LocalDate returnDate;

    @Transient
    private boolean overdue;

    @Transient
    private long daysInOverdue;

    public Lending() {
    }

    public Lending(Book book, User user, LocalDate lendingDate, LocalDate returnDate) {
        this.book = book;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

