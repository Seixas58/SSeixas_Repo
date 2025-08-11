package com.example.psoftg5.bookmanagement.api;

import com.example.psoftg5.bookmanagement.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    private String isbn;
    private String title;
    private String description;
    private Genre genre;
    private List<String> authors;
    private String coverPhoto;

    public String toJsonString() {
        StringBuilder json = new StringBuilder("{");

        json.append("\"isbn\":").append("\"").append(isbn).append("\",");
        json.append("\"title\":").append("\"").append(title).append("\",");
        json.append("\"description\":").append("\"").append(description).append("\",");
        json.append("\"genre\":").append("\"").append(genre).append("\",");

        json.append("\"authors\":").append("[");
        if (authors != null) {
            for (int i = 0; i < authors.size(); i++) {
                json.append("\"").append(authors.get(i)).append("\"");
                if (i < authors.size() - 1) {
                    json.append(",");
                }
            }
        }
        json.append("],");

        json.append("\"coverPhoto\":").append("\"").append(coverPhoto).append("\"");

        json.append("}");
        return json.toString();
    }

    public static CreateBookRequest fromJsonString(String json) {
        CreateBookRequest request = new CreateBookRequest();

        // Remove the outer braces
        String content = json.trim().substring(1, json.length() - 1);
        // Use a map to store key-value pairs
        Map<String, String> keyValueMap = new HashMap<>();

        // Parse the JSON manually
        int length = content.length();
        int i = 0;
        while (i < length) {
            // Find the key
            int keyStart = content.indexOf('"', i) + 1;
            int keyEnd = content.indexOf('"', keyStart);
            String key = content.substring(keyStart, keyEnd);

            // Find the value
            int valueStart = content.indexOf(':', keyEnd) + 1;
            int valueEnd = valueStart;
            char valueStartChar = content.charAt(valueStart);

            if (valueStartChar == '"') { // Value is a string
                valueEnd = content.indexOf('"', valueStart + 1) + 1;
            } else if (valueStartChar == '[') { // Value is an array
                int bracketCount = 1;
                valueEnd++;
                while (bracketCount > 0 && valueEnd < length) {
                    if (content.charAt(valueEnd) == '[') bracketCount++;
                    if (content.charAt(valueEnd) == ']') bracketCount--;
                    valueEnd++;
                }
            } else { // Value is a primitive
                while (valueEnd < length && content.charAt(valueEnd) != ',') {
                    valueEnd++;
                }
            }

            String value = content.substring(valueStart, valueEnd).trim();
            keyValueMap.put(key, value);

            // Move to the next key-value pair
            i = valueEnd + 1;
        }

        // Process key-value pairs
        for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "isbn":
                    request.setIsbn(value.replace("\"", ""));
                    break;
                case "title":
                    request.setTitle(value.replace("\"", ""));
                    break;
                case "description":
                    request.setDescription(value.replace("\"", ""));
                    break;
                case "genre":
                    request.setGenre(new Genre(value.replace("\"", "")));
                    break;
                case "authors":
                    value = value.substring(1, value.length() - 1); // Remove '[' and ']'
                    String[] authorsArray = value.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    List<String> authors = new ArrayList<>();
                    for (String author : authorsArray) {
                        authors.add(author.trim().replace("\"", ""));
                    }
                    request.setAuthors(authors);
                    break;
                case "coverPhoto":
                    request.setCoverPhoto(value.replace("\"", ""));
                    break;
            }
        }

        return request;
    }
}


