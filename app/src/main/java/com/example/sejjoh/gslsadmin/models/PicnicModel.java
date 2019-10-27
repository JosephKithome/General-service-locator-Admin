package com.example.sejjoh.gslsadmin.models;

public class PicnicModel {
    String picnicName;
    String description;
    String  image;

    public PicnicModel() {
    }

    public PicnicModel(String picnicName, String description, String image) {
        this.picnicName = picnicName;
        this.description = description;
        this.image = image;
    }

    public String getPicnicName() {
        return picnicName;
    }

    public void setPicnicName(String picnicName) {
        this.picnicName = picnicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
