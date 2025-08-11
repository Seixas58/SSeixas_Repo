package com.example.psoftg5.lendingmanagement.api;

import lombok.Data;
import java.time.LocalDate;
@Data
public class CreateReturnRequest {
    private LocalDate returnDate;
}
