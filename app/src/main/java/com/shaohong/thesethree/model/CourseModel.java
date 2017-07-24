package com.shaohong.thesethree.model;

import android.content.Context;

import com.shaohong.thesethree.bean.EduDetail;
import com.shaohong.thesethree.bean.KJ;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.SharedPreferencesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shaohong on 2017/5/10.
 */

public class CourseModel {
    public static boolean getEdu(int eduId) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eduId", String.valueOf(eduId))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"getEdu")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (!result.isEmpty()) {
                JSONObject obj=new JSONObject(result);
                if(obj.getString("result").equals("true"))
                {
                    return true;
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return false;
    }

    public static List<EduDetail> getEdus(Context context,int type) throws IOException, JSONException{
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        int hosid=Integer.parseInt(sharedPreferencesHelper.get("hospitalcode","-1").toString());
        List<EduDetail> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("type", String.valueOf(type))
                .add("hosid", String.valueOf(hosid))
                .add("userid", String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"getEdus")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (!result.isEmpty()) {
                JSONObject obj=new JSONObject(result);
                if(obj.getString("result").equals("true"))
                {
                    JSONArray jsonArray=obj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject lan = jsonArray.getJSONObject(i);
                        EduDetail commonData = new EduDetail();
                        commonData.adress=lan.getString("adress");
                        commonData.hosid=lan.getInt("hosid");
                        commonData.id=lan.getInt("id");
                        commonData.org=lan.getString("org");
                        commonData.recordtime=lan.getString("recordtime");
                        commonData.score=lan.getInt("score");
                        commonData.teacher=lan.getString("teacher");
                        commonData.zhuti=lan.getString("zhuti");
                        commonData.time=lan.getString("time");
                        commonData.state=lan.getInt("state");
                        commonData.type=lan.getInt("type");
                        list.add(commonData);
                    }
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return list;
    }

    public static List<KJ> getEduDetail(int eduId) throws IOException, JSONException{
        List<KJ> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eduid", String.valueOf(eduId))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"edus")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (!result.isEmpty()) {
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.getJSONArray("sps");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject lan = jsonArray.getJSONObject(i);
                    KJ commonData = new KJ();
                    commonData.eduid=lan.getInt("eduid");
                    commonData.id=lan.getInt("id");
                    commonData.kjname=lan.getString("spname");
                    commonData.kjurl=lan.getString("spurl");
                    list.add(commonData);
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return list;
    }
}
