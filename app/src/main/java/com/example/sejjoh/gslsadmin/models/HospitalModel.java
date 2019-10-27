package com.example.sejjoh.gslsadmin.models;

public class HospitalModel {
    String hospital;
    String description;
            String image;

    public HospitalModel() {
    }

    public HospitalModel(String hospital, String description, String image) {
        this.hospital = hospital;
        this.description = description;
        this.image = image;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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
