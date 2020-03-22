package com.example.watechpark;

public class GateStatus {

    private long gStatus;
    private String timestamp;

    public GateStatus(long gStatus, String timestamp) {
        this.gStatus = gStatus;
        this.timestamp = timestamp;
    }

    public GateStatus() {
    }

    public long getgStatus() {
        return gStatus;
    }

    public void setgStatus(long gStatus) {
        this.gStatus = gStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
