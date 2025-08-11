package com.example.psoftg5.bookmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "An Author")
public class AuthorDTO {

    private String name;
    private String shortBio;
    private Long authorNumber;
    private String photoURL;
    //private List<BookDTO> books;
}
