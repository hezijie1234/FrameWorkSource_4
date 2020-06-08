package com.zijie.frameworksource_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class TestCoodActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private BottomSheetBehavior<RecyclerView> mFrom;
//    private RelativeLayout mRlBottomSheet;
    private List<String> mDatas;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cood);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRlBottomSheet = (RelativeLayout) findViewById(R.id.rl_bottom_sheet);
        mFrom = BottomSheetBehavior.from(mRecyclerView);
        initData();
        initListener();
    }
    private void initData() {
//        WriteLogUtil.init(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
//                LinearLayoutManager.VERTICAL);
//        mRecyclerView.addItemDecoration(itemDecoration);

        mDatas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            String s = String.format("我是第%d个item", i);
            mDatas.add(s);
        }
        mAdapter = new ItemAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {

        mFrom.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e("111", "onStateChanged: newState=" + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.e("111", "onStateChanged: slideOffset=" + slideOffset);
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("111", "onScrolled: dy=" + dy);
            }
        });
    }

}
