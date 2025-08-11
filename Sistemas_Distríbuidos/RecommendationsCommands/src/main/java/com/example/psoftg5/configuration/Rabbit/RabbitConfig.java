package com.example.psoftg5.configuration.Rabbit;

import com.example.psoftg5.recommendationmanagement.api.Rabbit.AuthorReceiver;
import com.example.psoftg5.recommendationmanagement.api.Rabbit.BookReceiver;

import com.example.psoftg5.recommendationmanagement.repository.rabbit.RecommendationSender;
import com.example.psoftg5.recommendationmanagement.service.RecommendationService;
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
    public TopicExchange lendingsTopicExchange() {
        return new TopicExchange("lending.topic.exchange");
    }

    @Bean
    public TopicExchange recommendationsTopicExchange() {
        return new TopicExchange("recommendation.topic.exchange");
    }


    @Component
    private static class ReceiverConfig {

        @Autowired
        private final RecommendationService recommendationService;

        public ReceiverConfig(RecommendationService recommendationService) {
            this.recommendationService = recommendationService;
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
        public Queue recommendationCreatedQueue() {
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
        public Binding bindingLendingCreated(TopicExchange lendingsTopicExchange, Queue lendingCreatedQueue) {
            return BindingBuilder.bind(lendingCreatedQueue).to(lendingsTopicExchange).with("lending.created.#");
        }


        @Bean
        public Binding bindingRecommendationCreated(TopicExchange recommendationsTopicExchange, Queue recommendationCreatedQueue) {
            return BindingBuilder.bind(recommendationCreatedQueue).to(recommendationsTopicExchange).with("recommendation.created.#");
        }


        @Bean(name = "RecoCommAuthorReceiver")
        public AuthorReceiver receiver() {
            return new AuthorReceiver(recommendationService);
        }

        @Bean(name = "RecoCommBookReceiver")
        public BookReceiver bookReceiver() {
            return new BookReceiver(recommendationService);
        }
    }

    @Bean
    public RecommendationSender recommendationSender() {return new RecommendationSender();}
}
