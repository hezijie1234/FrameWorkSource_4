package com.zijie.ipctest;

import com.zijie.aidlipc.TaskId;

/**
 * Created by hezijie on 2020/7/22.
 */
@TaskId("getlocation")
public interface ILocationManager {
    Location getLocation(String type1, Integer type2);
}
