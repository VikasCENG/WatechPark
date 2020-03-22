package com.example.watechpark;

public class AdminControl {

    private int adminEntry = 1;
    private int adminEntry0 = 0;

    public AdminControl(int adminEntry, int adminEntry0) {
        this.adminEntry = adminEntry;
        this.adminEntry0 = adminEntry0;
    }

    public AdminControl() {
    }

    public int getAdminEntry() {
        return adminEntry;
    }

    public void setAdminEntry(int adminEntry) {
        this.adminEntry = adminEntry;
    }

    public int getAdminEntry0() {
        return adminEntry0;
    }

    public void setAdminEntry0(int adminEntry0) {
        this.adminEntry0 = adminEntry0;
    }
}
