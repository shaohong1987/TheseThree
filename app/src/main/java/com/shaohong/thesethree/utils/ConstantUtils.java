package com.shaohong.thesethree.utils;

import com.shaohong.thesethree.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaohong on 2017/5/10.
 */

public class ConstantUtils {

    public static final String REQUEST_URL="http://js00000000.com:8880/OrderService/postJson";
    public static final String UPDATE_URL="http://192.168.1.121:8080/update.json";
    public static final String PACKAGE_NAME="com.shaohong.thesethree";
    public static final String APK_VERSION="version";
    public static final String APK_NAME="name";
    public static final String APK_URL="url";

    public static final String EXAM_INFO="EXAM_INFO";
    public static final String COURSE_INFO="COURSE_INFO";
    public static final String COMMON_ARG="COMMON_ARG";

    public static final String ITEM_IMAGE="ItemImage";
    public static final String ITEM_TEXT="ItemText";

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
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put(ITEM_IMAGE, R.drawable.i1);
        map1.put(ITEM_TEXT, "通讯录");
        commonFunctions.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put(ITEM_IMAGE, R.drawable.i2);
        map2.put(ITEM_TEXT, "工作计划");
        commonFunctions.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put(ITEM_IMAGE, R.drawable.i3);
        map3.put(ITEM_TEXT, "提醒");
        commonFunctions.add(map3);
        return commonFunctions;
    }

    public static final String isCorrect="1";//正确
    public static final String isError="0";//错误

    public static final String answerId[]={"1","2","3","4"};
    public static final String answerName[]={"您的年龄是？","您的工作是？","下列属于腾讯开发的游戏?","网络游戏一定需要付费"};
    public static final String answerType[]={"0","0","1","2"};
    public static final String answerOptionA[]={"18岁以下","学生","梦幻西游","对"};
    public static final String answerOptionB[]={"18岁至25岁","公务单位","英雄联盟","错"};
    public static final String answerOptionC[]={"25岁至35岁","工薪一族","诛仙",""};
    public static final String answerOptionD[]={"35岁至45岁","自己当老板","逆战",""};
    public static final String answerOptionE[]={"45岁以上","其他","劲舞团",""};
    public static final String answerAnalysis[]={"暂无解答","自己当老板","此题太简单了","简单"};
    public static final String answerScore[]={"2","2","1","2"};
    public static final String answerCorrect[]={"A","D","BD","B"};
}
