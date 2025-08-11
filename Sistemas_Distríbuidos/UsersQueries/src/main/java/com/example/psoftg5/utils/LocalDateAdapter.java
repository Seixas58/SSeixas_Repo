package com.example.psoftg5.utils;



import com.nimbusds.jose.shaded.gson.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    // Serializador para LocalDate
    public static JsonSerializer<LocalDate> getLocalDateSerializer() {
        return (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.format(formatter));
    }

    // Desserializador para LocalDate
    public static JsonDeserializer<LocalDate> getLocalDateDeserializer() {
        return (json, typeOfT, context) -> {
            if (json == null) {
                return null;
            }

            if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
                return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
            }

            else if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                if (jsonObject.has("dateOfBirth")) {
                    String dateOfBirth = jsonObject.get("dateOfBirth").getAsString();
                    return LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE);
                }
            }

            throw new JsonParseException("Expected a String or JsonObject with 'dateOfBirth' property for LocalDate");
        };
    }

    // Método para criar Gson com adaptadores de LocalDate
    public static Gson createGsonWithLocalDateAdapter() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, getLocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, getLocalDateDeserializer())
                .create();
    }
}
