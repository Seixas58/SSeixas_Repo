package com.example.library2024.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.library2024.LibraryDetailActivity;
import com.example.library2024.R;
import com.example.library2024.UpdateLibraryActivity;
import com.example.library2024.model.Library;
import com.example.library2024.LibraryListActivity;

import java.util.ArrayList;

public class LibraryListAdapter extends ArrayAdapter<Library> {

    private Context mContext;
    private ArrayList<Library> libraries;

    public LibraryListAdapter(Context context, int resource, ArrayList<Library> libraries) {
        super(context, resource, libraries);
        this.libraries = libraries;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Library library = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(R.layout.adapter_view_library, parent, false);
        }

        TextView libraryName = convertView.findViewById(R.id.libraryName);
        TextView libraryAddress = convertView.findViewById(R.id.libraryAddress);
        TextView libraryOpenDays = convertView.findViewById(R.id.libraryOpenDays);
        TextView libraryOpenHours = convertView.findViewById(R.id.libraryOpenTime);
        TextView libraryCloseHours = convertView.findViewById(R.id.libraryCloseTime);
        Button deleteLibraryButton = convertView.findViewById(R.id.deleteLibraryButton);
        Button updateLibraryButton = convertView.findViewById(R.id.updateLibraryButton);

        if (library != null) {
            libraryName.setText(library.getName() != null ? library.getName() : "Unknown Name");
            libraryAddress.setText(library.getAddress() != null ? library.getAddress() : "Unknown Address");
            libraryOpenDays.setText(library.getOpenDays() != null ? "Open Days: " + library.getOpenDays() : "Open Days: N/A");
            libraryOpenHours.setText(library.getOpenTime() != null ? "Open Hours: " + library.getOpenTime() : "Open Hours: N/A");
            libraryCloseHours.setText(library.getCloseTime() != null ? "Close Hours: " + library.getCloseTime() : "Close Hours: N/A");

            libraryName.setOnClickListener(v -> {
                if (library != null) {
                    Intent intent = new Intent(mContext, LibraryDetailActivity.class);
                    intent.putExtra("library", library);
                    mContext.startActivity(intent);
                }
            });

            deleteLibraryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (library != null) {
                        ((LibraryListActivity) mContext).deleteLibrary(library.getId(), position);
                    }
                }
            });

            updateLibraryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (library != null) {
                        Intent intent = new Intent(mContext, UpdateLibraryActivity.class);
                        intent.putExtra("libraryId", library.getId());
                        intent.putExtra("library", library);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        return convertView;
    }
}

