package com.shaohong.thesethree.model;

import android.content.Context;

import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.bean.UserAnswer;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;
import com.shaohong.thesethree.utils.SharedPreferencesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shaohong on 2017/5/m10.
 */

public class ExamModel {

    //0 全院考试，m1 科室考试，2 专科考试
    public static List<Exam> GetExamList(Context context,int type) {
        List<Exam> exams=new ArrayList<>();
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        String hosid=sharedPreferencesHelper.get("hospitalcode","-1").toString();
        String wardcode= sharedPreferencesHelper.get("wardcode","-1").toString();
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("type", String.valueOf(type))
                .add("hosid",hosid)
                .add("wardcode",wardcode)
                .add("userid",String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"getTests")
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                if (!result.isEmpty()) {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("result").equals("true")){
                        JSONArray jsonArray=obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject lan = jsonArray.getJSONObject(i);
                            Exam exam=new Exam();
                            exam.setAddress(lan.getString("adress"));
                            exam.setStartTime(lan.getString("begintime"));
                            exam.setEndTime(lan.getString("endtime"));
                            exam.setFanWei(lan.getString("fanwei"));
                            exam.setFen(lan.getInt("fen"));
                            exam.setGroupId(lan.getInt("groupid"));
                            exam.setGroupName(lan.getString("groupname"));;
                            exam.setHosId(lan.getInt("hosid"));;
                            exam.setId(lan.getInt("id"));
                            exam.setEnd(lan.getInt("isend")>0);
                            exam.setJiGeScore(lan.getInt("jigescore"));
                            exam.setTitle(lan.getString("title"));
                            exam.setType(lan.getInt("type"));
                            exam.setWardCode(lan.getInt("wardcode"));
                            exam.setImage("m1.png");
                            exams.add(exam);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return exams;
    }

    public static HashMap<String,String> GetHead(Context context){
        HashMap<String,String> result=new HashMap<>();
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("userId",String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"gethead")
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                if (!res.isEmpty()) {
                    JSONObject obj=new JSONObject(res);
                    result.put("score",obj.getString("score"));
                    result.put("cc",obj.getString("cc"));
                    result.put("zql",obj.getString("zql"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static HashMap<String,String> GetExamStatus(Context context,int testid){
        HashMap<String,String> result=new HashMap<>();
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId",String.valueOf(testid))
                .add("userId",String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"getjk")
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                if (!res.isEmpty()) {
                    JSONObject obj=new JSONObject(res);
                    result.put("result",obj.getString("result"));
                    result.put("isend",obj.getString("isend"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean Qd(Context context,int testid){
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId",String.valueOf(testid))
                .add("userId",String.valueOf(userid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"ksqd")
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
               return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Paper> DownloadPaper(int testid){
        List<Paper> papers=null;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId",String.valueOf(testid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"tbtimu")
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                if (!res.isEmpty()) {
                    papers=new ArrayList<>();
                    JSONObject obj=new JSONObject(res);
                    if(obj.getString("result").equals("true")){
                        JSONArray jsonArray=obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject lan = jsonArray.getJSONObject(i);
                            Paper paper = new Paper();
                            paper.setTestId(testid);
                            paper.setAnli(lan.getString("anli"));
                            paper.setAnswer(lan.getString("answer"));
                            paper.setDifficulty(lan.getInt("difficulty"));
                            paper.setExerciseType(lan.getInt("exerciseType"));
                            paper.setId(lan.getInt("id"));
                            paper.setIsright(lan.getInt("isright"));
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
                            paper.setLabel(lan.getString("label"));
                            paper.setPaperid(lan.getInt("paperid"));
                            paper.setPartid(lan.getInt("partid"));
                            paper.setQuestion(lan.getString("question"));
                            paper.setQuestionId(lan.getInt("questionid"));
                            paper.setRemark(lan.getString("remark"));
                            paper.setScroe(lan.getInt("score"));
                            paper.setSetcion(lan.getInt("section"));
                            paper.setSeq(lan.getInt("seq"));
                            paper.setUserAnswer(lan.getString("userAnswer"));
                            papers.add(paper);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return papers;
    }

    public static boolean UploadPaper(Context context){
        List<UserAnswer> userAnswers= ContextUtils.mUserAnswers;
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(context);
        int userid=(int)sharedPreferencesHelper.get("userid",-1);
        int testid=-1;
        JSONArray jsonArray = new JSONArray();
        int count = userAnswers.size();
        for(int i = 0; i < count; i++)
        {
            JSONObject tmpObj = new JSONObject();
            try {
                testid=userAnswers.get(i).testid;
                tmpObj.put("testid" , testid);
                tmpObj.put("userid", userid);
                tmpObj.put("score", userAnswers.get(i).score);
                tmpObj.put("seq", userAnswers.get(i).seq);
                tmpObj.put("answer", userAnswers.get(i).answer);
                tmpObj.put("isright", userAnswers.get(i).isright);
                tmpObj.put("timuid", userAnswers.get(i).timuid);
                jsonArray.put(tmpObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String data = jsonArray.toString();

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("data",data)
                .add("userid",String.valueOf(userid))
                .add("testid",String.valueOf(testid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"jiaojuan")
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static HashMap<String,Integer> GetKaoSheng(int testid){
        HashMap<String,Integer> data=null;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("testId",String.valueOf(testid))
                .build();
        Request request = new Request.Builder()
                .url(ConstantUtils.REQUEST_URL+"getkaosheng")
                .post(formBody)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                if (!res.isEmpty()) {
                    data=new HashMap<>();
                    JSONObject obj=new JSONObject(res);
                    if(obj.getString("result").equals("true")){

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
