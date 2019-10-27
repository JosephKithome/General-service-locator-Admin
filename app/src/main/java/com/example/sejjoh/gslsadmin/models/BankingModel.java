package com.example.sejjoh.gslsadmin.models;

public class BankingModel {
    String bankName;
    String description;
    String image;

    public BankingModel() {
    }

    public BankingModel(String bankName, String description, String image) {
        this.bankName = bankName;
        this.description = description;
        this.image = image;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
