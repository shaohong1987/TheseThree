package com.shaohong.thesethree.utils;

import android.app.Application;

/**
 * Created by shaohong on 2017/5/10.
 */

public class ContextUtils extends Application {
    private static ContextUtils instance;

    public static ContextUtils getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}