package com.example.psoftg5.usermanagement.services;

import com.example.psoftg5.usermanagement.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getByReaderNumber(Long readerNumber);

    List <User> getByName(String name);
    
}
