package com.example.psoftg5.recommendationmanagement.api.Rabbit;


import com.example.psoftg5.recommendationmanagement.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component("RecoQueriesAuthorReceiver")

@RequiredArgsConstructor
public class AuthorReceiver {

    private final RecommendationService service;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCreatedAuthor(String authorJson) {
        service.saveAuthor(authorJson);
        System.out.println("AUTHOR TO BE CREATED - " + authorJson);
    }

    @RabbitListener(queues = "#{lendingCreatedQueue.name}")
    public void receiveCreatedLending(String lendingJson) {
        service.saveLending(lendingJson);
        System.out.println("LENDING TO BE CREATED - " + lendingJson);
    }

    @RabbitListener(queues = "#{recommendationCreatedQueue.name}")
    public void receiveCreatedRecommendation(String json) {
        service.saveRecommendation(json);
        System.out.println("RECOMMENDATION TO BE CREATED - " + json);
    }
}
