package com.example.psoftg5.recommendationmanagement.repository.rabbit;

import com.example.psoftg5.recommendationmanagement.model.Recommendation;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class RecommendationSender {

    @Autowired
    private RabbitTemplate template;



    @Autowired
    @Qualifier("recommendationsTopicExchange")
    private TopicExchange recommendationExchange;

    public void sendRecommendations(Recommendation recommendation) {
        Gson gson = new Gson();
        String json = gson.toJson(recommendation);
        template.convertAndSend(recommendationExchange.getName(), "recommendation.created."+recommendation.getUsername(),json);
    }
}
