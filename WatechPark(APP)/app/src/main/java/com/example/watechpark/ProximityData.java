package com.example.watechpark;

public class ProximityData {

    private long proximity;
    private String timestamp;
    private long slot1A;
    private long slot2B;
    private long slot3C;
    private long slot4D;

    public ProximityData() {
    }

    public ProximityData(long proximity, String timestamp, long slot1A, long slot2B, long slot3C, long slot4D) {
        this.proximity = proximity;
        this.timestamp = timestamp;
        this.slot1A = slot1A;
        this.slot2B = slot2B;
        this.slot3C = slot3C;
        this.slot4D = slot4D;


    }

    public long getProximity() {
        return proximity;
    }

    public void setProximity(long proximity) {
        this.proximity = proximity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getSlot1A() { return slot1A; }

    public void setSlot1A(long slot1A) {
        this.slot1A = slot1A;
    }

    public long getSlot2B() {
        return slot2B;
    }

    public void setSlot2B(long slot2B) {
        this.slot2B = slot2B;
    }

    public long getSlot3C() {
        return slot3C;
    }

    public void setSlot3C(long slot3C) {
        this.slot3C = slot3C;
    }

    public long getSlot4D() {
        return slot4D;
    }

    public void setSlot4D(long slot4D) {
        this.slot4D = slot4D;
    }
}