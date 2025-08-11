package com.example.psoftg5.configuration.Rabbit;

import com.example.psoftg5.bookmanagement.api.Rabbit.BookReceiver;
import com.example.psoftg5.bookmanagement.api.Rabbit.Receiver;
import com.example.psoftg5.bookmanagement.service.BookService;
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



    @Component
    private static class ReceiverConfig {


        @Autowired
        private final BookService bookService;

        public ReceiverConfig(BookService bookService) {
            this.bookService = bookService;
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
        public Queue autoDeleteQueue3() {
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
        public Binding bindingGenreCreated(TopicExchange bookTopicExchange, Queue autoDeleteQueue3) {
            return BindingBuilder.bind(autoDeleteQueue3).to(bookTopicExchange).with("book.genre.#");
        }

        @Bean
        public Receiver receiver() {
            return new Receiver(bookService);
        }
        @Bean
        public BookReceiver bookReceiver() {
            return new BookReceiver(bookService);
        }
    }
}
