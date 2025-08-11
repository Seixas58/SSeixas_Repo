package com.example.psoftg5.authormanagement.api.Rabbit;

import com.example.psoftg5.authormanagement.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {

    private final AuthorService service;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCreatedAuthor(String authorJson) {
        service.saveAuthor(authorJson);
        System.out.println("AUTHOR TO BE CREATED - " + authorJson);
    }


    @RabbitListener(queues = "#{authorQueue.name}")
    public void receiveUpdatedAuthor(String json) {
        System.out.println("AUTHORS SENT TO BE CREATED " + json);
        service.saveAuthors(json);
    }
}
