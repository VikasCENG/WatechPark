package com.example.watechpark;

public class Data {

    private String status;
    private int proximity;
    private String timestamp;

    public Data(String status, int proximity, String timestamp) {
        this.status = status;
        this.proximity = proximity;
        this.timestamp = timestamp;
    }

    public Data() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProximity() {
        return proximity;
    }

    public void setProximity(int proximity) {
        this.proximity = proximity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

