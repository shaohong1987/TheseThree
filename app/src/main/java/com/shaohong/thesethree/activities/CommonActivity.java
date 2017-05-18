package com.shaohong.thesethree.activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.modules.home.AddressListFragment;
import com.shaohong.thesethree.modules.home.NoticeFragment;
import com.shaohong.thesethree.modules.home.TIPFragment;
import com.shaohong.thesethree.modules.home.WorkPlanFragment;
import com.shaohong.thesethree.modules.personal.AboutUsFragment;
import com.shaohong.thesethree.modules.personal.HelpAndFeedBackFragment;
import com.shaohong.thesethree.modules.personal.MistakeBookFragment;
import com.shaohong.thesethree.modules.personal.PersonalInfoFragment;
import com.shaohong.thesethree.utils.ConstantUtils;

public class CommonActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private Fragment aboutUsFragment;
    private Fragment helpAndFeedbackFragment;
    private Fragment personalInfoFragment;
    private Fragment mistakeBookFragment;
    private Fragment noticeFragment;
    private Fragment addressListFragment;
    private Fragment tipFragment;
    private Fragment workPlanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        fragmentManager = getSupportFragmentManager();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        int id = getIntent().getExtras().getInt(ConstantUtils.COMMON_ARG);
        initView(id);
    }

    private void initView(int id) {
        noticeFragment=new NoticeFragment();
        switch (id) {
            case R.id.personal_info_layout://个人信息
                if (personalInfoFragment == null)
                    personalInfoFragment = new PersonalInfoFragment();
                switchContent(personalInfoFragment);
                break;
            case R.id.mistake_book_layout://错题集
                if (mistakeBookFragment == null) {
                    mistakeBookFragment = new MistakeBookFragment();
                }
                switchContent(mistakeBookFragment);
                break;
            case R.id.help_feedback_layout://帮助反馈
                if (helpAndFeedbackFragment == null) {
                    helpAndFeedbackFragment = new HelpAndFeedBackFragment();
                }
                switchContent(helpAndFeedbackFragment);
                break;
            case R.id.about_us_layout://关于我们
                if (aboutUsFragment == null) {
                    aboutUsFragment = new AboutUsFragment();
                }
                switchContent(aboutUsFragment);
                break;
            case R.drawable.i0:
                if(noticeFragment==null){
                    noticeFragment=new NoticeFragment();
                }
                switchContent(noticeFragment);
                break;
            case R.drawable.i1:
                if(addressListFragment==null){
                    addressListFragment=new AddressListFragment();
                }
                switchContent(addressListFragment);
                break;
            case R.drawable.i2:
                if(workPlanFragment==null){
                    workPlanFragment=new WorkPlanFragment();
                }
                switchContent(workPlanFragment);
                break;
            case R.drawable.i3:
                if(tipFragment==null){
                    tipFragment=new TIPFragment();
                }
                switchContent(tipFragment);
                break;
        }
    }

    public void switchContent(Fragment to) {
        if (currentFragment != to) {
            if (!to.isAdded()) {
                if (currentFragment != null) {
                    fragmentManager.beginTransaction().hide(currentFragment).commit();
                }
                fragmentManager.beginTransaction()
                        .add(R.id.common_fragment_container, to)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(to).commit();
            }
            currentFragment = to;
        }
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
