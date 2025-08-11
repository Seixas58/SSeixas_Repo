package com.example.psoftg5.recommendationmanagement.api;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateRecommendationRequest {
    @NotNull
    @NotBlank
    private String isbn;

    @Size(max = 2048)
    private String description;

    @Min(0)
    @Max(5)
    private int rating;
}
