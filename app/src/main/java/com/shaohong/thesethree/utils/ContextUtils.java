package com.shaohong.thesethree.utils;

import android.app.Application;
import android.util.Log;

import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.bean.UserAnswer;

import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by shaohong on 2017/5/m10.
 */

public class ContextUtils extends Application {
    public static boolean isLogin;
    public static List<Paper> mPapers;
    public static int testId;
    public static List<UserAnswer> mUserAnswers;

    private static ContextUtils instance;

    public static ContextUtils getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        int userid=(int) new SharedPreferencesHelper(this).get("userid",-1);
        isLogin= userid>0;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        JPushInterface.setAlias(this,String.valueOf(userid),
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.d("alias", "set alias result is" + i);
                    }
                });
    }
}