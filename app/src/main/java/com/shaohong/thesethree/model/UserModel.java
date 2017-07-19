package com.shaohong.thesethree.model;

import android.content.Context;

import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;
import com.shaohong.thesethree.utils.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shaohong on 2017/5/m10.
 */

public class UserModel {
    public static String getUserInfo(Context context) {
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        String name = (String) sharedPreferencesHelper.get("name", "");
        String loginID = (String)sharedPreferencesHelper.get("loginID", "");
        String deptName = (String)sharedPreferencesHelper.get("deptname", "");
        return name+" "+loginID+" "+deptName;
    }

    public static HashMap<String,String> getUserInfoMore(Context context){
        HashMap<String,String> result=new HashMap<>();
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        String name = (String) sharedPreferencesHelper.get("name", "");
        result.put("name",name);
        String loginID = (String)sharedPreferencesHelper.get("loginID", "");
        result.put("loginID",loginID);
        String deptName = (String)sharedPreferencesHelper.get("deptname", "");
        result.put("deptname",deptName);
        return result;
    }

    /*
    * 登陆操作
    * */
    public static boolean login(Context context,String userName,String password) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("phone", userName)
                .add("password",password)
                .add("dervicetoken","1")
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"login")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            if (!result.isEmpty()) {
                JSONObject obj=new JSONObject(result);
                if(obj.getString("result").equals("true"))
                {
                    SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
                    sharedPreferencesHelper.put("userid", obj.getInt("userid"));
                    sharedPreferencesHelper.put("loginID", obj.getString("loginid"));
                    sharedPreferencesHelper.put("name", obj.getString("name"));
                    sharedPreferencesHelper.put("phone", userName);
                    sharedPreferencesHelper.put("password", password);
                    sharedPreferencesHelper.put("gwname", obj.getString("gwname"));
                    sharedPreferencesHelper.put("zcname", obj.getString("zhichen"));
                    sharedPreferencesHelper.put("hospitalname", obj.getString("hospital"));
                    sharedPreferencesHelper.put("hospitalcode", obj.getString("hospitalcode"));
                    sharedPreferencesHelper.put("wardcode", obj.getString("wardcode"));
                    sharedPreferencesHelper.put("wardname", obj.getString("wardname"));
                    sharedPreferencesHelper.put("deptcode", obj.getString("deptcode"));
                    sharedPreferencesHelper.put("deptname", obj.getString("deptname"));
                    sharedPreferencesHelper.put("type", obj.getInt("type"));
                    ContextUtils.isLogin=true;
                    return true;
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return false;
    }

    /*
    * 登出操作
    * */
    public boolean loginOut(Context context) {
        try {
            new SharedPreferencesHelper(context).clear();
            ContextUtils.isLogin=false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
