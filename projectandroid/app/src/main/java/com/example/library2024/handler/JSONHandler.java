package com.example.library2024.handler;

import com.example.library2024.dto.AuthorDTO;
import com.example.library2024.dto.BookDTO;
import com.example.library2024.dto.LibraryDTO;
import com.example.library2024.dto.ReviewDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONHandler {

    public static BookDTO deSerializeJson2BookDTO(String info) throws JSONException{
        JSONObject respJSON = new JSONObject(info);
        JSONObject coverJSON = respJSON.getJSONObject("cover");
        String largeUrl = coverJSON.optString("largeUrl");
        String mediumUrl = coverJSON.optString("mediumUrl");
        String smallUrl = coverJSON.optString("smallUrl");
        String byStatement = respJSON.optString("byStatement");
        String description = respJSON.optString("description");
        String isbn = respJSON.optString("isbn");
        int numberOfPages = respJSON.optInt("numberOfPages");
        String publishDate = respJSON.optString("publishDate");
        String title = respJSON.optString("title");


        BookDTO data = new BookDTO(byStatement,description,isbn,numberOfPages,publishDate,title,largeUrl,mediumUrl,smallUrl);
        return data;
    }

    public static ArrayList<BookDTO> deSerializeJson2BooksDTO(String info) throws JSONException{
        ArrayList<BookDTO> bookDTOS = new ArrayList<>();
        JSONArray array = new JSONArray(info);
        for (int i = 0; i< array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            BookDTO dto = deSerializeJson2BookDTO(obj.toString());
            bookDTOS.add(dto);
        }

        return bookDTOS;
    }
    public static ArrayList<AuthorDTO> deSerializeJson2AuthorsDTO(String info) throws JSONException{
        ArrayList<AuthorDTO> authorsDTO = new ArrayList<>();
        JSONObject respJSON = new JSONObject(info);
        JSONArray jsonAuthors = respJSON.getJSONArray("authors");
        for(int i = 0; i<jsonAuthors.length();i++) {
             AuthorDTO authorDTO = new AuthorDTO();
             JSONObject object = jsonAuthors.getJSONObject(i);
             authorDTO.setName(object.getString("name"));
             authorDTO.setBirthDate(object.getString("birthDate"));
             authorDTO.setDeathDate(object.getString("deathDate"));
             authorDTO.setBio(object.getString("bio"));
             authorsDTO.add(authorDTO);
        }

        return authorsDTO;
    }

    public static LibraryDTO deSerializeJson2LibraryDTO(String info) throws JSONException {
        JSONObject respJSON = new JSONObject(info);

        String id = respJSON.optString("id");
        String name = respJSON.optString("name");
        String address = respJSON.optString("address");
        String openDays = respJSON.optString("openDays");
        String openTime = respJSON.optString("openHours");
        String closeTime = respJSON.optString("closeHours");

      LibraryDTO libraryDTO = new LibraryDTO(id, name, address, openDays, openTime, closeTime);

        return libraryDTO;
    }

    public static ArrayList<LibraryDTO> deSerializeJson2LibrariesDTO(String info) throws JSONException {
        ArrayList<LibraryDTO> libraryDTOS = new ArrayList<>();
        JSONArray array = new JSONArray(info);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            LibraryDTO dto = deSerializeJson2LibraryDTO(obj.toString());
            libraryDTOS.add(dto);
        }

        return libraryDTOS;
    }
    public static ArrayList<ReviewDTO> deSerializeJson2ReviewsDTO(String info) throws JSONException {
        ArrayList<ReviewDTO> reviewDTOS = new ArrayList<>();
        JSONArray array = new JSONArray(info);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String reviewerName = obj.optString("reviewerName");
            String reviewText = obj.optString("reviewText");
            reviewDTOS.add(new ReviewDTO(reviewerName, reviewText));
        }

        return reviewDTOS;
    }

    public static ReviewDTO deSerializeJson2ReviewDTO(String info) throws JSONException {
        JSONObject obj = new JSONObject(info);
        String reviewerName = obj.optString("reviewerName");
        String reviewText = obj.optString("reviewText");

        return new ReviewDTO(reviewerName, reviewText);
    }
}
