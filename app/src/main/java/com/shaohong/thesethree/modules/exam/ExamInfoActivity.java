package com.shaohong.thesethree.modules.exam;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.base.BaseActivity;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.utils.ConstantUtils;

public class ExamInfoActivity extends BaseActivity {

    private Exam exam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        exam= (Exam) bundle.get(ConstantUtils.EXAM_INFO);
        if(exam==null)
        {
            finish();
        }
        if(exam.getStatus()==0||exam.getStatus()==1){
            ExamEnterFragment examEnterFragment=new ExamEnterFragment();
            examEnterFragment.setArguments(bundle);
            showFragment(examEnterFragment);
            setTitle(exam.getName()+"--参加考试");
        }
        if(exam.getStatus()==2){
            ExamSuperviseFragment examSuperviseFragment=new ExamSuperviseFragment();
            examSuperviseFragment.setArguments(bundle);
            showFragment(examSuperviseFragment);
            setTitle(exam.getName()+"--监考");
        }
        setContentView(R.layout.activity_exam_info);

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
                .add(R.id.exam_fragment_container, to)
                .commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

