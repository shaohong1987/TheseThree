package com.shaohong.thesethree.model;

import android.content.Context;

import com.shaohong.thesethree.bean.EduDetail;
import com.shaohong.thesethree.bean.KJ;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.bean.Pinlun;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.SharedPreferencesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                .url(ConstantUtils.REQUEST_URL + "getEdu")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (!result.isEmpty()) {
                JSONObject obj = new JSONObject(result);
                if (obj.getString("result").equals("true")) {
                    return true;
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return false;
    }

    public static List<EduDetail> getEdus(Context context, int type) throws IOException, JSONException {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);
        int userid = (int) sharedPreferencesHelper.get("userid", -1);
        int hosid = Integer.parseInt(sharedPreferencesHelper.get("hospitalcode", "-1").toString());
        List<EduDetail> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("type", String.valueOf(type))
                .add("hosid", String.valueOf(hosid))
                .add("userid", String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL + "getEdus")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (!result.isEmpty()) {
                JSONObject obj = new JSONObject(result);
                if (obj.getString("result").equals("true")) {
                    JSONArray jsonArray = obj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject lan = jsonArray.getJSONObject(i);
                        EduDetail commonData = new EduDetail();
                        commonData.adress = lan.getString("adress");
                        commonData.hosid = lan.getInt("hosid");
                        commonData.id = lan.getInt("id");
                        commonData.org = lan.getString("org");
                        commonData.recordtime = lan.getString("recordtime");
                        commonData.score = lan.getInt("score");
                        commonData.teacher = lan.getString("teacher");
                        commonData.zhuti = lan.getString("zhuti");
                        commonData.time = lan.getString("time");
                        commonData.state = lan.getInt("state");
                        commonData.type = lan.getInt("type");
                        list.add(commonData);
                    }
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return list;
    }

    public static List<KJ> getEduDetail(Context context, int eduId) throws IOException, JSONException {
        List<KJ> list = new ArrayList<>();
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);
        int hosid = Integer.parseInt(sharedPreferencesHelper.get("hospitalcode", "-1").toString());
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eduid", String.valueOf(eduId))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL + "edus")
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
                    commonData.eduid = lan.getInt("eduid");
                    commonData.id = lan.getInt("id");
                    commonData.kjname = lan.getString("spname");
                    commonData.kjurl = "https://api.js00000000.com:8443/kj/" + hosid + "/" + lan.getString("spurl");
                    list.add(commonData);
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return list;
    }

    public static List<Pinlun> getDiscus(String eduId) throws IOException, JSONException, ParseException {
        List<Pinlun> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eduid", String.valueOf(eduId))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL + "getdiscus")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (!result.isEmpty()) {
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.getJSONArray("pinlun");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject lan = jsonArray.getJSONObject(i);
                    Pinlun pinlun = new Pinlun();
                    pinlun.id = lan.getInt("id");
                    pinlun.name = lan.getString("name");
                    pinlun.icon = lan.getString("icon");
                    pinlun.shoushouText = lan.getString("shuoshuoText");
                    pinlun.time = lan.getString("time");
                    pinlun.replys = new ArrayList<>();
                    JSONArray arr = lan.getJSONArray("replys");
                    for (int j = 0; j < arr.length(); j++) {
                        pinlun.replys.add(arr.getString(j));
                    }
                    list.add(pinlun);
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return list;
    }

    public static void addreplys(Context context, String content, int fromid) throws IOException, JSONException {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);
        String username = (String) sharedPreferencesHelper.get("name", "");
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("content", username + ":" + content)
                .add("fromid", String.valueOf(fromid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL + "addreplys")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static void adddiscus(Context context, String eduid, String shuoshuoText) throws IOException, JSONException {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);
        int userid = (int) sharedPreferencesHelper.get("userid", -1);
        String username = (String) sharedPreferencesHelper.get("name", "");
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eduid", eduid)
                .add("icon", "")
                .add("userid", String.valueOf(userid))
                .add("name", username)
                .add("shuoshuoText", shuoshuoText)
                .add("time", time)//"yyyy-MM-dd HH:mm:ss"
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL + "adddiscus")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static List<Paper> downloadPxtimu(int eduId) throws IOException, JSONException {
        List<Paper> papers = null;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("eduId", String.valueOf(eduId))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL + "pxtimu")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            JSONObject obj = new JSONObject(result);
            JSONArray jsonArray = obj.getJSONArray("data");
            papers = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject lan = jsonArray.getJSONObject(i);
                Paper paper = new Paper();
                paper.setTestId(lan.getInt("pid"));
                paper.setAnli(lan.getString("anli"));
                paper.setAnswer(lan.getString("answer"));
                paper.setDifficulty(lan.getInt("difficulty"));
                paper.setExerciseType(lan.getInt("exerciseType"));
                paper.setId(lan.getInt("id"));
                paper.setItemA(lan.getString("itemA"));
                paper.setItemB(lan.getString("itemB"));
                paper.setItemC(lan.getString("itemC"));
                paper.setItemD(lan.getString("itemD"));
                paper.setItemE(lan.getString("itemE"));
                paper.setItemF(lan.getString("itemF"));
                paper.setItemG(lan.getString("itemG"));
                paper.setItemH(lan.getString("itemH"));
                paper.setItemI(lan.getString("itemI"));
                paper.setItemJ(lan.getString("itemJ"));
                paper.setItemNum(lan.getInt("itemNum"));
                paper.setQuestion(lan.getString("question"));
                paper.setRemark(lan.getString("remark"));
                papers.add(paper);
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return papers;
    }

    public static void PxJiaoJuan(String data, String userid, String eduid) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("data", data)
                .add("userid", userid)
                .add("eduid", eduid)
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL + "pxjiaojuan")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
