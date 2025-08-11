package com.example.psoftg5.usermanagement.services;
import ch.qos.logback.classic.encoder.JsonEncoder;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.repositories.BookRepository;
import com.example.psoftg5.bookmanagement.repositories.GenreRepository;
import com.example.psoftg5.exceptions.ConflictException;
import com.example.psoftg5.usermanagement.api.UserViewMapperImpl;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory;
import com.example.psoftg5.usermanagement.api.CreateUserRequest;
import com.example.psoftg5.usermanagement.api.UserViewMapper;
import com.example.psoftg5.usermanagement.model.AuthorityRole;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.utils.Utils;
import org.springframework.transaction.annotation.Transactional;
import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.bookmanagement.model.Genre;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserViewMapper userViewMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User create(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists!");
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }

        final User user = userViewMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }
    public List<Book> getBooksByUserGenres(Long readerNumber) {
        User user = userRepository.findByReaderNumber(readerNumber).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getGenres().stream()
                .flatMap(genre -> genre.getBooks().stream())
                .distinct()
                .toList();
    }

    public User partialUpdate(String username, CreateUserRequest request) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Cannot update a user that does not yet exist"));

        user.applyPatch(request.getUsername(), request.getPassword(), request.getName(), String.valueOf(request.getDateOfBirth()), request.getPhoneNumber());
        return userRepository.save(user);
    }

    public Optional<User> getUserByReaderNumber(Long readerNumber) {
        return userRepository.findByReaderNumber(readerNumber);
    }

    public List<User> findByName(String name) {
        return userRepository.getUserByName(name);
    }

    public List<User> getUsersByPhoneNumber(Long phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}







