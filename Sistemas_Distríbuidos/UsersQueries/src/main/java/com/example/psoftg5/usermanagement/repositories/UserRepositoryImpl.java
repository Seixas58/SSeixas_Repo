package com.example.psoftg5.usermanagement.repositories;

import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.services.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserDBRepository dbRepository;

    @Override
    public Optional<User> getByReaderNumber(Long readerNumber) {
        return dbRepository.findByReaderNumber(readerNumber);
    }

    public List<User> getByName(String name) {
        return dbRepository.getUserByName(name);
    }
}
