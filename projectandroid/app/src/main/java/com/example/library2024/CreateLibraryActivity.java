package com.example.library2024;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.network.HttpOperation;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateLibraryActivity extends AppCompatActivity {
    EditText name, address, openDays, openTime, closeTime;
    Button createLibraryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_library);

        name = findViewById(R.id.libraryName);
        address = findViewById(R.id.libraryAddress);
        openDays = findViewById(R.id.libraryOpenDays);
        openTime = findViewById(R.id.libraryOpenTime);
        closeTime = findViewById(R.id.libraryCloseTime);
        createLibraryButton = findViewById(R.id.createLibraryButton);

        createLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the text directly using class-level variables
                String nameValue = name.getText().toString();
                String addressValue = address.getText().toString();
                String openDaysValue = openDays.getText().toString();
                String openTimeValue = openTime.getText().toString();
                String closeTimeValue = closeTime.getText().toString();

                if (nameValue.isEmpty() || addressValue.isEmpty() || openDaysValue.isEmpty() ) {
                    Toast.makeText(CreateLibraryActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject libraryData = new JSONObject();
                try {
                    libraryData.put("name", nameValue);
                    libraryData.put("address", addressValue);
                    libraryData.put("openDays", openDaysValue);
                    libraryData.put("openTime", openTimeValue);
                    libraryData.put("closeTime", closeTimeValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateLibraryActivity.this, "Error creating JSON data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String jsonData = libraryData.toString();

                new Thread(() -> {
                    String response = HttpOperation.createLibrary(jsonData);
                    runOnUiThread(() -> {
                        if (response != null) {
                            Toast.makeText(CreateLibraryActivity.this, "Library created successfully!", Toast.LENGTH_SHORT).show();
                            finish(); // Closes the current activity
                        } else {
                            Toast.makeText(CreateLibraryActivity.this, "Failed to create library", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }
        });
    }

}

