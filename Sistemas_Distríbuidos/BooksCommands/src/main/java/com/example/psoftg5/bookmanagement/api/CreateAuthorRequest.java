package com.example.psoftg5.bookmanagement.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAuthorRequest {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 150)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 4096)
    private String shortBio;

    private String photoURL;

    public CreateAuthorRequest(String name, String shortBio, String photoURL) {
        this.name = name;
        this.shortBio = shortBio;
        this.photoURL = photoURL;
    }

}
