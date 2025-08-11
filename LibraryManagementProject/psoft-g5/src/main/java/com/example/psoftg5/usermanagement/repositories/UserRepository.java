package com.example.psoftg5.usermanagement.repositories;

import com.example.psoftg5.exceptions.NotFoundException;
import com.example.psoftg5.usermanagement.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> getUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.readerNumber = :readerNumber")
    Optional<User> findByReaderNumber(@Param("readerNumber") Long readerNumber);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    List<User> findByPhoneNumber(@Param("phoneNumber") Long phoneNumber);

}



