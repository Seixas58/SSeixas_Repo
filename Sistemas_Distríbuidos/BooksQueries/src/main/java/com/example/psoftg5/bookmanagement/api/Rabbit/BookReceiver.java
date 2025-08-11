package com.example.psoftg5.bookmanagement.api.Rabbit;

import com.example.psoftg5.bookmanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookReceiver {
    private final BookService service;

    @Qualifier("bookTopicExchange")
    @RabbitListener(queues = "#{bookCreatedQueue.name}")
    public void receiveCreatedBook(String bookJson) {
        service.saveBook(bookJson);
        System.out.println("Book TO BE CREATED - " + bookJson);
    }
}
