package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.bookmanagement.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookAllRequest {
    private String isbn;
    private String title;
    private String description;
    private String genre;
    private List<CreateAuthorRequest> authors;
    private String coverPhoto;
}
