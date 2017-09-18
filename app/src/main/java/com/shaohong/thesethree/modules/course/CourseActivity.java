package com.shaohong.thesethree.modules.course;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.EduDetail;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.model.CourseModel;
import com.shaohong.thesethree.modules.course.adapter.CourseDetailPagerAdapter;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private EduDetail course;
    private List<Paper> mPapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        course = (EduDetail) bundle.get(ConstantUtils.COURSE_INFO);
        if (course == null) {
            finish();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_course);
        TextView teacher_text_view = (TextView) findViewById(R.id.teacher_course_detail_text_view);
        TextView zhuti_text_view = (TextView) findViewById(R.id.zhuti_course_detail_text_view);
        TextView jianjie_text_view = (TextView) findViewById(R.id.jianjie_course_detail_text_view);

        teacher_text_view.setText(course.teacher);
        zhuti_text_view.setText(course.zhuti);
        jianjie_text_view.setText(course.zhuti);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager_course_detail);
        pager.setAdapter(new CourseDetailPagerAdapter(this.getSupportFragmentManager(), course.id));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_course_detail);
        tabs.setViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.action_compose) {
            new LoadDataThread().start();
        }
        return super.onOptionsItemSelected(item);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mPapers != null && mPapers.size() > 0) {
                        Intent intent = new Intent(getApplicationContext(), CourseExamActivity.class);
                        intent.putExtra(ConstantUtils.EXAM_INFO, (Serializable) mPapers);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    mPapers = CourseModel.downloadPxtimu(course.id);
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }
}