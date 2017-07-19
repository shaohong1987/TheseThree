package com.shaohong.thesethree.model;

import android.content.Context;

import com.shaohong.thesethree.bean.CommonData;
import com.shaohong.thesethree.bean.HistoryListItemObject;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;
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

public class HomeModel {

    public static List<CommonData> getBanner(Context context) throws IOException, JSONException {
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        String hosid=sharedPreferencesHelper.get("hospitalcode","0").toString();
        List<CommonData> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("hosid", hosid)
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"getAdv")
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
                        CommonData commonData = new CommonData();
                        commonData.setImage(ConstantUtils.Image_URL+hosid+"/"+lan.getString("url"));
                        commonData.setTitle(lan.getString("title"));
                        list.add(commonData);
                    }
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return list;
    }

    /*
    * 通知
    * */
    public static List<HistoryListItemObject> getNotice(Context context,int rowid) throws IOException, JSONException{
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        List<HistoryListItemObject> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userId", String.valueOf(userid))
                .add("row", String.valueOf(rowid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"getNotice")
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
                        HistoryListItemObject commonData = new HistoryListItemObject();
                        commonData.setTitle(lan.getString("content"));
                        commonData.setDt(lan.getString("sendtime"));
                        commonData.setType(lan.getInt("type")>1?"考试通知":"培训通知");
                        commonData.setStatus("未确认");
                        commonData.setEducode(lan.getString("educode"));
                        commonData.setTestcode(lan.getString("testcode"));
                        commonData.setGroupid(lan.getString("groupid"));
                        commonData.setHospitalcode(lan.getString("hospitalcode"));
                        commonData.setIsvalued(lan.getString("isvalued"));
                        commonData.setId(lan.getInt("id"));
                        list.add(commonData);
                    }
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return list;
    }

    public static boolean DengJi(Context context,int testId) throws IOException, JSONException{
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId", String.valueOf(testId))
                .add("userid", String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"dengji")
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

    public static boolean DengJiEdu(Context context,int testId) throws IOException, JSONException{
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId", String.valueOf(testId))
                .add("userid", String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"dengjiedu")
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

    public static boolean JuJue(Context context,int testId) throws IOException, JSONException{
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId", String.valueOf(testId))
                .add("userid", String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"jujue")
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

    public static boolean JuJuePx(Context context,int testId) throws IOException, JSONException{
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId", String.valueOf(testId))
                .add("userid", String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"jujuepx")
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
}
