package com.example.library2024.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library2024.CreateReviewActivity;
import com.example.library2024.adapter.AuthorListAdapter;
import com.example.library2024.adapter.BooksListAdapter;
import com.example.library2024.R;
import com.example.library2024.dto.Mapper;
import com.example.library2024.handler.JSONHandler;
import com.example.library2024.model.Author;
import com.example.library2024.model.Book;
import com.example.library2024.network.HttpOperation;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

public class BookDetail extends AppCompatActivity {
    Book book;
    ListView authors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent pIntent = getIntent();
        String isbn = pIntent.getStringExtra("isbn");
        authors = (ListView) findViewById(R.id.authorsList);
        getBookInfo(isbn);
    }

    public void setValues() {

        ImageView bookPhoto = (ImageView) findViewById(R.id.bookPhoto);
        TextView bookByStatement = (TextView) findViewById(R.id.bookByStatement);
        TextView numberOfPages = (TextView) findViewById(R.id.NumberOfPages);
        TextView publishDate = (TextView) findViewById(R.id.PublishDate);
        TextView description = (TextView) findViewById(R.id.Description);

        Picasso.get().load(book.getCover().getMediumUrl()).into(bookPhoto);
        bookByStatement.setText(book.getByStatement());
        if (book.getNumberOfPages() != 0) {
            numberOfPages.setText("0");
        } else {
            numberOfPages.setText("0");
        }
        publishDate.setText(book.getPublishDate());
        description.setText(book.getDescription());

        AuthorListAdapter adapter = new AuthorListAdapter(getApplicationContext(), R.layout.adapter_view_authors, book.getAuthors());
        authors.setAdapter(adapter);


        Button buttonReviews = findViewById(R.id.button_reviews);
        buttonReviews.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.library2024.ReviewsActivity.class);
            intent.putExtra("isbn", book.getIsbn());
            startActivity(intent);
        });

        Button createReviewButton = findViewById(R.id.buttonCreateReview);

        createReviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookDetail.this, CreateReviewActivity.class);
            intent.putExtra("isbn", book.getIsbn());
            startActivity(intent);
        });

    }

    void getBookInfo(String isbn) {
        new Thread(){
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                String info = HttpOperation.getBookByIsbn(isbn);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            book = Mapper.bookDTO2Book(JSONHandler.deSerializeJson2BookDTO(info),JSONHandler.deSerializeJson2AuthorsDTO(info));
                            setValues();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
}

