package com.shaohong.thesethree.modules.course;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Course;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.modules.exam.ExamEnterFragment;
import com.shaohong.thesethree.modules.exam.ExamSuperviseFragment;
import com.shaohong.thesethree.utils.ConstantUtils;

public class CourseActivity extends AppCompatActivity {

    private Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        course= (Course) bundle.get(ConstantUtils.COURSE_INFO);
        if(course==null)
        {
            finish();
        }
        if(course.getStatus()=="院内培训"||course.getStatus()=="院外培训"){

            setTitle(course.getName()+"--参加考试");
        }
        if(course.getStatus()=="我参加的"){

            setTitle(course.getName()+"--监考");
        }
        setContentView(R.layout.activity_course);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);

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

    private void showFragment(Fragment to){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.course_fragment_container, to)
                .commit();
    }
}
