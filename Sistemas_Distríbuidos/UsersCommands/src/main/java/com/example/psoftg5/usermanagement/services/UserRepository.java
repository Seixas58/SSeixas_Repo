package com.example.psoftg5.usermanagement.services;

import com.example.psoftg5.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository{
    User create(User user);
}
