package com.zijie.ipctest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zijie.aidlipc.CommonService;
import com.zijie.aidlipc.IPC;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,GPSService.class));
        IPC.bindService(CommonService.MyService1.class,"",this);
    }

    public void locationClick(View view) {
        ILocationManager getInstance = IPC.send(CommonService.MyService1.class, ILocationManager.class, "getInstance");
        Toast.makeText(this,  getInstance.getLocation("1",2).getAddress(),Toast.LENGTH_LONG).show();
    }
}
