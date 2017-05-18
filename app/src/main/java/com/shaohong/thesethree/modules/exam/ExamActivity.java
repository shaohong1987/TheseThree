package com.shaohong.thesethree.modules.exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.shaohong.thesethree.R;

import java.util.Date;

public class ExamActivity extends AppCompatActivity {

    private Date outTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
    }

    @Override
    protected void onPause() {
        super.onPause();
        outTime = new Date();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (outTime != null) {
            long between = (new Date().getTime() - outTime.getTime()) / 1000;
            if (between > 10) {
                Toast.makeText(this, "退出时间大于10秒，系统已自动交卷", Toast.LENGTH_LONG).show();

                finish();
            } else
                Toast.makeText(this, "退出考试一次，该行为将被记录", Toast.LENGTH_LONG).show();
        }
    }


}