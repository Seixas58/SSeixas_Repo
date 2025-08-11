package com.example.psoftg5.lendingmanagement.repository.rabbit;

import com.example.psoftg5.lendingmanagement.model.Lending;
import com.example.psoftg5.utils.Utils;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;

public class LendingSender {

    @Autowired
    private RabbitTemplate template;



    @Autowired
    @Qualifier("lendingsTopicExchange")
    private TopicExchange lenderExchange;

    public void sendLending(Lending lending) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, Utils.createLocalDateTypeAdapter())
                .create();
        String json = gson.toJson(lending);
        template.convertAndSend(lenderExchange.getName(),"lending.created."+lending.getUser(), json);
    }
}
