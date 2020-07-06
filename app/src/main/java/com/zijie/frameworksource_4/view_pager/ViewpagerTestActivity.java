package com.zijie.frameworksource_4.view_pager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.zijie.frameworksource_4.R;

public class ViewpagerTestActivity extends AppCompatActivity {

    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_test);
        viewpager = findViewById(R.id.view_pager);
        viewpager.setPageMargin(30);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(new MyPagerAdapter(this));

        //让缓存变成倾斜状态
        viewpager.setPageTransformer(true, new PageTransform());
        viewpager.setCurrentItem(4 * 100);
    }
}
