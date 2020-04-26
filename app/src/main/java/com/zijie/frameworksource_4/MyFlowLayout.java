package com.zijie.frameworksource_4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezijie on 2020/4/26.
 */
public class MyFlowLayout extends ViewGroup {
    public static final String TAG = "111";
    private List<View> curLineViews;
    private List<List<View>> allLines;
    private List<Integer> lineHeight;
    public MyFlowLayout(Context context) {
        this(context, null);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "MyFlowLayout: "  );
    }

    private void init() {
        curLineViews = new ArrayList<>();
        allLines = new ArrayList<>();
        lineHeight = new ArrayList<>();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int curWidth = 0;//FlowLayout当前的宽度
        int curHeight = 0;//FlowLayout当前的高度
        int curLineHeight = 0;//当前行的高度
        int curLineWidth = 0;//当前行的宽度
        init();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //如果加上这个子view的宽度大于布局的宽度，则需要换行
            if (curLineWidth + child.getMeasuredWidth() > widthSize) {
                allLines.add(curLineViews);
                //记录每一行的行高
                Log.e(TAG, "这一行的高度" + curLineHeight );
                lineHeight.add(curLineHeight);
                curLineViews = new ArrayList<>();
                //换行时流式布局的高度需要累加
                curHeight += curLineHeight;
                //换行时流式布局的宽度要取最大值
                curWidth = Math.max(curWidth,curLineWidth);
                curLineHeight = 0;
                curLineWidth = 0;
            }
            //当前行的宽度需要累加
            curLineWidth += child.getMeasuredWidth();
            //当前行的高度需要取最大高度
            curLineHeight = Math.max(curLineHeight,child.getMeasuredHeight());
            curLineViews.add(child);
            //将最后一行加入
            if (i == getChildCount() - 1){
                allLines.add(curLineViews);
                lineHeight.add(curLineHeight);
                Log.e(TAG, "这一行的高度" + curLineHeight );
                curHeight += curLineHeight;
                curWidth = Math.max(curWidth,curLineWidth);
            }
        }
        Log.e(TAG, "onMeasure: 宽度=" + curWidth + "------高度=" + curHeight );
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : curWidth,heightMode == MeasureSpec.EXACTLY ? heightSize : curHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l = 0;
        t = 0;
        r = 0;
        b = 0;
        for (int i = 0; i < allLines.size(); i++) {
            List<View> curViews = allLines.get(i);
            l = 0;
            r = 0;
            if (i > 0){
                t += lineHeight.get(i - 1);
                b += lineHeight.get(i - 1);
            }

            for (int j = 0; j < curViews.size(); j++) {
                View child = curViews.get(j);
                if (j > 0){
                    l += curViews.get(j - 1).getMeasuredWidth();
                }
                r += child.getMeasuredWidth();
                child.layout(l,t,r,b + child.getMeasuredHeight());
            }
        }
    }
}