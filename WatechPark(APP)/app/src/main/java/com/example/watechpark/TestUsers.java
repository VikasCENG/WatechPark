package com.example.watechpark;

public class TestUsers {

    private String imageID;
    private String fullName;
    private String phone;
    private String email;
    private String username;
    private String timestamp;

    public TestUsers(String imageID, String fullName, String phone, String email, String username, String timestamp) {
        this.imageID = imageID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.timestamp = timestamp;
    }

    public TestUsers() {
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
