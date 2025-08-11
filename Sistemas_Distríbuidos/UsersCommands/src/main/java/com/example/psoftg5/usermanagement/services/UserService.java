package com.example.psoftg5.usermanagement.services;

import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.exceptions.ConflictException;
import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.usermanagement.api.CreateUserRequest;
import com.example.psoftg5.usermanagement.api.UserViewMapper;
import com.example.psoftg5.usermanagement.repositories.UserDBRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import com.example.psoftg5.usermanagement.repositories.Rabbit.Sender;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDBRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserViewMapper userViewMapper;
    private final Sender sender;


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

        User savedUser = userRepository.save(user);

        sender.sendCreatedUser(savedUser);

        return savedUser;
    }

    public User partialUpdate(String username, CreateUserRequest request) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Cannot update a user that does not yet exist"));

        user.applyPatch(request.getUsername(), request.getPassword(), request.getName(), String.valueOf(request.getDateOfBirth()), request.getPhoneNumber());
        return userRepository.save(user);
    }

    public User saveUser(final String userJson) {
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);

        try {
            return userRepository.save(user);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }
}







