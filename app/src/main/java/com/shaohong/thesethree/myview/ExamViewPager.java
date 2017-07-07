package com.shaohong.thesethree.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shaohong on 2017/5/18.
 */

public class ExamViewPager extends ViewPager {

    private boolean isScrollable = false;

    public boolean isScrollable() {
        return isScrollable;
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    public ExamViewPager(Context context) {
        super(context);
    }

    public ExamViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!isScrollable)
            return false;
        return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!isScrollable)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }

}
