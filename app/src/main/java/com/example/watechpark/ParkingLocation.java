package com.example.watechpark;

public class ParkingLocation  {

    private String lotName;
    private String lotLocation;
    private double lotDistance;
    private double cost;
    private int lotImage;

    public ParkingLocation(String lotName, String lotLocation, double lotDistance, double cost, int lotImage) {
        this.lotName = lotName;
        this.lotLocation = lotLocation;
        this.lotDistance = lotDistance;
        this.cost = cost;
        this.lotImage = lotImage;
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

    public double getLotDistance() {
        return lotDistance;
    }

    public void setLotDistance(double lotDistance) {
        this.lotDistance = lotDistance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getLotImage() {
        return lotImage;
    }

    public void setLotImage(int lotImage) {
        this.lotImage = lotImage;
    }
}
