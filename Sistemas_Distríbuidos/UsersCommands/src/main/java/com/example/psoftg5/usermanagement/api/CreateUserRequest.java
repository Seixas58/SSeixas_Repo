package com.example.psoftg5.usermanagement.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotNull
    @NotBlank
    @Email
    @Size(min = 1, max = 128)
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 32)
    private String name;

    @NonNull
    private LocalDate dateOfBirth;

    @NotNull
    @NotBlank
    @Size(max = 9)
    private String phoneNumber;

    @NotNull
    @NotBlank
    private String role;

    @NotNull
    private boolean gdpr;

    @NotNull
    @NotBlank
    private String rePassword;

    private String optionalPhoto;

    public String getGdpr() {
        return String.valueOf(this.gdpr = gdpr);
    }
}
