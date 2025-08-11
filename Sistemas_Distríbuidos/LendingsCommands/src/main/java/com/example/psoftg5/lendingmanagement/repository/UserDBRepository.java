package com.example.psoftg5.lendingmanagement.repository;

import com.example.psoftg5.lendingmanagement.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDBRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.readerNumber = :readerNumber")
    Optional<User> findByReaderNumber(@Param("readerNumber") Long readerNumber);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    List<User> findByPhoneNumber(@Param("phoneNumber") Long phoneNumber);

}






