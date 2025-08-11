package com.example.psoftg5.lendingmanagement.repository;

import com.example.psoftg5.lendingmanagement.model.Lending;
//import com.example.psoftg5.lendingmanagement.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LendingRepository extends CrudRepository<Lending, Long> {
    Optional<Lending> findByLendingNumber(Long lendingNumber);
    //List<Lending> findAllByUser(User user);

    @Query("SELECT l FROM Lending l WHERE MONTH(l.lendingDate) = ?1 AND YEAR(l.lendingDate) = ?2")
    List<Lending> findAllByMonthAndYear(int month, int year);

    @Query("SELECT l FROM Lending l WHERE l.lendingDate BETWEEN ?1 AND ?2")
    List<Lending> findAllBetweenDates(LocalDate startDate, LocalDate endDate);

    @Query("SELECT l.username, COUNT(l) FROM Lending l WHERE l.lendingDate BETWEEN ?1 AND ?2 GROUP BY l.username")
    List<Object[]> findLendingCountPerUserBetweenDates(LocalDate startDate, LocalDate endDate);

    @Query("SELECT l FROM Lending l WHERE l.username=:username")
    List<Lending> findAllByUsername(String username);

}
