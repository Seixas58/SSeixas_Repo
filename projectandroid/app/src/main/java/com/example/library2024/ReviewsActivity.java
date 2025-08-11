package com.example.library2024;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.R;
import com.example.library2024.adapter.ReviewsListAdapter;
import com.example.library2024.model.Review;
import com.example.library2024.network.HttpOperation;
import com.example.library2024.dto.Mapper;
import com.example.library2024.handler.JSONHandler;
import org.json.JSONException;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    private ListView reviewsListView;
    private ArrayList<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        reviewsListView = findViewById(R.id.reviewsListView);

        String isbn = getIntent().getStringExtra("isbn");
        getReviews(isbn);
    }

    private void getReviews(String isbn) {
        new Thread(() -> {
            String response = HttpOperation.getReviewsByIsbn(isbn);
            if (response != null) {
                try {
                    reviews = Mapper.reviewsDTO2Reviews(JSONHandler.deSerializeJson2ReviewsDTO(response));
                    runOnUiThread(() -> setUpReviewsList());
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Erro ao carregar reviews", Toast.LENGTH_SHORT).show());
                }
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Falha na conexão", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void setUpReviewsList() {
        ReviewsListAdapter adapter = new ReviewsListAdapter(this, R.layout.adapter_view_reviews, reviews);
        reviewsListView.setAdapter(adapter);
    }
}
