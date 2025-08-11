package com.example.library2024.model;

public class Review {
    private String reviewerName;
    private String text;


    public Review(String reviewerName, String text) {
        this.reviewerName = reviewerName;
        this.text = text;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
