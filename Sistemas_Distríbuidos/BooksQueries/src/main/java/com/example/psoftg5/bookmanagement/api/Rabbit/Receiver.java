package com.example.psoftg5.bookmanagement.api.Rabbit;

import com.example.psoftg5.bookmanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {

    private final BookService service;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCreatedAuthor(String authorJson) {
        service.saveAuthor(authorJson);
        System.out.println("AUTHOR TO BE CREATED - " + authorJson);
    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void receiveNewGenre(String json) {
        service.saveGenre(json);
        System.out.println("AUTHOR THAT WERE CREATED - " + json);
    }
}

