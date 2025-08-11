package com.example.psoftg5.recommendationmanagement.service;

import com.example.psoftg5.exceptions.DuplicatedDataException;
import com.example.psoftg5.recommendationmanagement.api.CreateRecommendationRequest;
import com.example.psoftg5.recommendationmanagement.model.*;
import com.example.psoftg5.recommendationmanagement.repository.*;
import com.example.psoftg5.recommendationmanagement.repository.rabbit.RecommendationSender;
import com.example.psoftg5.utils.Utils;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private LendingDBRepository lendingRepository;
    @Autowired
    private BookDBRepository bookRepository;
    @Autowired
    private AuthorDBRepository authorRepository;

    private final RecommendationSender recommendationSender;



    public Recommendation createRecommendation(CreateRecommendationRequest request, String username) {
        Optional<Lending> lending = lendingRepository.findLendingByUsernameAndISBN(username, request.getIsbn());
        if (lending.isEmpty()) {
            return null;
        }
        Recommendation recommendation = recommendationRepository.save(new Recommendation(request.getIsbn(),username,request.getDescription(), request.getRating()));
        recommendationSender.sendRecommendations(recommendation);
        return recommendation;
    }

    public Book saveBook(final String bookJson) {
        Gson gson = new Gson();
        Book book = gson.fromJson(bookJson, Book.class);


        try {
            return bookRepository.save(book);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }
    public Author saveAuthor(final String authorJson) {
        Gson gson = new Gson();
        Author author = gson.fromJson(authorJson, Author.class);

        try {
            return authorRepository.save(author);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }


    public Lending saveLending(final String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, Utils.createLocalDateTypeAdapter())
                .create();
        Lending lending = gson.fromJson(json, Lending.class);

        try {
            return lendingRepository.save(lending);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }

    public Recommendation saveRecommendation(final String json) {
        Gson gson = new Gson();
        Recommendation recommendation = gson.fromJson(json, Recommendation.class);

        try {
            return recommendationRepository.save(recommendation);
        } catch (DuplicatedDataException e) {
            return null;
        }
    }
}
