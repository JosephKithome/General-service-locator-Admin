package com.example.sejjoh.gslsadmin.models;

public class LaundryModel {
    String loundryName;
    String loudrydesc;
    String loudryImage;

    public LaundryModel(String loundryName, String loudrydesc, String loudryImage) {
        this.loundryName = loundryName;
        this.loudrydesc = loudrydesc;
        this.loudryImage = loudryImage;
    }

    public String getLoundryName() {
        return loundryName;
    }

    public void setLoundryName(String loundryName) {
        this.loundryName = loundryName;
    }

    public String getLoudrydesc() {
        return loudrydesc;
    }

    public void setLoudrydesc(String loudrydesc) {
        this.loudrydesc = loudrydesc;
    }

    public String getLoudryImage() {
        return loudryImage;
    }

    public void setLoudryImage(String loudryImage) {
        this.loudryImage = loudryImage;
    }

    public LaundryModel() {


    }
}
