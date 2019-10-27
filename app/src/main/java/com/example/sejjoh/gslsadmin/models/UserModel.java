package com.example.sejjoh.gslsadmin.models;

public class UserModel {
    String username;
    String email;
    String phoneNo;
    String id;
    String imageUrl;
    private  String status;
    private  String search;

    public UserModel(String username, String email, String phoneNo, String id, String imageUrl, String status, String search) {
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.id = id;
        this.imageUrl = imageUrl;
        this.status = status;
        this.search = search;
    }

    public UserModel() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}