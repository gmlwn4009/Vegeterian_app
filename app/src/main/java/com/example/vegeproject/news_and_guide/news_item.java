package com.example.vegeproject.news_and_guide;

public class news_item {
    String title;
    String name;
    String email;
    String photo;

    public news_item(){};

    public news_item(String title, String name, String email, String photo) {
        this.title = title;
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }
}