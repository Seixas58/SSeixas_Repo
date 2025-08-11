package com.example.library2024;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.adapter.BooksListAdapter;
import com.example.library2024.LibraryListActivity;
import com.example.library2024.R;
import com.example.library2024.dto.Mapper;
import com.example.library2024.handler.JSONHandler;
import com.example.library2024.model.Book;
import com.example.library2024.model.Library;
import com.example.library2024.network.HttpOperation;
import com.example.library2024.ui.BookDetail;
import org.json.JSONException;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class BookListActivity extends AppCompatActivity {
    ArrayList<Book> books = new ArrayList<>();
    ListView bookList;
    ArrayList<Library> libraries = new ArrayList<>();


    ImageView imgBackGround;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);


        bookList = (ListView) findViewById(R.id.bookListView);


        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book) books.get(position);
                Intent intent = new Intent(getApplicationContext(), BookDetail.class);
                intent.putExtra("isbn", book.getIsbn());
                startActivity(intent);
            }
        });

        SearchView searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getBooks(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getBooks(s);
                return false;
            }
        });



    }

    private void getBooks(String name){
        new Thread(){
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                String info = HttpOperation.searchBooks(name, 0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            books = Mapper.booksDTO2books(JSONHandler.deSerializeJson2BooksDTO(info));
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        setUpBooksList();
                    }
                });
            }
        }.start();
    }

    private void setUpBooksList(){
        BooksListAdapter adapter = new BooksListAdapter(getApplicationContext(), R.layout.adapter_view_books, books);
        bookList.setAdapter(adapter);
    }
}

