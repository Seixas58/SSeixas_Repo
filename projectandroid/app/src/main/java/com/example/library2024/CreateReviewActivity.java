package com.example.library2024;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.network.HttpOperation;

public class CreateReviewActivity extends AppCompatActivity {

    private EditText reviewerNameEditText;
    private EditText reviewTextEditText;
    private Button submitReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        reviewerNameEditText = findViewById(R.id.editTextReviewerName);
        reviewTextEditText = findViewById(R.id.editTextReviewText);
        submitReviewButton = findViewById(R.id.buttonSubmitReview);

        String isbn = getIntent().getStringExtra("isbn");

        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewerName = reviewerNameEditText.getText().toString();
                String reviewText = reviewTextEditText.getText().toString();

                if (reviewerName.isEmpty() || reviewText.isEmpty()) {
                    Toast.makeText(CreateReviewActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(() -> {
                    String response = HttpOperation.createReview(isbn, reviewerName, reviewText);
                    runOnUiThread(() -> {
                        if (response != null) {
                            Toast.makeText(CreateReviewActivity.this, "Review criada com sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(CreateReviewActivity.this, "Erro ao criar a review!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }
        });
    }
}
