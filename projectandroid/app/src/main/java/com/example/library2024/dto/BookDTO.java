package com.example.library2024.dto;

import java.util.ArrayList;

public class BookDTO {
    private String byStatement;
    private String description;
    private String isbn;
    private int numberOfPages;
    private String publishDate;
    private String title;
    private String largeUrl;
    private String mediumUrl;
    private String smallUrl;


    public BookDTO() {}

    public BookDTO(String byStatement, String description, String isbn, int numberOfPages, String publishDate, String title, String largeUrl, String mediumUrl, String smallUrl) {
        this.byStatement = byStatement;
        this.description = description;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
        this.publishDate = publishDate;
        this.title = title;
        this.largeUrl = largeUrl;
        this.mediumUrl = mediumUrl;
        this.smallUrl = smallUrl;
    }

    public String getByStatement() {
        return byStatement;
    }

    public void setByStatement(String byStatement) {
        this.byStatement = byStatement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
