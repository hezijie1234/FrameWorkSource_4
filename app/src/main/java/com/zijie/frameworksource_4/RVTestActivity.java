package com.zijie.frameworksource_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RVTestActivity extends AppCompatActivity {
    private RecyclerView rv;

    private int[] iv = new int[]{
            R.mipmap.icon_0,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvtest);
        rv = (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new GridLayoutManager(this, 1));

        final List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(iv[0]);
        }
        final CustomAdapter adapter = new CustomAdapter(this, list);
        rv.setAdapter(adapter);
    }
}
