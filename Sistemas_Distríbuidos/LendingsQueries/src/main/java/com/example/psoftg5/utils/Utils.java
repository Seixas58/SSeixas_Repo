package com.example.psoftg5.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nimbusds.jose.shaded.gson.TypeAdapter;
import com.nimbusds.jose.shaded.gson.stream.JsonReader;
import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Utils {

    @Autowired
    private JwtDecoder jwtDecoder;


    public static LocalDateTime parseString(String stringDate) {
        String[] parts = stringDate.split("-");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid date format");
        }
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);


        return LocalDateTime.of(year,month,day,0,0);
    }

    public String getEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        String newToken = token.replace("Bearer ", "");
        Jwt decodedToken = this.jwtDecoder.decode(newToken);
        String subject = (String) decodedToken.getClaims().get("sub");

        String[] parts = subject.split(",");
        if (parts.length > 1) {
            return parts[1];
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Username!");
        }
    }

    public static TypeAdapter<LocalDate> createLocalDateTypeAdapter(){
        return new TypeAdapter<LocalDate>() {
            @Override
            public void write(JsonWriter out, LocalDate value) throws IOException {
                out.value(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
            }

            @Override
            public LocalDate read(JsonReader in) throws IOException {
                return LocalDate.parse(in.nextString(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
        };
    }
}
