package com.example.library2024.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.library2024.R;
import com.example.library2024.model.Author;
import com.example.library2024.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuthorListAdapter extends ArrayAdapter<Author> {

    private Context mContext;
    private ArrayList<Author> objects;

    public AuthorListAdapter(Context context, int resource, ArrayList<Author> objects){
        super(context,resource,objects);
        this.objects = objects;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Author author = (Author) getItem(position);


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(R.layout.adapter_view_authors, null);
        }


        TextView authorName = convertView.findViewById(R.id.authorName);
        TextView authorBirthDate = convertView.findViewById(R.id.birthDate);
        TextView authorDeathDate = convertView.findViewById(R.id.deathDate);
        TextView authorBio = convertView.findViewById(R.id.bioAuthor);

        authorName.setText(author.getName());
        if (author.getBirthDate() == null) {
            authorBirthDate.setText("Unknown");
        } else {
            authorBirthDate.setText(author.getBirthDate());
        }
        if (author.getDeathDate() == null) {
            authorDeathDate.setText("Unknown");
        } else {
            authorDeathDate.setText(author.getDeathDate());
        }
        if (author.getBio() == null) {
            authorBio.setText("Unknown");
        } else {
            authorBio.setText(author.getBio());
        }


        return convertView;
    }
}

