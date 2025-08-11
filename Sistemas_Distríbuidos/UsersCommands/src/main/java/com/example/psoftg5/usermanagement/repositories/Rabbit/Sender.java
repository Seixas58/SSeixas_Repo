package com.example.psoftg5.usermanagement.repositories.Rabbit;

import com.example.psoftg5.usermanagement.model.User;

import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    public void sendCreatedUser(User user) {
        Gson gson = new Gson();
        String entity = gson.toJson(user);
        template.convertAndSend(topic.getName(), "user.created." + "test" , entity);
    }
}
