package com.example.psoftg5.lendingmanagement.api.Rabbit;

import com.example.psoftg5.lendingmanagement.service.LendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorReceiver {

    private final LendingService service;

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
}
