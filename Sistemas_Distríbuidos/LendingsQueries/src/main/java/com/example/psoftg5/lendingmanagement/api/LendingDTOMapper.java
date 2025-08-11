package com.example.psoftg5.lendingmanagement.api;

import com.example.psoftg5.lendingmanagement.model.Lending;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LendingDTOMapper {

    public LendingDTO toDTO(Lending lending) {
        LendingDTO dto = new LendingDTO();
        dto.setLendingNumber(lending.getLendingNumber());
        dto.setBookTitle(lending.getBook().getTitle());
        dto.setLendingDate(lending.getLendingDate());
        dto.setReturnDate(lending.getReturnDate());
        dto.setNumberOfDaysTillReturn(14);
        return dto;
    }

    public LendingReturnDTO toReturnDTO(Lending lending) {
        LendingReturnDTO dto = new LendingReturnDTO();
        dto.setLendingNumber(lending.getLendingNumber());
        dto.setISBN(lending.getBook().getTitle());
        dto.setLendingDate(lending.getLendingDate());
        dto.setReturnDate(lending.getReturnDate());
        dto.setOverdue(lending.isOverdue());
        dto.setNumberOfDaysInOverdue(lending.getNumberOfDaysInOverdue());
        dto.setFineAmount(lending.getFineAmount());
        return dto;
    }
}
