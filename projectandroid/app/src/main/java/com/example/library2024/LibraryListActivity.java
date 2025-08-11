package com.example.library2024;
import android.widget.AdapterView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.adapter.LibraryListAdapter;
import com.example.library2024.dto.Mapper;
import com.example.library2024.handler.JSONHandler;
import com.example.library2024.model.Library;
import com.example.library2024.network.HttpOperation;
import org.json.JSONException;

import java.util.ArrayList;

public class LibraryListActivity extends AppCompatActivity {
    ListView libraryListView;
    ArrayList<Library> libraries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        libraryListView = findViewById(R.id.libraryListView);

        libraryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Library selectedLibrary = libraries.get(position);

                Intent intent = new Intent(LibraryListActivity.this, BookListActivity.class);
                intent.putExtra("libraryId", selectedLibrary.getId());
                startActivity(intent);
            }
        });

        findViewById(R.id.createLibrary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryListActivity.this, CreateLibraryActivity.class);
                startActivity(intent);
            }
        });

        getLibraries();
    }

    private void getLibraries() {
        new Thread() {
            public void run() {
                String response = HttpOperation.getLibraries();

                if (response == null || response.isEmpty()) {
                    Log.e("LibraryListActivity", "Resposta da API está vazia ou nula");
                    return;
                }

                Log.d("LibraryListActivity", "Resposta da API: " + response);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            libraries = Mapper.librariesDTO2libraries(JSONHandler.deSerializeJson2LibrariesDTO(response));
                            setUpLibraryList();
                        } catch (JSONException e) {
                            Log.e("LibraryListActivity", "Erro ao desserializar a resposta JSON", e);
                        }
                    }
                });
            }
        }.start();
    }

    private void setUpLibraryList() {
        LibraryListAdapter adapter = new LibraryListAdapter(this, R.layout.adapter_view_library, libraries);
        libraryListView.setAdapter(adapter);
    }

    public void deleteLibrary(String libraryId, int position) {
        new Thread(() -> {
            String response = HttpOperation.deleteLibrary(libraryId);

            runOnUiThread(() -> {
                if (response != null) {
                    Toast.makeText(LibraryListActivity.this, "Library deleted successfully!", Toast.LENGTH_SHORT).show();
                    libraries.remove(position);
                    setUpLibraryList();
                } else {
                    Toast.makeText(LibraryListActivity.this, "Failed to delete library.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}


