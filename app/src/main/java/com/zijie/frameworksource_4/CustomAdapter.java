package com.zijie.frameworksource_4;

import android.content.Context;
import android.graphics.Color;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private Context context;
    private List<Integer> list;

    private static final String TAG = "CustomAdapter";

    public CustomAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        Log.e(TAG, "onCreateViewHolder: " + getItemCount());
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.iv.setImageResource(list.get(position % 8));
        Log.e(TAG, "onBindViewHolder: " + position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 :3000;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public CustomViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
