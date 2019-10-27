package com.example.sejjoh.gslsadmin.models;

public class MechanicModel {
    String mechanicTitle;
    String description;
    String image;

    public MechanicModel() {
    }

    public MechanicModel(String mechanicTitle, String description, String image) {
        this.mechanicTitle = mechanicTitle;
        this.description = description;
        this.image = image;
    }

    public String getMechanicTitle() {
        return mechanicTitle;
    }

    public void setMechanicTitle(String mechanicTitle) {
        this.mechanicTitle = mechanicTitle;
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
