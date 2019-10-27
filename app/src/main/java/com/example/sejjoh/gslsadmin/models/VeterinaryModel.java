package com.example.sejjoh.gslsadmin.models;

public class VeterinaryModel {
    String vetName;
    String vetDesc;
     String vetImage;

    public VeterinaryModel(String vetName, String vetDesc, String vetImage) {
        this.vetName = vetName;
        this.vetDesc = vetDesc;
        this.vetImage = vetImage;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getVetDesc() {
        return vetDesc;
    }

    public void setVetDesc(String vetDesc) {
        this.vetDesc = vetDesc;
    }

    public String getVetImage() {
        return vetImage;
    }

    public void setVetImage(String vetImage) {
        this.vetImage = vetImage;
    }

    public VeterinaryModel() {

    }
}
