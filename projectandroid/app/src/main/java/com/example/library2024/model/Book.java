package com.example.library2024.model;

import java.util.ArrayList;

public class Book {
    private ArrayList<Author> authors;
    private Cover cover;
    private String byStatement;
    private String description;
    private String isbn;
    private String publishDate;
    private String title;
    private int numberOfPages;


    public Book(String title, String author) {}

    public Book(ArrayList<Author> authors, Cover cover, String byStatement, String description, String isbn, String publishDate, String title, int numberOfPages) {
        this.authors = authors;
        this.cover = cover;
        this.byStatement = byStatement;
        this.description = description;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.title = title;
        this.numberOfPages = numberOfPages;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
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

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", byStatement='" + byStatement + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", title='" + title + '\'' +
                ", numberOfPages=" + numberOfPages +
                '}';
    }
}
