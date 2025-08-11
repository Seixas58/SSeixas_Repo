package com.example.psoftg5.recommendationmanagement.repository;

import com.example.psoftg5.recommendationmanagement.model.Lending;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("RecoQueriesLendingRepo")
public interface LendingDBRepository extends CrudRepository<Lending, Long> {

    Optional<Lending> findLendingByUsernameAndISBN(@Email @NotNull @NotBlank String username, String isbn);
}
