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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Recommendations")
@RestController("RecoCommRest")
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {


    private final RecommendationService service;

    private final Utils utils;

    @Operation(summary = "Create a Recommendation")
    @PostMapping()
    public ResponseEntity<Recommendation> lendBook(@RequestBody CreateRecommendationRequest body, HttpServletRequest request) {

        String username = utils.getEmailFromToken(request);

        Recommendation recommendation = service.createRecommendation(body, username);

        if (recommendation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(recommendation);
    }

}
