package com.shaohong.thesethree.modules.course;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.shaohong.thesethree.R;
import com.shaohong.thesethree.base.BaseFragment;
import com.shaohong.thesethree.modules.course.adapter.CoursePagerAdapter;

public class CourseFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_course, container, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.viewPager_course);
        pager.setAdapter(new CoursePagerAdapter(getActivity().getSupportFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs_course);
        tabs.setViewPager(pager);
        return view;
    }
}
