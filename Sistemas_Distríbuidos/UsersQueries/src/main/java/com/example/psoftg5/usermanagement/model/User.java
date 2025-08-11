package com.example.psoftg5.usermanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "users")

public class User implements UserDetails {

    @Getter
    @Setter
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long readerNumber;

    @Column(unique = true, updatable = false, nullable = false)
    @Email
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Expose
    private String username;

    @Column(nullable = false)
    @Getter
    @Setter
    @NotNull
    @NotBlank
    private String password;

    @Getter
    @Column(nullable = false, updatable = false)
    @NotNull
    @NotBlank
    @Expose
    @Size(min = 1, max = 32)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false)
    private boolean enabled = true; // Define se a conta do usuário está ativa

    @Setter
    @Getter
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Getter
    @Setter
    @Column(nullable = false)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(nullable = false)
    private boolean gdpr;

    @Getter
    @Setter
    @Column(nullable = false)
    private long borrowCount = 0;

    @Getter
    @Setter
    private String optionalPhoto;

    @ElementCollection
    @Getter
    private final Set<AuthorityRole> authorities = new HashSet<>();



    /*@OneToMany(fetch = FetchType.LAZY)
    @Setter
    @Getter
    private List<Book> books = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Setter
    @Getter
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @Setter
    @Getter
    private List<Book> book =new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @Setter
    @Getter
    private List<Genre> genre = new ArrayList<>();
    @Lob
    @Column(name = "photo")
    private byte[] photoUser;*/


    // Construtores
    public User() {
    }

    public User(final String username, final String password) {
        this.username = username;
        setPassword(password);
    }

    public static User newUser(String username, String password, String name, LocalDate dateOfBirth, Long phoneNumber, boolean gdpr, String role,String optionalPhoto) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(String.valueOf(phoneNumber));
        user.setGdpr(gdpr);
        user.addAuthority(AuthorityRole.fromString(role));
        user.setOptionalPhoto(optionalPhoto);
        return user;
    }

    public void addAuthority(final AuthorityRole r) {
        authorities.add(r);
    }

    public void setPassword(final String password) {
        this.password = Objects.requireNonNull(password);
    }

    public void setName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("'name' is a mandatory attribute of User");
        }
        if (!name.matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException("Invalid character(s) in 'name', only alphanumeric are valid");
        }
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{9}")) {
            throw new IllegalArgumentException("Invalid Phone Number " + phoneNumber);
        }
        this.phoneNumber = phoneNumber;
    }


    public String getRole() {
        if (authorities.isEmpty()) {
            return null;
        }

        AuthorityRole firstAuthority = authorities.iterator().next();
        return firstAuthority.getAuthority();
    }

    public void applyPatch(String username, String password, String name, String dateOfBirth, String phoneNumber) {
        if (username != null) {
            setUsername(username);
        }
        if (password != null) {
            setPassword(password);
        }
        if (name != null) {
            setName(name);
        }
        if (dateOfBirth != null) {
            setDateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if (phoneNumber != null) {
            setPhoneNumber(phoneNumber);
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    // Implementar os métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (AuthorityRole role : this.authorities) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPhotoUser(byte[] photo) {
    }

    public void incrementBorrowCount() {
        this.borrowCount++;
    }

    public long getReaderNumber() {
        return readerNumber;
    }

    public void setReaderNumber(long readerNumber) {
        this.readerNumber = readerNumber;
    }

    public void setUsername(@Email @NotNull @NotBlank String username) {
        this.username = username;
    }

    public @NotNull @NotBlank @Size(min = 1, max = 32) String getName() {
        return name;
    }
}
