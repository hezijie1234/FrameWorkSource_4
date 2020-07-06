package com.zijie.frameworksource_4.view_pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zijie.frameworksource_4.R;

/**
 * Created by hezijie on 2020/7/2.
 */
public class MyPagerAdapter extends PagerAdapter {

    private Context context;

    public MyPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.linear_item, null);
        ImageView iv = view.findViewById(R.id.tv);
        switch (position % 4){
            case 0:
                iv.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_1));
                break;
            case 1:
                iv.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_2));
                break;
            case 2:
                iv.setImageDrawable(context.getResources().getDrawable(R.drawable.card_bg));
                break;
            case 3:
                iv.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_p_details));
                break;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
