package com.zijie.frameworksource_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view) {
        startActivity(new Intent(this,ViewBaseActivity1.class));
    }

    public void recycleViewClick(View view) {

        startActivity(new Intent(this,RVTestActivity.class));
    }

    public void cardviewClick(View view) {
        startActivity(new Intent(this,CardViewTestActivity.class));
    }

    public void flowClick(View view) {
        startActivity(new Intent(this,TestFlowActivity.class));
    }
}
