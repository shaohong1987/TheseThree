package com.shaohong.thesethree.modules.home;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.HistoryListItemObject;
import com.shaohong.thesethree.database.DbManager;
import com.shaohong.thesethree.model.HomeModel;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    private ViewGroup button_layout;
    private TextView status_text_view;
    private int testCode;
    private int type;//0:考试通知，else：培训通知
    private int noticeId;
    private TextView exam_time_notice_text_view;
    private TextView exam_name_notice_text_view;
    private TextView exam_address_notice_text_view;
    private TextView exam_person_notice_text_view;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        button_layout= (ViewGroup) findViewById(R.id.show_notice_detail);
        status_text_view= (TextView) findViewById(R.id.status_notice_detail);
        exam_time_notice_text_view = (TextView) findViewById(R.id.exam_time_notice_text_view);
        exam_name_notice_text_view = (TextView) findViewById(R.id.exam_name_notice_text_view);
        exam_address_notice_text_view = (TextView) findViewById(R.id.exam_address_notice_text_view);
        exam_person_notice_text_view = (TextView) findViewById(R.id.exam_person_notice_text_view);

        Bundle bundle=getIntent().getExtras();
        HistoryListItemObject object= (HistoryListItemObject) bundle.get(ConstantUtils.NOTICE);
        if(object!=null){
            String status=object.getStatus();
            status_text_view.setText(status);
            exam_time_notice_text_view.setText(object.getDt());
            exam_name_notice_text_view.setText(object.getTitle());
            exam_person_notice_text_view.setText(object.getGroupid());
            exam_address_notice_text_view.setText("");
            noticeId=object.getId();
            if(status.equals("未确认")){
                if(object.getType().equals("考试通知")){
                    testCode= Integer.parseInt(object.getTestcode());
                    type=0;
                }else{
                    testCode= Integer.parseInt(object.getEducode());
                    type=1;
                }
                button_layout.setVisibility(View.VISIBLE);
                final Button confirm_button=(Button) findViewById(R.id.confirm_notice_detail);
                Button refuse_button= (Button) findViewById(R.id.refuse_notice_detail);
                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateStatus("已确认");
                        new ConfirmThread().start();
                    }
                });
                refuse_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateStatus("已拒绝");
                        new RefuseThread().start();
                    }
                });
            }else{
                button_layout.setVisibility(View.GONE);
            }
        }
    }

    private void updateStatus(String status){
        DbManager db=new DbManager(getApplicationContext());
        db.openDB();
        db.updateNoticeStatus(noticeId,status);
        db.closeDB();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ConfirmThread extends Thread {
        @Override
        public void run() {
            try {
                if(type==0){
                    HomeModel.DengJi(getApplicationContext(),testCode);
                }else{
                    HomeModel.DengJiEdu(getApplicationContext(),testCode);
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

    private class RefuseThread extends Thread {
        @Override
        public void run() {
            try {
                if(type==0) {
                    HomeModel.JuJue(getApplicationContext(), testCode);
                }else{
                    HomeModel.JuJuePx(getApplicationContext(), testCode);
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }
}
