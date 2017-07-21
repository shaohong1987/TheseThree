package com.shaohong.thesethree.modules.exam;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.base.BaseActivity;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import java.util.HashMap;

public class ExamInfoActivity extends BaseActivity {

    private HashMap<String,String> data;
    private Exam exam;
    private Bundle mBundle;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(data.get("result").equals("true")) {
                        ExamSuperviseFragment examSuperviseFragment=new ExamSuperviseFragment();
                        examSuperviseFragment.setArguments(mBundle);
                        showFragment(examSuperviseFragment);
                    }else {
                        if(data.get("isend").equals("false")){
                            ExamEnterFragment examEnterFragment = new ExamEnterFragment();
                            examEnterFragment.setArguments(mBundle);
                            showFragment(examEnterFragment);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"该考试已结束",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBundle = this.getIntent().getExtras();
        exam= (Exam) mBundle.get(ConstantUtils.EXAM_INFO);
        if(exam==null)
        {
            finish();
        }
        setContentView(R.layout.activity_exam_info);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        new LoadDataThread().start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment to){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.exam_fragment_container, to)
                .commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    class LoadDataThread extends Thread{
        @Override
        public void run() {
            try {
                if(ContextUtils.isLogin){
                    data=ExamModel.GetExamStatus(getApplicationContext(),exam.getId());
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);//通过handler发送一个更新数据的标记
        }
    }
}

