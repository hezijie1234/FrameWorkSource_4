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
        int marginL = 0;
        int marginR = 0;
        int marginTop = 0;
        int marginB = 0;
        //根据父控件的测量参数，测量子view
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            marginTop += layoutParams.topMargin;
            marginB += layoutParams.bottomMargin;
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
                    MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
                    marginL = Math.max(0,layoutParams.leftMargin);//计算子view的最大
                    marginR = Math.max(0,layoutParams.rightMargin);
                    measureWidth = Math.max(measureWidth,i * offSet + childAt.getMeasuredWidth() + marginL + marginR);
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
        measureWidth += getPaddingLeft() + getPaddingRight();
        measureHeight += getPaddingTop() + getPaddingBottom() + marginTop + marginB;
        Log.e(TAG, "onMeasure: " + measureWidth + "----" + measureHeight );
        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = getPaddingTop(),bottom = 0,left = 0,right = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            if (i > 0){
                View lastView = getChildAt(i - 1);
                MarginLayoutParams lastViewParams = (MarginLayoutParams) lastView.getLayoutParams();
                top += getChildAt(i - 1).getMeasuredHeight() + layoutParams.topMargin + lastViewParams.bottomMargin;
            }else {
                top += layoutParams.topMargin;
            }
            bottom = top + getChildAt(i).getMeasuredHeight();
            left = layoutParams.leftMargin + getPaddingLeft() + i * offSet;
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
