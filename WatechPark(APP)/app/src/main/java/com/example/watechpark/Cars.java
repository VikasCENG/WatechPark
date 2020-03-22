package com.example.watechpark;

public class Cars {

    private String make, model, color, lplate, timestamp;


    public Cars() {
    }

    public Cars(String make, String model, String color, String lplate, String timestamp) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.lplate = lplate;
        this.timestamp = timestamp;
    }


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLplate() {
        return lplate;
    }

    public void setLplate(String lplate) {
        this.lplate = lplate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}