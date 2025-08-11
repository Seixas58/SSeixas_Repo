package com.example.library2024;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.model.Library;
import com.example.library2024.network.HttpOperation;

import static android.content.Intent.getIntent;

public class UpdateLibraryActivity extends AppCompatActivity {

    private EditText libraryNameEditText, libraryAddressEditText, libraryOpenDaysEditText, libraryOpenTimeEditText, libraryCloseTimeEditText;
    private Button updateLibraryButton;
    private String libraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_library);

        libraryNameEditText = findViewById(R.id.libraryNameEditText);
        libraryAddressEditText = findViewById(R.id.libraryAddressEditText);
        libraryOpenDaysEditText = findViewById(R.id.libraryOpenDaysEditText);
        libraryOpenTimeEditText = findViewById(R.id.libraryOpenTimeEditText);
        libraryCloseTimeEditText = findViewById(R.id.libraryCloseTimeEditText);
        updateLibraryButton = findViewById(R.id.updateLibraryButton);

        // Recuperando os dados da biblioteca a ser editada
        libraryId = getIntent().getStringExtra("libraryId");
        Library library = (Library) getIntent().getSerializableExtra("library");

        if (library != null) {
            libraryNameEditText.setText(library.getName());
            libraryAddressEditText.setText(library.getAddress());
            libraryOpenDaysEditText.setText(library.getOpenDays());
            libraryOpenTimeEditText.setText(library.getOpenTime());
            libraryCloseTimeEditText.setText(library.getCloseTime());
        }

        updateLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pegando os dados atualizados
                String name = libraryNameEditText.getText().toString();
                String address = libraryAddressEditText.getText().toString();
                String openDays = libraryOpenDaysEditText.getText().toString();
                String openTime = libraryOpenTimeEditText.getText().toString();
                String closeTime = libraryCloseTimeEditText.getText().toString();

                // Enviando a requisição para atualizar os dados da biblioteca
                new Thread(() -> {
                    String response = HttpOperation.updateLibrary(libraryId, name, address, openDays, openTime, closeTime);

                    runOnUiThread(() -> {
                        if (response != null) {
                            Toast.makeText(UpdateLibraryActivity.this, "Library updated successfully!", Toast.LENGTH_SHORT).show();
                            finish();  // Volta para a tela anterior
                        } else {
                            Toast.makeText(UpdateLibraryActivity.this, "Failed to update library.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }
        });
    }
}
