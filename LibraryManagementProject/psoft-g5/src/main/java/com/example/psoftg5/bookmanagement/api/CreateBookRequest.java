package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.authormanagement.model.Author;
import com.example.psoftg5.bookmanagement.model.Genre;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateBookRequest {
    private String isbn;
    private String title;
    private String description;
    private Genre genre;
    private List<String> authors;
    private String coverPhoto;
}
