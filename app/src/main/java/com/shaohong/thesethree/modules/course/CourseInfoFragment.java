package com.shaohong.thesethree.modules.course;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.EduDetail;
import com.shaohong.thesethree.model.CourseModel;
import com.shaohong.thesethree.modules.course.adapter.CourseRecyclerViewAdapter;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseInfoFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout_course)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView_course)
    RecyclerView recyclerView;

    public int courseType = 1;
    private List<EduDetail> data = new ArrayList<>();
    private CourseRecyclerViewAdapter adapter = new CourseRecyclerViewAdapter(data);
    private EduDetail mEduDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        swipeRefreshLayout.setRefreshing(true);
        new LoadDataThread().start();
        return view;
    }

    public void initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataThread().start();
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        //添加点击事件
        adapter.setOnItemClickListener(new CourseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mEduDetail = data.get(position);
                new GetCourseStateThread().start();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (swipeRefreshLayout.isRefreshing()){
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    break;
                case 2:
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    intent.putExtra(ConstantUtils.COURSE_INFO, mEduDetail);
                    startActivity(intent);
                    break;
                case 3:
                    Toast.makeText(getContext(),"操作失败",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    class LoadDataThread extends Thread{
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    data.clear();
                    List<EduDetail> edus = CourseModel.getEdus(getContext(),courseType);
                    if(edus!=null&&edus.size()>0){
                        data.addAll(edus);
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

    private class GetCourseStateThread extends Thread {
        @Override
        public void run() {
            int result=0;
            try {
                if(ContextUtils.isLogin){
                    boolean flag=CourseModel.getEdu(mEduDetail.id);
                    result=flag?2:3;
                }
                Thread.sleep(100);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(result);//通过handler发送一个更新数据的标记
        }
    }
}