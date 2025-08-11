package com.example.library2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.model.Book;
import com.example.library2024.model.Library;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Book> books = new ArrayList<>();
    ListView bookList;
    ArrayList<Library> libraries = new ArrayList<>();
    ListView libraryList;

    ImageView imgBackGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button librariesButton = findViewById(R.id.librariesButton);
        ((View) librariesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibraryListActivity.class);
                startActivity(intent);
            }
        });

        Button booksButton = findViewById(R.id.booksButton);
        ((View) booksButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });
    }
}
