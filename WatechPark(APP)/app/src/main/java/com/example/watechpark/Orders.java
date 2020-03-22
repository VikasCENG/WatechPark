package com.example.watechpark;

public class Orders {

    private String orderID;
    private String email;
    private String confirmName;
    private String confirmLocation;
    private double confirmCost;
    private String confirmPassType;
    private int confrimDuration;
    private String confirmValidTime;
    private String confirmExpiryTime;
    private double confirmBalance;
    private String timePurchased;

    public Orders(String orderID, String email, String confirmName, String confirmLocation, double confirmCost, String confirmPassType, int confrimDuration, String confirmValidTime, String confirmExpiryTime, double confirmBalance, String timePurchased) {
        this.orderID = orderID;
        this.email = email;
        this.confirmName = confirmName;
        this.confirmLocation = confirmLocation;
        this.confirmCost = confirmCost;
        this.confirmPassType = confirmPassType;
        this.confrimDuration = confrimDuration;
        this.confirmValidTime = confirmValidTime;
        this.confirmExpiryTime = confirmExpiryTime;
        this.confirmBalance = confirmBalance;
        this.timePurchased = timePurchased;
    }

    public Orders() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmName() {
        return confirmName;
    }

    public void setConfirmName(String confirmName) {
        this.confirmName = confirmName;
    }

    public String getConfirmLocation() {
        return confirmLocation;
    }

    public void setConfirmLocation(String confirmLocation) {
        this.confirmLocation = confirmLocation;
    }

    public double getConfirmCost() {
        return confirmCost;
    }

    public void setConfirmCost(double confirmCost) {
        this.confirmCost = confirmCost;
    }

    public String getConfirmPassType() {
        return confirmPassType;
    }

    public void setConfirmPassType(String confirmPassType) {
        this.confirmPassType = confirmPassType;
    }

    public int getConfrimDuration() {
        return confrimDuration;
    }

    public void setConfrimDuration(int confrimDuration) {
        this.confrimDuration = confrimDuration;
    }

    public String getConfirmValidTime() {
        return confirmValidTime;
    }

    public void setConfirmValidTime(String confirmValidTime) {
        this.confirmValidTime = confirmValidTime;
    }

    public String getConfirmExpiryTime() {
        return confirmExpiryTime;
    }

    public void setConfirmExpiryTime(String confirmExpiryTime) {
        this.confirmExpiryTime = confirmExpiryTime;
    }

    public double getConfirmBalance() {
        return confirmBalance;
    }

    public void setConfirmBalance(double confirmBalance) {
        this.confirmBalance = confirmBalance;
    }

    public String getTimePurchased() {
        return timePurchased;
    }

    public void setTimePurchased(String timePurchased) {
        this.timePurchased = timePurchased;
    }
}