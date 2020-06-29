package com.zijie.frameworksource_4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.Scroller;

import androidx.core.view.ViewConfigurationCompat;

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
    //如果一行只有一个子view并且这个子view的高度是MATCH_PARENT，那我们就给它设置一个固定的高度
    private int commonHeight = 150;
    //流式布局内容的高度
    private int contentHeight;
    //滑动灵敏度控制值
    private int mTouchSlop;
    private int rootHeight;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    public MyFlowLayout(Context context) {
        this(context, null);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "MyFlowLayout: ");
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);//获取最小滑动距离
        mScroller = new OverScroller(context);
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
//        mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
    }

    private void init() {
        curLineViews = new ArrayList<>();
        allLines = new ArrayList<>();
        lineHeight = new ArrayList<>();
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void fling(int velocityY) {
        if (getChildCount() > 0) {
            int height = rootHeight;
            int bottom = contentHeight;

            mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0,
                    Math.max(0, bottom - height), 0, height / 2);

            postInvalidateOnAnimation();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        rootHeight = heightSize;
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
                //如果一行只有一个子view并且这个子view的高度是MATCH_PARENT，那我们就给它设置一个固定的高度
                if (curLineViews.size() == 1 && curLineViews.get(0).getLayoutParams().height == LayoutParams.MATCH_PARENT){
                    curLineHeight = commonHeight;
                }
                allLines.add(curLineViews);
                //记录每一行的行高
                Log.e(TAG, "这一行的高度" + curLineHeight);
                lineHeight.add(curLineHeight);
                curLineViews = new ArrayList<>();
                //换行时流式布局的高度需要累加
                curHeight += curLineHeight;
                //换行时流式布局的宽度要取最大值
                curWidth = Math.max(curWidth, curLineWidth);
                curLineHeight = 0;
                curLineWidth = 0;
            }
            LayoutParams layoutParams = child.getLayoutParams();
            //当前行的宽度需要累加
            curLineWidth += child.getMeasuredWidth();
            //当前行的高度需要取最大高度,高度为MATCH_PARENT会在后面重新测量
            if (layoutParams.height != LayoutParams.MATCH_PARENT)
                curLineHeight = Math.max(curLineHeight, child.getMeasuredHeight());
            curLineViews.add(child);
            //将最后一行加入
            if (i == getChildCount() - 1) {
                allLines.add(curLineViews);
                lineHeight.add(curLineHeight);
                Log.e(TAG, "这一行的高度" + curLineHeight);
                curHeight += curLineHeight;
                curWidth = Math.max(curWidth, curLineWidth);
            }
        }
        isCanScroll = curHeight > heightSize;
        contentHeight = curHeight;
        //重新测量高度为match_Parent时，子view的高度设置成这一行的最高高度
        remeasureChild(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure: 宽度=" + curWidth + "------高度=" + curHeight);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : curWidth, heightMode == MeasureSpec.EXACTLY ? heightSize : curHeight);
    }

    private void remeasureChild(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < allLines.size(); i++) {
            List<View> views = allLines.get(i);
            int lineH = lineHeight.get(i);
            for (int j = 0; j < views.size(); j++) {
                View view = views.get(j);
                LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams.height == LayoutParams.MATCH_PARENT) {
                    layoutParams.height = lineH;
                    view.setLayoutParams(layoutParams);
                    measureChild(view, widthMeasureSpec, heightMeasureSpec);
                }
            }
        }
    }
    //记录上一次滑动结束后的位置
    private float mLastX;
    private float mLastY;
    //记录y轴大于x轴滑动事件,并且大于滑动初始设定值
    private boolean isCanintercept = false;
    //只有流式布局内容的高度，大于容器的高度，才需要滑动，
    private boolean isCanScroll = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float y = ev.getY();
        float x = ev.getX();
        Log.e(TAG, "onInterceptTouchEvent: 出发事件" );
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isCanintercept = false;
                //按下去初始化值
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //每次移动事件的时候计算滑动距离
                float moveY = y - mLastY;
                float moveX = x - mLastX;

                //如果y轴距离大于x轴移动距离则可以判定为滑动
                if (Math.abs(moveY) > Math.abs(moveX) && Math.abs(moveY) > mTouchSlop){
                    isCanintercept = true;
                }else {
                    isCanintercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isCanintercept = false;
                break;
        }
        Log.e(TAG, "onInterceptTouchEvent: isCanintercept = " + isCanintercept );
        //每次事件结束时记录
        mLastX = x;
        mLastY = y;
        return isCanintercept;
    }
    //上一次移动结束的位置
    private float mLastMoveY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isCanScroll){
            return super.onTouchEvent(event);
        }
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //本次移动的距离
                float move = mLastMoveY - y;
                Log.e(TAG, "onTouchEvent:move =  " + move );
                int scrollY = getScrollY();//偏移量
                Log.e(TAG, "onTouchEvent:getScrollY =  " + scrollY );
//                int scroll = scrollY + (int) move;
//                if (scroll < 0){//不滑过顶部
//                    scroll = 0;
//                }
//                //不滑过底部
//                if (scroll > contentHeight - rootHeight){
//                    scroll = contentHeight - rootHeight;
//                }
                //向上滑传递正值
//                scrollTo(0,scroll);
                mScroller.startScroll(0,mScroller.getFinalY(),0,(int)move);
                invalidate();
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(500, mMaximumVelocity);
                int initialVelocity = (int) velocityTracker.getYVelocity();

                if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                    fling(-initialVelocity);
                } else if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0,
                        ( contentHeight- rootHeight))) {
                    postInvalidateOnAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            int currY = mScroller.getCurrY();
//            if (currY < 0){
//                currY = 0;
//            }
//            if (currY > contentHeight - rootHeight){
//                currY = contentHeight - rootHeight;
//            }
            scrollTo(0,currY);
            postInvalidate();
        }
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
            if (i > 0) {
                t += lineHeight.get(i - 1);
                b += lineHeight.get(i - 1);
            }

            for (int j = 0; j < curViews.size(); j++) {
                View child = curViews.get(j);
                if (j > 0) {
                    l += curViews.get(j - 1).getMeasuredWidth();
                }
                r += child.getMeasuredWidth();
                child.layout(l, t, r, b + child.getMeasuredHeight());
            }
        }
    }
}
