package com.example.psoftg5.recommendationmanagement.api;


import com.example.psoftg5.recommendationmanagement.model.Lending;
import com.example.psoftg5.recommendationmanagement.model.Recommendation;
import com.example.psoftg5.recommendationmanagement.service.RecommendationService;
import com.example.psoftg5.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Recommendations")
@RestController("RecoQueriesRest")
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {


    private final RecommendationService service;

    @Operation
    @GetMapping()
    public ResponseEntity<List<Recommendation>> getAllRecommendations() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRecommendations());
    }

}
