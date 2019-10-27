package com.example.sejjoh.gslsadmin.models;

public class GymModel {
    String gymName;
    String gymimage;
    String gymdescription;

    public GymModel(String gymName, String gymimage, String gymdescription) {
        this.gymName = gymName;
        this.gymimage = gymimage;
        this.gymdescription = gymdescription;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getGymimage() {
        return gymimage;
    }

    public void setGymimage(String gymimage) {
        this.gymimage = gymimage;
    }

    public String getGymdescription() {
        return gymdescription;
    }

    public void setGymdescription(String gymdescription) {
        this.gymdescription = gymdescription;
    }

    public GymModel() {


    }
}