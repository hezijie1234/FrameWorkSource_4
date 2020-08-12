package com.zijie.ipctest;

/**
 * Created by hezijie on 2020/7/22.
 */
public class Location {

    public Location() {
    }

    public Location(String address) {
        this.address = address;
    }

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
