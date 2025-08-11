package com.example.library2024.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.library2024.R;
import com.example.library2024.model.Book;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class BooksListAdapter extends ArrayAdapter<Book> {

    private Context mContext;
    private ArrayList<Book> objects;

    public BooksListAdapter(Context context, int resource, ArrayList<Book> objects){
        super(context,resource,objects);
        this.objects = objects;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = (Book) getItem(position);


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(R.layout.adapter_view_books, null);
        }

        String name = book.getTitle();

        TextView bookName = convertView.findViewById(R.id.bookName);
        ImageView bookPhoto = convertView.findViewById(R.id.bookPhoto);


        String imagePath = book.getCover().getSmallUrl();

        bookName.setText(name);
        Picasso.get().load(imagePath).into(bookPhoto);

        return convertView;
    }
}
