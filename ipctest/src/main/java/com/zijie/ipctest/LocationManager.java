package com.zijie.ipctest;

import com.zijie.aidlipc.TaskId;

/**
 * Created by hezijie on 2020/7/22.
 */
@TaskId("getlocation")
public class LocationManager {

    private static final LocationManager locationManager = new LocationManager();
    public static LocationManager getInstance(){
        return locationManager;
    }

    private Location location;

    public Location getLocation(String type1, Integer type2) {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
