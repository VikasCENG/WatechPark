package com.example.watechpark;

public class Data {
    private String proximity;
    private String timestamp;

    public Data(String proximity, String timestamp) {
        this.proximity = proximity;
        this.timestamp = timestamp;
    }

    public Data() {
    }

    public String getProximity() {
        return proximity;
    }

    public void setProximity(String proximity) {
        this.proximity = proximity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}