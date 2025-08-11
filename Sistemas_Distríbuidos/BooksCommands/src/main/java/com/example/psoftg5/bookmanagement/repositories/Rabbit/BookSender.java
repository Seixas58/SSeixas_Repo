package com.example.psoftg5.bookmanagement.repositories.Rabbit;

import com.example.psoftg5.bookmanagement.api.CreateBookRequest;
import com.example.psoftg5.bookmanagement.api.NewAuthorAndBookRequest;
import com.example.psoftg5.bookmanagement.model.*;
import com.example.psoftg5.bookmanagement.repositories.BookDBRepository;
import com.google.gson.Gson;
import org.hibernate.Hibernate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;


public class BookSender {
    @Autowired
    private RabbitTemplate template;



    @Autowired
    @Qualifier("bookTopicExchange")
    private TopicExchange bookTopic;

    @Autowired
    private DirectExchange authorExchange;

    public void sendCreatedBook(Book book) {
        Gson gson = new Gson();
        String entity = gson.toJson(book);
        template.convertAndSend(bookTopic.getName(), "book.created.", entity);

    }

    public void sendTempBook(TempBook entity) {
        Set<Author> author = new HashSet<>();

        for(TempAuthor auth : entity.getAuthors()) {
            author.add(new Author(auth.getName(), auth.getShortBio(),""));
        }

        Book book = new Book(entity.getIsbn(),entity.getTitle(), entity.getDescription(), new Genre(entity.getGenres()), author, entity.getCoverPhoto());

        Gson gson = new Gson();

        String json = gson.toJson(book);

        template.convertAndSend(bookTopic.getName(), "book.created.", json);

    }

    public void sendCreatedAuthor(NewAuthorAndBookRequest request) {
        Gson gson = new Gson();
        String entity = gson.toJson(request);
        template.convertAndSend(authorExchange.getName(), "author.created", entity);
    }

    public void sendCreatedGenre(Genre genre) {
        Gson gson = new Gson();
        String entity = gson.toJson(genre);
        template.convertAndSend(bookTopic.getName(), "book.genre." + genre.getGenreName(), entity);
    }

}
