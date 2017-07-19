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
    private int type;
    private int actionType;

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

        Bundle bundle=getIntent().getExtras();
        HistoryListItemObject object= (HistoryListItemObject) bundle.get(ConstantUtils.NOTICE);
        if(object!=null){
            String status=object.getStatus();
            status_text_view.setText(status);
            if(status.equals("未确认")){
                if(object.getType().equals("考试通知")){

                }
                button_layout.setVisibility(View.VISIBLE);
                Button confirm_button=(Button) findViewById(R.id.confirm_notice_detail);
                Button refuse_button= (Button) findViewById(R.id.refuse_notice_detail);
                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                refuse_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }else{
                button_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    break;
            }
        }
    };
    class LoadDataThread extends Thread{
        @Override
        public void run() {
            try {
                HomeModel.DengJi(getApplicationContext(),1);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }
}
