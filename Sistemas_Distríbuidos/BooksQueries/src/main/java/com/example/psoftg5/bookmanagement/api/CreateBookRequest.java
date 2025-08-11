package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.bookmanagement.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    private String isbn;
    private String title;
    private String description;
    private Genre genre;
    private List<String> authors;
    private String coverPhoto;
}
