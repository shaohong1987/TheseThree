package com.shaohong.thesethree.modules.exam;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.shaohong.thesethree.R;
import com.shaohong.thesethree.base.BaseFragment;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.modules.exam.adapter.ExamPagerAdapter;
import com.shaohong.thesethree.utils.ContextUtils;

import java.util.HashMap;
import java.util.List;

public class ExamFragment extends BaseFragment {

    private HashMap<String, String> data;
    private TextView xfTextView;
    private TextView ccTextView;
    private TextView zqlTextView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if(data!=null){
                        if (data.containsKey("score"))
                            xfTextView.setText(data.get("score"));
                        if (data.containsKey("cc"))
                            ccTextView.setText(data.get("cc"));
                        if (data.containsKey("zql"))
                            zqlTextView.setText(data.get("zql"));
                    }

                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        ViewPager pager = (ViewPager) view.findViewById(R.id.viewPager_exam);
        pager.setAdapter(new ExamPagerAdapter(getActivity().getSupportFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs_exam);
        tabs.setViewPager(pager);

        xfTextView = (TextView) view.findViewById(R.id.xf_exam);
        ccTextView = (TextView) view.findViewById(R.id.cc_exam);
        zqlTextView = (TextView) view.findViewById(R.id.zql_exam);

        new LoadDataThread().start();
        return view;
    }


    class LoadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    data = ExamModel.GetHead(getContext());
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);//通过handler发送一个更新数据的标记
        }
    }
}
