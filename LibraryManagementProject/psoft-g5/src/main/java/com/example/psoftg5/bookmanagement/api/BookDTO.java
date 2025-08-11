package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.authormanagement.api.AuthorDTO;
import com.example.psoftg5.authormanagement.model.Author;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.example.psoftg5.bookmanagement.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
