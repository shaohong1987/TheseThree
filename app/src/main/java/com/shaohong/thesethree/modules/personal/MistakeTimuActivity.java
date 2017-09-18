package com.shaohong.thesethree.modules.personal;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.utils.ConstantUtils;

import java.util.List;

public class MistakeTimuActivity extends AppCompatActivity {
    private List<Paper> mPapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistake_timu);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        mPapers = (List<Paper>) bundle.get(ConstantUtils.EXAM_INFO);
        //需要在此实现 listview 功能，加点击事件
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
