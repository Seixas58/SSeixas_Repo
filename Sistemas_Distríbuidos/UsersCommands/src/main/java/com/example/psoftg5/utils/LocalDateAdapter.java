package com.example.psoftg5.utils;

import com.nimbusds.jose.shaded.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    // Custom TypeAdapter for LocalDate
    public static class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            // Serialize LocalDate to string
            return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            // Deserialize string back to LocalDate
            return json == null || json.isJsonNull() ? null : LocalDate.parse(json.getAsString(), formatter);
        }
    }

    // Method to create Gson with the combined LocalDate adapter
    public static Gson createGsonWithLocalDateAdapter() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
    }
}