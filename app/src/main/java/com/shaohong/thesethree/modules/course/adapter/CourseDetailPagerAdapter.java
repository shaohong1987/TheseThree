package com.shaohong.thesethree.modules.course.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shaohong.thesethree.modules.course.CourseDetailInfoFragment;
import com.shaohong.thesethree.utils.ConstantUtils;

/**
 * Created by shaohong on 2017/5/18.
 */

public class CourseDetailPagerAdapter extends FragmentPagerAdapter {
    private int courseId;
    public CourseDetailPagerAdapter(FragmentManager fm,int courseId) {
        super(fm);
        this.courseId=courseId;
    }

    @Override
    public Fragment getItem(int position) {
        //在这边实现不同的加载
        if(position==0){
            CourseDetailInfoFragment courseOptionFragment=new CourseDetailInfoFragment();
            courseOptionFragment.courseId=courseId;
            return courseOptionFragment;
        }else if(position==1){
            //在线讨论
        }
        return null;
    }

    @Override
    public int getCount() {
        return ConstantUtils.COURSE_DETAIL_OPTIONS().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ConstantUtils.COURSE_DETAIL_OPTIONS().get(position);
    }
}
