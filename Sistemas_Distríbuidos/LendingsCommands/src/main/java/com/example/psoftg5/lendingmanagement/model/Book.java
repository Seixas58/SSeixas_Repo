package com.example.psoftg5.lendingmanagement.model;




import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Book {

    @Id
    private String isbn;

    private String title;
    private String description;
    private String coverPhoto;

    public Book(){}

    public Book(String isbn, String title, String description, String coverPhoto){
        this.isbn = isbn;
        this.title = title;
        this.description = description;

        this.coverPhoto = coverPhoto;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getCoverPhoto(){
        return coverPhoto;
    }



    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setCoverPhoto(String coverPhoto){
        this.coverPhoto = coverPhoto;
    }


    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", coverPhoto='" + coverPhoto + '\'' +
                '}';
    }
}
