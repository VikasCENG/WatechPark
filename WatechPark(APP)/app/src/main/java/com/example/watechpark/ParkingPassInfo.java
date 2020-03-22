package com.example.watechpark;

import java.io.Serializable;

public class ParkingPassInfo{

    private String lotName;
    private String lotLocation;
    private double lotCost;
    private String passType;
    private int duration;
    private String vaildFrom;
    private String expiryTime;
    private int balance = 200;


    public ParkingPassInfo(String lotName, String lotLocation, double lotCost, String passType, int duration, String vaildFrom, String expiryTime, int balance) {
        this.lotName = lotName;
        this.lotLocation = lotLocation;
        this.lotCost = lotCost;
        this.passType = passType;
        this.duration = duration;
        this.vaildFrom = vaildFrom;
        this.expiryTime = expiryTime;
        this.balance = balance;
    }

    public ParkingPassInfo() {
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getLotLocation() {
        return lotLocation;
    }

    public void setLotLocation(String lotLocation) {
        this.lotLocation = lotLocation;
    }

    public double getLotCost() {
        return lotCost;
    }

    public void setLotCost(double lotCost) {
        this.lotCost = lotCost;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}