package com.example.library2024.dto;

public class ReviewDTO {
    private String reviewerName;
    private String reviewText;

    public ReviewDTO(String reviewerName, String reviewText) {
        this.reviewerName = reviewerName;
        this.reviewText = reviewText;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
