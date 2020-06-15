package com.zijie.frameworksource_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ScrollTestActivity extends AppCompatActivity {

    private View viewById;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        viewById = findViewById(R.id.btn_scroll);
        rootView = findViewById(R.id.root_view);
        viewById.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               rootView.scrollBy(50,50);
           }
       });
    }
}
