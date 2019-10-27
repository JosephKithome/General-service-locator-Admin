package com.example.sejjoh.gslsadmin.models;

public class AirportModel {
    String aiport;
    String image;
    String description;

    public AirportModel() {
    }

    public AirportModel(String aiport, String image, String description) {
        this.aiport = aiport;
        this.image = image;
        this.description = description;
    }

    public String getAiport() {
        return aiport;
    }

    public void setAiport(String aiport) {
        this.aiport = aiport;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
