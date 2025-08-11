package com.example.psoftg5.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public class AuthorityRole implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    public static final String LIBRARIAN = "Librarian";
    public static final String READER = "Reader";

    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public static AuthorityRole fromString(String role) {
        if (role.equalsIgnoreCase(AuthorityRole.LIBRARIAN)) {
            return new AuthorityRole(AuthorityRole.LIBRARIAN);
        } else if (role.equalsIgnoreCase(AuthorityRole.READER)) {
            return new AuthorityRole(AuthorityRole.READER);
        } else {
            throw new IllegalArgumentException("Role '" + role + "' não é suportada");
        }
    }
}
