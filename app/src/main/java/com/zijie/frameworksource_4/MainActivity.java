package com.zijie.frameworksource_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zijie.frameworksource_4.animation.MainActivity2;
import com.zijie.frameworksource_4.view_pager.ViewpagerTestActivity;

public class MainActivity extends AppCompatActivity {

    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.root_view);
        final View viewById = findViewById(R.id.btn_scroll);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ScrollTestActivity.class));
            }
        });
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

    public void testCoor(View view) {
        startActivity(new Intent(this,TestCoodActivity.class));
    }

    public void animaTest(View view) {
        startActivity(new Intent(this, MainActivity2.class));
    }

    public void viewpagerTest(View view) {
        startActivity(new Intent(this, ViewpagerTestActivity.class));
    }
}
