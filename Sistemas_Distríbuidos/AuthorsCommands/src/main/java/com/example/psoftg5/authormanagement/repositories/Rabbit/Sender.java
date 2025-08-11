package com.example.psoftg5.authormanagement.repositories.Rabbit;

import com.example.psoftg5.authormanagement.model.Author;
import com.google.gson.Gson;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    public void sendCreatedAuthor(Author author) {
        Gson gson = new Gson();
        String entity = gson.toJson(author);
        template.convertAndSend(topic.getName(), "author.created." + "test" , entity);
    }

    public void createdBonusAuthors(String isbn) {
        template.convertAndSend(topic.getName(), "author.bonus.created." + isbn, isbn);
    }
}
