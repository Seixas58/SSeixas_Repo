package com.example.psoftg5.lendingmanagement.api.Rabbit;

import com.example.psoftg5.lendingmanagement.service.LendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookReceiver {
    private final LendingService service;

    @Qualifier("bookTopicExchange")
    @RabbitListener(queues = "#{bookCreatedQueue.name}")
    public void receiveCreatedBook(String bookJson) {
        service.saveBook(bookJson);
        System.out.println("Book TO BE CREATED - " + bookJson);
    }
}
