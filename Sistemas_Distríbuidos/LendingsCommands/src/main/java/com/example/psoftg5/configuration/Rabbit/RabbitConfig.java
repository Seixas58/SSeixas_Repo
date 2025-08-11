package com.example.psoftg5.configuration.Rabbit;

import com.example.psoftg5.lendingmanagement.api.Rabbit.AuthorReceiver;
import com.example.psoftg5.lendingmanagement.api.Rabbit.BookReceiver;
import com.example.psoftg5.lendingmanagement.api.Rabbit.UserReceiver;
import com.example.psoftg5.lendingmanagement.repository.rabbit.LendingSender;
import com.example.psoftg5.lendingmanagement.service.LendingService;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("author.topic.exchange");
    }

    @Bean
    public TopicExchange bookTopicExchange() {
        return new TopicExchange("book.topic.exchange");
    }
    @Bean
    public TopicExchange userTopicExchange() {
        return new TopicExchange("user.topic.exchange");
    }

    @Bean
    public TopicExchange lendingsTopicExchange() {
        return new TopicExchange("lending.topic.exchange");
    }



    @Component
    private static class ReceiverConfig {

        @Autowired
        private final LendingService lendingService;

        public ReceiverConfig(LendingService lendingService) {
            this.lendingService = lendingService;
        }

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue bookCreatedQueue() {
            return new AnonymousQueue();
        }
        @Bean
        public Queue userCreatedQueue() {
            return new AnonymousQueue();
        }
        @Bean
        public Queue lendingCreatedQueue() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingCreated(TopicExchange topic, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("author.created.#");
        }

        @Bean
        public Binding bindingBookCreated(TopicExchange bookTopicExchange, Queue bookCreatedQueue) {
            return BindingBuilder.bind(bookCreatedQueue).to(bookTopicExchange).with("book.created.#");
        }
        @Bean
        public Binding bindingUserCreated(TopicExchange userTopicExchange, Queue userCreatedQueue) {
            return BindingBuilder.bind(userCreatedQueue).to(userTopicExchange).with("user.created.#");
        }

        @Bean
        public Binding bindingLendingCreated(TopicExchange lendingsTopicExchange, Queue lendingCreatedQueue) {
            return BindingBuilder.bind(lendingCreatedQueue).to(lendingsTopicExchange).with("lending.created.#");
        }

        @Bean
        public AuthorReceiver receiver() {
            return new AuthorReceiver(lendingService);
        }

        @Bean
        public BookReceiver bookReceiver() {
            return new BookReceiver(lendingService);
        }

        @Bean
        public UserReceiver UserRecei4ver() {
            return new UserReceiver(lendingService);
        }
    }

    @Bean
    public LendingSender lendingSender() {return new LendingSender();}
}
