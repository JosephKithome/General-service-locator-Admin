package com.example.sejjoh.gslsadmin.models;

public class CompModel {
    String computername;
    String compDesc;
    String compImage;
    String checkin;
    String checkout;
    String location;
    String amenities;

    public CompModel(String computername, String compDesc, String compImage, String checkin, String checkout, String location, String amenities) {
        this.computername = computername;
        this.compDesc = compDesc;
        this.compImage = compImage;
        this.checkin = checkin;
        this.checkout = checkout;
        this.location = location;
        this.amenities = amenities;
    }

    public String getComputername() {
        return computername;
    }

    public void setComputername(String computername) {
        this.computername = computername;
    }

    public String getCompDesc() {
        return compDesc;
    }

    public void setCompDesc(String compDesc) {
        this.compDesc = compDesc;
    }

    public String getCompImage() {
        return compImage;
    }

    public void setCompImage(String compImage) {
        this.compImage = compImage;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public CompModel() {


    }
}

