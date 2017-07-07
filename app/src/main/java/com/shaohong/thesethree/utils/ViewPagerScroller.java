package com.shaohong.thesethree.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;
/**
 * Created by shaohong on 2017/5/18.
 */

public class ViewPagerScroller extends Scroller {

    private int mScrollDuration = 1500;// 滑动速度
    public ViewPagerScroller(Context context) {
        super(context);
    }
    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

}
