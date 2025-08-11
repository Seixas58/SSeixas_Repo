package com.example.library2024.network;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpOperation {


    public static String searchBooksHttpConnection(String name, int page)  {
        String body = null;
        InputStream in = null;
        HttpURLConnection httpConn = null;
        int resCode = -1;

        try {
            URL url = new URL("http://193.136.62.24/v1/search?page="+page+"&query="+name);
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            httpConn = (HttpURLConnection) urlConn;
            //httpConn.setRequestProperty("X-RapidAPI-Key", "d453b4e780msh4f40630bd8d1281p18b1cbjsna08476b0059c");
            //httpConn.setRequestProperty("X-RapidAPI-Host", "shazam-core.p.rapidapi.com");
            httpConn.setRequestProperty("accept", "*/*");
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
                body = readBody(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null)
                httpConn.disconnect();
        }
        return body;
    }
    public static String searchBooks(String name, int page){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/search?page="+page+"&query="+name)
                    .addHeader("accept", "*/*")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string(); // Get the response body as a string
            } else {
                Log.e("GET HTTP ERROR", "HTTP request was not successful. Response code: " + response.code());
            }
        }catch (IOException e) {
            e.printStackTrace();
            Log.e("GET HTTP ERROR","Cannot Search Books",e);
        }
        return null;
    }

    public static String getBookByIsbn(String isbn) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/book/" + isbn + "?persist=true")
                    .addHeader("accept", "*/*")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string(); // Get the response body as a string
            } else {
                Log.e("GET HTTP ERROR", "HTTP request was not successful. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GET HTTP ERROR", "Cannot Get Specific Book", e);
        }
        return null;
    }

    public static Bitmap getImage(String path){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://193.136.62.24" + path)
                    .addHeader("accept", "*/*")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                byte[] imageBytes = response.body().bytes();

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GET HTTP ERROR", "Cannot Get Specific Image", e);
        }
        return null;
    }

    static String readBody(InputStream in){
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String read = br.readLine();
            while(read !=null){
                sb.append(read);
                read = br.readLine();
            }
        }catch (IOException e) { e.printStackTrace(); }
        return sb.toString();
    }

    public static String getLibraryById(String libraryId) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/library" + libraryId)
                    .addHeader("accept", "*/*")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                Log.e("GET HTTP ERROR", "HTTP request was not successful. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GET HTTP ERROR", "Cannot Get Library", e);
        }
        return null;
    }

    public static String getLibraries() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/library")
                    .addHeader("accept", "*/*")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string(); // Get the response body as a string
            } else {
                Log.e("GET HTTP ERROR", "HTTP request was not successful. Response code: " + response.code());
            }
        }catch (IOException e) {
            e.printStackTrace();
            Log.e("GET HTTP ERROR", "Cannot Get Libraries", e);
        }
        return null;
    }

    public static String createLibrary(String libraryDataJson) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"),
                    libraryDataJson
                        );

            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/library")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("accept", "*/*")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                Log.e("POST HTTP ERROR", "HTTP request was not successful. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("POST HTTP ERROR", "Cannot Create Library", e);
        }
        return null;
    }
    public static String deleteLibrary(String libraryId) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/library/" + libraryId)
                    .delete()
                    .addHeader("accept", "*/*")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                Log.e("DELETE HTTP ERROR", "HTTP request was not successful. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DELETE HTTP ERROR", "Cannot Delete Library", e);
        }
        return null;
    }

    public static String updateLibrary(String libraryId, String name, String address, String openDays, String openTime, String closeTime) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            JSONObject json = new JSONObject();
            try {
                json.put("name", name);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            json.put("address", address);
            json.put("openDays", openDays);
            json.put("openTime", openTime);
            json.put("closeTime", closeTime);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/library/" + libraryId)
                    .put(body)
                    .addHeader("accept", "*/*")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                Log.e("PUT HTTP ERROR", "HTTP request was not successful. Response code: " + response.code());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("PUT HTTP ERROR", "Cannot update Library", e);
        }
        return null;
    }

   /* private String getBooksByLibrary(int libraryId) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://example.com/v1/library/" + libraryId + "/book";  // Update this URL with the actual endpoint

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json") // Assuming the server accepts JSON responses
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                Log.e("GET HTTP ERROR", "HTTP request failed. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GET HTTP ERROR", "Error while fetching books", e);
        }

        return null;
    }

    */
   public static String getReviewsByIsbn(String isbn) {
       try {
           OkHttpClient client = new OkHttpClient().newBuilder().build();
           Request request = new Request.Builder()
                   .url("http://193.136.62.24/v1/book/" + isbn + "/review")
                   .addHeader("accept", "*/*")
                   .build();
           Response response = client.newCall(request).execute();
           if (response.isSuccessful()) {
               return response.body().string();
           } else {
               Log.e("GET HTTP ERROR", "HTTP request falhou. Código de resposta: " + response.code());
           }
       } catch (IOException e) {
           e.printStackTrace();
           Log.e("GET HTTP ERROR", "Erro ao buscar reviews", e);
       }
       return null;
   }

    public static String createReview(String isbn, String reviewerName, String reviewText) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            JSONObject json = new JSONObject();
            json.put("reviewerName", reviewerName);
            json.put("reviewText", reviewText);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

            Request request = new Request.Builder()
                    .url("http://193.136.62.24/v1/book/" + isbn + "/review")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("accept", "*/*")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                Log.e("POST HTTP ERROR", "HTTP request falhou. Código de resposta: " + response.code());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("POST HTTP ERROR", "Erro ao criar a review", e);
        }
        return null;
    }



}
