package com.shaohong.thesethree.modules.course;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.EduDetail;
import com.shaohong.thesethree.modules.course.adapter.CourseDetailPagerAdapter;
import com.shaohong.thesethree.utils.ConstantUtils;

public class CourseActivity extends AppCompatActivity {

    private EduDetail course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        course= (EduDetail) bundle.get(ConstantUtils.COURSE_INFO);
        if(course==null)
        {
            finish();
        }
        setContentView(R.layout.activity_course);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView teacher_text_view= (TextView) findViewById(R.id.teacher_course_detail_text_view);
        TextView zhuti_text_view= (TextView) findViewById(R.id.zhuti_course_detail_text_view);
        TextView jianjie_text_view= (TextView) findViewById(R.id.jianjie_course_detail_text_view);

        teacher_text_view.setText(course.teacher);
        zhuti_text_view.setText(course.zhuti);
        jianjie_text_view.setText(course.zhuti);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager_course_detail);
        pager.setAdapter(new CourseDetailPagerAdapter(this.getSupportFragmentManager(),course.id));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_course_detail);
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
