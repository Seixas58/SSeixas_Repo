package com.example.psoftg5.usermanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A Reader or Librarian")
public class UserView {
        private String username;
        private String name;
        private String dateOfBirth;
        private String phoneNumber;
        @Setter
        private boolean gdpr;
        private String readerNumber;
        private String role;
        private long borrowCount;
        private String optionalPhoto;
}
