package com.example.psoftg5.lendingmanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;

@Data
@Schema
public class BookDTO {
    private String isbn;
    private String title;
    private String description;
    private String coverPhoto;
    private ArrayList<String> genres;
    private Iterable<AuthorDTO> authors;
}
