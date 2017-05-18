package com.shaohong.thesethree.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shaohong on 2017/5/10.
 */

public class UserModel {
    public boolean isLogin() {
        Context context = ContextUtils.getInstance();
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int uid = sp.getInt("id", -1);
        String un = sp.getString("userName", "");
        String pw = sp.getString("password", "");
        if (uid > 0 && !un.isEmpty() && !pw.isEmpty()) {
            //需要在此实现记录用户的登录信息
            return true;
        }
        return false;
    }

    /*
    * 登陆操作
    * */
    public boolean login(String userName,String password) throws IOException, JSONException {
       /* OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userId", userName)
                .add("password",password)
                .add("dervicetoken","1234567890")
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
                    Context context = ContextUtils.getInstance();
                    SharedPreferences sp = context.getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userName", userName);
                    editor.putString("password", password);
                    editor.putString("name", obj.getString("name"));
                    editor.putInt("id", 1);
                    editor.commit();
                    return true;
                }
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }*/
        Context context = ContextUtils.getInstance();
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.putInt("id", 1);
        editor.commit();
        return true;
    }

    /*
    * 登出操作
    * */
    public boolean loginOut() {
        try {
            Context context = ContextUtils.getInstance();
            SharedPreferences sp = context.getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
