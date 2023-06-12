package com.example.crudoperationwithfirebase3;

public class User {
    private String title;
    private String description;
    private String date;
    private String ImageUri;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public User(String title, String description, String date, String imageUri) {
        this.title = title;
        this.description = description;
        this.date = date;
        ImageUri = imageUri;
    }
}


