package com.zijie.ipctest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zijie.aidlipc.IPC;

/**
 * Created by hezijie on 2020/7/22.
 */
public class GPSService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //先实现定位功能
        LocationManager.getInstance().setLocation(new Location("仙桃市"));
        //定位功能完成后，去注册一下，把这个定位信息服务提供出去。
        IPC.register(LocationManager.class);
    }
}
