package com.shaohong.thesethree.modules.exam;


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

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.modules.exam.adapter.ExamRecyclerViewAdapter;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamInfoFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout_exam)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView_exam)
    RecyclerView recyclerView;
    public int examType = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (swipeRefreshLayout.isRefreshing()){
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };

    private List<Exam> data = new ArrayList<>();
    private ExamRecyclerViewAdapter adapter = new ExamRecyclerViewAdapter(data);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_info, container, false);
        ButterKnife.bind(this, view);
        initView();
//        swipeRefreshLayout.setRefreshing(true);
//        new LoadDataThread().start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        new LoadDataThread().start();
    }

    public void initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
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
        adapter.setOnItemClickListener(new ExamRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Exam exam = data.get(position);
                Intent intent = new Intent(ContextUtils.getInstance(), ExamInfoActivity.class);
                intent.putExtra(ConstantUtils.EXAM_INFO, exam);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    class LoadDataThread extends Thread{
        @Override
        public void run() {
            try {
                if(ContextUtils.isLogin){
                    data.clear();
                    List<Exam> exams = ExamModel.GetExamList(getContext(), examType);
                    if(exams!=null&&exams.size()>0){
                        data.addAll(exams);
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);//通过handler发送一个更新数据的标记
        }
    }
}