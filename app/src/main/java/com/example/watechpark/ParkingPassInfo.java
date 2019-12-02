package com.example.watechpark;

public class ParkingPassInfo {

    private String passType;
    private int duration;
    private String vaildFrom;
    private String expiryTime;

    public ParkingPassInfo(String passType, int duration, String vaildFrom, String expiryTime) {
        this.passType = passType;
        this.duration = duration;
        this.vaildFrom = vaildFrom;
        this.expiryTime = expiryTime;
    }

    public ParkingPassInfo() {
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getVaildFrom() {
        return vaildFrom;
    }

    public void setVaildFrom(String vaildFrom) {
        this.vaildFrom = vaildFrom;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
}

