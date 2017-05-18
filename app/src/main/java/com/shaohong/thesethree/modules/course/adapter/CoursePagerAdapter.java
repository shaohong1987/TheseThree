package com.shaohong.thesethree.modules.course.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shaohong.thesethree.modules.course.CourseInfoFragment;
import com.shaohong.thesethree.utils.ConstantUtils;

/**
 * Created by shaohong on 2017/5/18.
 */

public class CoursePagerAdapter extends FragmentPagerAdapter {
    public CoursePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        CourseInfoFragment courseOptionFragment=new CourseInfoFragment();
        courseOptionFragment.courseType=position;
        return courseOptionFragment;
    }

    @Override
    public int getCount() {
        return ConstantUtils.COURSE_OPTIONS().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ConstantUtils.COURSE_OPTIONS().get(position);
    }
}
