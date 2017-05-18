package com.shaohong.thesethree.modules.exam.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shaohong.thesethree.modules.exam.ExamInfoFragment;
import com.shaohong.thesethree.utils.ConstantUtils;

/**
 * Created by shaohong on 2017/5/10.
 */

public class ExamPagerAdapter extends FragmentPagerAdapter {
    public ExamPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ExamInfoFragment examOptionFragment=new ExamInfoFragment();
        examOptionFragment.examType=position;
        return examOptionFragment;
    }

    @Override
    public int getCount() {
        return ConstantUtils.EXAM_OPTIONS().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ConstantUtils.EXAM_OPTIONS().get(position);
    }
}
