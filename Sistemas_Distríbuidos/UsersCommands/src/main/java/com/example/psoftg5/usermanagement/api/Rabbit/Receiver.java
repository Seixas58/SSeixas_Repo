package com.example.psoftg5.usermanagement.api.Rabbit;

import com.example.psoftg5.usermanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {

    private final UserService service;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCreatedUser(String userJson) {
        service.saveUser(userJson);
        System.out.println("USER TO BE CREATED - " + userJson);
    }

}
