package com.example.psoftg5.recommendationmanagement.repository;

import com.example.psoftg5.recommendationmanagement.model.Recommendation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {

}
