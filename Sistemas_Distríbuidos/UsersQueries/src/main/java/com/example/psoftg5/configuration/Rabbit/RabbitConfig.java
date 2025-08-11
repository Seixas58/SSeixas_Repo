package com.example.psoftg5.configuration.Rabbit;

import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.services.UserService;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.example.psoftg5.usermanagement.api.Rabbit.Receiver;


@Configuration
public class RabbitConfig {


    @Bean
    public TopicExchange topic() {
        return new TopicExchange("user.topic.exchange");
    }



    @Component
    private static class ReceiverConfig {


        @Autowired
        private final UserService service;

        public ReceiverConfig(UserService service){
            this.service = service;
        }

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }



        @Bean
        public Binding bindingCreated(TopicExchange topic, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("user.created.#");
        }


        @Bean
        public  Receiver receiver() {
            return new Receiver(service);
        }
    }

}