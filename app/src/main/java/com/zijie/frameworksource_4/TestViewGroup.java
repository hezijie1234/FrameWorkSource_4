package com.zijie.frameworksource_4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hezijie on 2020/4/13.
 */
public class TestViewGroup extends ViewGroup {

    private int offSet = 0;
    public static final String TAG = "111";
    public TestViewGroup(Context context) {
        super(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        //根据父控件的测量参数，测量子view
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = childAt.getLayoutParams();
            int childMeasureSpec = getChildMeasureSpec(widthMeasureSpec, childAt.getPaddingLeft() + childAt.getPaddingRight() , layoutParams.width);
            int childMeasureSpec1 = getChildMeasureSpec(heightMeasureSpec, childAt.getPaddingTop() + childAt.getPaddingBottom(), layoutParams.height);
            childAt.measure(childMeasureSpec,childMeasureSpec1);
        }
        //根据自己的测量参数确定自己的大小
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                measureWidth = width;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED://
                for (int i = 0; i < count; i++) {
                    View childAt = getChildAt(i);
                    measureWidth = Math.max(measureWidth,i * offSet + childAt.getMeasuredWidth());
                }
                break;
            default:
                break;
        }

        switch (heightMode){
            case MeasureSpec.EXACTLY:
                measureHeight = height;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < count; i++) {
                    measureHeight += getChildAt(i).getMeasuredHeight();
                }
                break;
            default:
                Log.e(TAG, "onMeasure: 都没有执行"  );
                break;
        }
        Log.e(TAG, "onMeasure: " + measureWidth + "----" + measureHeight );
        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0,bottom = 0,left = 0,right = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (i > 0){
                top += getChildAt(i - 1).getMeasuredHeight();
            }
            bottom = top + getChildAt(i).getMeasuredHeight();
            left = i * offSet;
            right = left + getChildAt(i).getMeasuredWidth();
            Log.e(TAG, "onLayout: " + left + "----" + top+ "----" + right + "----" + bottom  );
            getChildAt(i).layout(left,top,right,bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
