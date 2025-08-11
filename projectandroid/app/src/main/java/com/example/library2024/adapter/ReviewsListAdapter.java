package com.example.library2024.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.library2024.R;
import com.example.library2024.model.Review;

import java.util.ArrayList;

public class ReviewsListAdapter extends ArrayAdapter<Review> {

    public ReviewsListAdapter(Context context, int resource, ArrayList<Review> reviews) {
        super(context, resource, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_reviews, parent, false);
        }

        TextView reviewerName = convertView.findViewById(R.id.reviewerName);
        TextView reviewText = convertView.findViewById(R.id.reviewText);

        reviewerName.setText(review.getReviewerName());
        reviewText.setText(review.getText());

        return convertView;
    }
}
