package com.example.psoftg5.authormanagement.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAuthorRequest {

    @NotBlank
    @Size(min = 1, max = 150)
    private String name;

    @NotBlank
    @Size(min = 1, max = 4096)
    private String shortBio;

    private String photoURL;

}

