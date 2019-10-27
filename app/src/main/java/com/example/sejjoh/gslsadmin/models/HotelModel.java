package com.example.sejjoh.gslsadmin.models;

public class HotelModel {
    String hotelName;
    String description;
     String image;

    public HotelModel() {
    }

    public HotelModel(String hotelName, String description, String image) {
        this.hotelName = hotelName;
        this.description = description;
        this.image = image;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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
