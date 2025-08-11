package com.example.psoftg5.usermanagement.services;

import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.example.psoftg5.usermanagement.api.UserViewMapper;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.repositories.UserDBRepository;
import com.example.psoftg5.utils.LocalDateAdapter;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserDBRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserViewMapper userViewMapper;
    private final UserRepository repository;


    public Optional<User> getUserByReaderNumber(Long readerNumber){
        return repository.getByReaderNumber(readerNumber);
    }

    public List<User> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        return repository.getByName(name.trim());
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }
}







