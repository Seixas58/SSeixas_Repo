package com.example.psoftg5.lendingmanagement.api;

import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.lendingmanagement.api.LendingDTO;
import com.example.psoftg5.lendingmanagement.api.LendingDTOMapper;
import com.example.psoftg5.lendingmanagement.model.Lending;
import com.example.psoftg5.lendingmanagement.service.LendingService;
import com.example.psoftg5.usermanagement.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Lendings")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lending")
public class LendingController {

    private final LendingService lendingService;
    private final LendingDTOMapper lendingDTOMapper;

    @Operation(summary = "Lend a book to a reader")
    @PostMapping("/lend")
    public ResponseEntity<LendingDTO> lendBook(@RequestBody CreateLendingRequest request) {
        Lending lending = lendingService.lendBook(request.getBookTitle(), request.getReaderNumber(), request.getLendingDate());
        LendingDTO lendingDTO = lendingDTOMapper.toDTO(lending);
        return ResponseEntity.status(HttpStatus.CREATED).body(lendingDTO);
    }

    @Operation(summary = "Return a book")
    @PostMapping("/return/{lendingNumber}")
    public ResponseEntity<LendingReturnDTO> returnBook(@PathVariable Long lendingNumber, @RequestBody CreateReturnRequest request) {
        Lending lending = lendingService.returnBook(lendingNumber, request.getReturnDate());
        LendingReturnDTO lendingReturnDTO = lendingDTOMapper.toReturnDTO(lending);
        return ResponseEntity.ok(lendingReturnDTO);
    }

    @Operation(summary = "Get lending details")
    @GetMapping("/{lendingNumber}")
    public ResponseEntity<LendingReturnDTO> getLendingDetails(@PathVariable Long lendingNumber) {
        Lending lending = lendingService.getLendingDetails(lendingNumber);
        LendingReturnDTO lendingReturnDTO = lendingDTOMapper.toReturnDTO(lending);
        return ResponseEntity.ok(lendingReturnDTO);
    }
    @GetMapping("/top5")
    public ResponseEntity<List<String>> getTop5LentBookTitles() {
        List<String> top5LentBookTitles = lendingService.getTop5LentBookTitles();
        return ResponseEntity.ok(top5LentBookTitles);
    }
    @GetMapping("/average-duration")
    public ResponseEntity<Double> getAverageLendingDuration() {
        double averageDuration = lendingService.getAverageLendingDuration();
        return ResponseEntity.ok(averageDuration);
    }
    @GetMapping("/average-lending-per-genre")
    @Operation(summary = "Get average number of lendings per genre for a specific month")
    public ResponseEntity<Map<String, Double>> getAverageLendingPerGenre(
            @RequestParam int month,
            @RequestParam int year) {
        Map<String, Double> averageLendingPerGenre = lendingService.getAverageLendingPerGenre(month, year);
        return ResponseEntity.ok(averageLendingPerGenre);
    }
    @Operation(summary = "Get number of lendings per month for the last 12 months, broken down by genre")
    @GetMapping("/lendings-per-month-by-genre")
    public ResponseEntity<Map<YearMonth, Map<String, Long>>> getLendingsPerMonthByGenre() {
        Map<YearMonth, Map<String, Long>> lendingsPerMonthByGenre = lendingService.getLendingsPerMonthByGenre();
        return ResponseEntity.ok(lendingsPerMonthByGenre);
    }
    @Operation(summary = "Get monthly lending count per reader for a specific period")
    @GetMapping("/monthly-lending-per-reader")
    public ResponseEntity<Map<String, Long>> getMonthlyLendingPerReader(
            @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Map<User, Long> lendingCounts = lendingService.getMonthlyLendingPerReader(start, end);
        Map<String, Long> response = lendingCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(), // ou qualquer outro identificador do usuário
                        Map.Entry::getValue
                ));
        return ResponseEntity.ok(response);
    }
}
