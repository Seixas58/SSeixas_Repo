package com.example.psoftg5.lendingmanagement.api.Rabbit;

import com.example.psoftg5.lendingmanagement.service.LendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReceiver {
    private final LendingService service;

    @RabbitListener(queues = "#{userCreatedQueue.name}")
    public void receiveCreatedUser(String userJson) {
        service.saveUser(userJson);
        System.out.println("User TO BE CREATED - " + userJson);
    }
}