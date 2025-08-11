package com.example.psoftg5.configuration.Rabbit;

import com.example.psoftg5.authormanagement.service.AuthorService;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.example.psoftg5.authormanagement.api.Rabbit.Receiver;
import com.example.psoftg5.authormanagement.repositories.Rabbit.Sender;


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
    public DirectExchange directTempBookAuthors() {
        return new DirectExchange("book.authors.created");
    }
    @Component
    private static class ReceiverConfig {

        @Autowired
        private final AuthorService authorService;


        public ReceiverConfig(AuthorService authorService) {
            this.authorService = authorService;
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
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue authorQueue() {
            return new Queue("authors-created");
        }

        @Bean
        public Binding bindingCreated(TopicExchange topic, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("author.created.#");
        }

        @Bean
        public Binding bindingCreated2(TopicExchange topic, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(topic).with("author.bonus.created.#");
        }


        @Bean
        public Binding bindingBookAuthorCreated(DirectExchange directTempBookAuthors, Queue authorQueue) {
            return BindingBuilder.bind(authorQueue).to(directTempBookAuthors).with("author.created");
        }

        @Bean
        public  Receiver receiver() {
            return new Receiver(authorService);
        }
       /* @Bean
        public BookReceiver bookReceiver() {
            return new BookReceiver(bookService);
        }*/
    }

    @Bean
    public Sender sender() {
        return new Sender();
    }

}
