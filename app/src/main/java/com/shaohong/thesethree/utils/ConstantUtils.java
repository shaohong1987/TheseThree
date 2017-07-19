package com.shaohong.thesethree.utils;

import com.shaohong.thesethree.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaohong on 2017/5/m10.
 */

public class ConstantUtils {

    public static final String REQUEST_URL="http://js00000000.com:8880/OrderService/postJson";
    public static final String Image_URL="https://api.js00000000.com:8443/img/";
    public static final String PACKAGE_NAME="com.shaohong.thesethree";
    public static final String APK_VERSION="versionCode";
    public static final String APK_NAME="name";
    public static final String APK_URL="url";
    public static final String NOTICE="NOTICE_DETAIL";
    public static final String EXAM_INFO="EXAM_INFO";
    public static final String COURSE_INFO="COURSE_INFO";
    public static final String COMMON_ARG="COMMON_ARG";

    public static final String ITEM_IMAGE="ItemImage";
    public static final String ITEM_TEXT="ItemText";

    public static final int UDP_PORT=1712;
    public static final String UDP_SERVER_URL="api.js00000000.com";
    //考试类型
    public static HashMap<Integer,String> EXAM_OPTIONS() {
        HashMap<Integer, String> examOptions = new HashMap<>();
        examOptions.put(0, "全院考试");
        examOptions.put(1, "科室考试");
        examOptions.put(2, "专科考试");
        return examOptions;
    }

    //培训类型
    public static HashMap<Integer,String> COURSE_OPTIONS(){
        HashMap<Integer,String> courseOptions=new HashMap<>();
        courseOptions.put(0,"院内培训");
        courseOptions.put(1,"院外培训");
        courseOptions.put(2,"我参加的");
        return courseOptions;
    }

    //首页九宫格功能
    public static ArrayList<HashMap<String, Object>> COMMON_FUNCTIONS(){
        ArrayList<HashMap<String, Object>> commonFunctions=new ArrayList<>();
        HashMap<String, Object> map0 = new HashMap<>();
        map0.put(ITEM_IMAGE, R.drawable.i0);
        map0.put(ITEM_TEXT, "通知");
        commonFunctions.add(map0);
//        HashMap<String, Object> map1 = new HashMap<>();
//        map1.put(ITEM_IMAGE, R.drawable.i1);
//        map1.put(ITEM_TEXT, "通讯录");
//        commonFunctions.add(map1);
//        HashMap<String, Object> map2 = new HashMap<>();
//        map2.put(ITEM_IMAGE, R.drawable.i2);
//        map2.put(ITEM_TEXT, "工作计划");
//        commonFunctions.add(map2);
//        HashMap<String, Object> map3 = new HashMap<>();
//        map3.put(ITEM_IMAGE, R.drawable.i3);
//        map3.put(ITEM_TEXT, "提醒");
//        commonFunctions.add(map3);
        return commonFunctions;
    }
}
