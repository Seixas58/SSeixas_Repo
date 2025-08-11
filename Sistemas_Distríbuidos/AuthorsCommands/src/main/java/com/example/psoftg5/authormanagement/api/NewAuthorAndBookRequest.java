package com.example.psoftg5.authormanagement.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAuthorAndBookRequest {
    String isbn;
    List<CreateAuthorRequest> authors;
}
