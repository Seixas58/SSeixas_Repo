package com.example.psoftg5.usermanagement.api.Rabbit;

import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.services.UserService;
import com.example.psoftg5.utils.LocalDateAdapter;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.nimbusds.jose.shaded.gson.JsonParseException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Receiver {

    private final UserService service;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveCreatedUser(String userJson) {
        try {
            JSONObject jsonObject = new JSONObject(userJson);

            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            String name = jsonObject.getString("name");
            JSONObject dateOfBirthJson = jsonObject.getJSONObject("dateOfBirth");
            int year = dateOfBirthJson.getInt("year");
            int month = dateOfBirthJson.getInt("month");
            int day = dateOfBirthJson.getInt("day");
            LocalDate dateOfBirth = LocalDate.of(year, month, day);

            String phoneNumber = jsonObject.getString("phoneNumber");
            boolean gdpr = jsonObject.getBoolean("gdpr");

            JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");
            String authority = authoritiesArray.getJSONObject(0).getString("authority");

            User user = User.newUser(username, password, name, dateOfBirth, Long.valueOf(phoneNumber), gdpr, authority, "" );
            service.saveUser(user);
            System.out.println("User " + username + "," + "reader number" + user.getReaderNumber() + " saved");
        } catch (JsonParseException e) {
            System.err.println("Failed to parse user JSON: " + e.getMessage());
        }
    }
}
