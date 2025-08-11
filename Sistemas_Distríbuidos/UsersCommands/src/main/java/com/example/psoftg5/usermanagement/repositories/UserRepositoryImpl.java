package com.example.psoftg5.usermanagement.repositories;

import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.repositories.Rabbit.Sender;
import com.example.psoftg5.usermanagement.services.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private final UserDBRepository dbRepository;
    private final Sender sender;

    @Override
    public User create(User user){
        final var userOpt = dbRepository.findByUsername(user.getUsername());
        if(userOpt.isEmpty()){
            User fUser = dbRepository.save(user);
            sender.sendCreatedUser(fUser);
            return fUser;
        }
        throw new DuplicatedDataException(User.class,user.getUsername());
    }

}
