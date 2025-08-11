package com.example.library2024.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.library2024.R;
import com.example.library2024.dto.Mapper;
import com.example.library2024.handler.JSONHandler;
import com.example.library2024.model.Library;
import com.example.library2024.network.HttpOperation;
import org.json.JSONException;

public class LibraryDetail extends AppCompatActivity {
    Library library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        Intent pIntent = getIntent();
        String libraryId = pIntent.getStringExtra("libraryId");

        getLibraryInfo(libraryId);
    }

    public void setValues() {

        TextView libraryName = findViewById(R.id.libraryName);
        TextView libraryAddress = findViewById(R.id.libraryAddress);
        TextView openDays = findViewById(R.id.libraryOpenDays);
        TextView openHours = findViewById(R.id.libraryOpenTime);
        TextView closeHours = findViewById(R.id.libraryCloseTime);

        libraryName.setText(library.getName());
        libraryAddress.setText(library.getAddress());
        openDays.setText(library.getOpenDays());
        openHours.setText(library.getOpenTime());
        closeHours.setText(library.getCloseTime());

    }

    void getLibraryInfo(String libraryId) {
        new Thread(){
            public void run() {
                String info = HttpOperation.getLibraryById(libraryId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            library = Mapper.libraryDTO2library(JSONHandler.deSerializeJson2LibraryDTO(info));
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