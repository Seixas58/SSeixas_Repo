package com.example.library2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.adapter.BooksListAdapter;
import com.example.library2024.model.Author;
import com.example.library2024.model.Book;
import com.example.library2024.model.Cover;
import com.example.library2024.model.Library;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LibraryDetailActivity extends AppCompatActivity {

    private ListView bookListView;
    private ArrayList<Book> books = new ArrayList<>();
    private BooksListAdapter booksListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_books);

        bookListView = findViewById(R.id.LibrarybookListView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("library")) {
            Library library = (Library) intent.getSerializableExtra("library");

            if (library != null) {
                TextView libraryName = findViewById(R.id.libraryName);
                TextView libraryAddress = findViewById(R.id.libraryAddress);
                TextView libraryOpenDays = findViewById(R.id.libraryOpenDays);
                TextView libraryOpenTime = findViewById(R.id.libraryOpenTime);
                TextView libraryCloseTime = findViewById(R.id.libraryCloseTime);

                libraryName.setText(library.getName() != null ? library.getName() : "Unknown Name");
                libraryAddress.setText(library.getAddress() != null ? library.getAddress() : "Unknown Address");
                libraryOpenDays.setText(library.getOpenDays() != null ? "Open Days: " + library.getOpenDays() : "Open Days: N/A");
                libraryOpenTime.setText(library.getOpenTime() != null ? "Open Time: " + library.getOpenTime() : "Open Time: N/A");
                libraryCloseTime.setText(library.getCloseTime() != null ? "Close Time: " + library.getCloseTime() : "Close Time: N/A");

                fetchBooksForLibrary(library.getId());
            }
        }
    }

    private void fetchBooksForLibrary(String libraryId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("THREAD_LOG", "Thread started");
                String jsonResponse = getBooksByLibrary(libraryId);

                if (jsonResponse != null && !jsonResponse.isEmpty()) {
                    try {
                        JSONArray jsonArray = new JSONArray(jsonResponse);
                        books.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            // Agora, acessamos o objeto "book" para pegar o "title" e outros dados
                            JSONObject bookObject = jsonObject.getJSONObject("book");

                            // Pegando os dados principais
                            String title = bookObject.getString("title");
                            String author = bookObject.getJSONArray("authors").getJSONObject(0).getString("name"); // Aqui pegamos o primeiro autor (ajuste conforme necessário)

                            // Coletando outros parâmetros
                            String byStatement = bookObject.optString("byStatement", "N/A");
                            String description = bookObject.optString("description", "No description available");
                            String isbn = bookObject.optString("isbn", "N/A");
                            String publishDate = bookObject.optString("publishDate", "N/A");
                            int numberOfPages = bookObject.optInt("numberOfPages", 0);  // Pode ser 0 se não for informado

                            // Para os autores, tratamos como uma lista
                            ArrayList<Author> authors = new ArrayList<>();
                            JSONArray authorsArray = bookObject.getJSONArray("authors");
                            for (int j = 0; j < authorsArray.length(); j++) {
                                JSONObject authorObject = authorsArray.getJSONObject(j);
                                authors.add(new Author(authorObject.getString("name")));
                            }

                            // Para a capa, caso exista um link para a imagem
                            JSONObject coverObject = bookObject.optJSONObject("cover");
                            Cover cover = new Cover(coverObject != null ? coverObject.optString("largeUrl", "") : "");

                            // Criando o livro com o construtor completo
                            books.add(new Book(authors, cover, byStatement, description, isbn, publishDate, title, numberOfPages));
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                booksListAdapter = new BooksListAdapter(LibraryDetailActivity.this, R.layout.adapter_view_books, books);
                                bookListView.setAdapter(booksListAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSON_ERROR", "Error parsing JSON response", e);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LibraryDetailActivity.this, "Failed to fetch books. Response is empty.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private String getBooksByLibrary(String libraryId) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://193.136.62.24/v1/library/" + libraryId + "/book";  // Ensure this URL is correct

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                Log.d("HTTP_RESPONSE", "Response Code: " + response.code());  // Log the response code
                Log.d("HTTP_RESPONSE", "Response Body: " + jsonResponse);  // Log the response body
                return jsonResponse;
            } else {
                Log.e("HTTP_ERROR", "Error: " + response.code() + " - " + response.message());
                return null;
            }
        } catch (IOException e) {
            Log.e("HTTP_ERROR", "Error while fetching books", e);
            return null;
        }
    }
}
