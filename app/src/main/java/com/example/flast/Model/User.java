package com.example.flast.Model;

public class User {

    private String username;
    private String fullName;
    private String email;
    private String aboutYourself;
    private String imageUrl;
    private String id;

    public User() {
    }

    public User(String username, String fullName, String email, String aboutYourself, String imageUrl, String id) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.aboutYourself = aboutYourself;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutYourself() {
        return aboutYourself;
    }

    public void setAboutYourself(String aboutYourself) {
        this.aboutYourself = aboutYourself;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Add other things



}
