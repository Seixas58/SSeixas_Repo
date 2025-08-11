package com.example.psoftg5.lendingmanagement.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LendingReturnDTO {
    private Long lendingNumber;
    private String bookTitle;
    private LocalDate lendingDate;
    private LocalDate returnDate;
    private boolean overdue;
    private long numberOfDaysInOverdue;
    private double fineAmount;
}
