package com.shaohong.thesethree.modules.personal;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.bean.SimpleExam;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.model.UserModel;
import com.shaohong.thesethree.modules.personal.adapter.SimpleExamRecyclerViewAdapter;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MistakeBookFragment extends Fragment {

    private static RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<SimpleExam> data = new ArrayList<>();
    private SimpleExamRecyclerViewAdapter adapter = new SimpleExamRecyclerViewAdapter(data);

    public MistakeBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_mistake_book, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.grid_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.grid_swipe_refresh);
        initView();
        return view;
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
        adapter.setOnItemClickListener(new SimpleExamRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (ContextUtils.isLogin) {
                    SimpleExam exam = data.get(position);
                    List<Paper> papers = UserModel.getMistakeBook(getContext(), exam.getId());
                    Intent intent = new Intent(ContextUtils.getInstance(), MistakeBookActivity.class);
                    intent.putExtra(ConstantUtils.EXAM_INFO, (Serializable) papers);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        new LoadDataThread().start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (swipeRefreshLayout.isRefreshing()) {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    data.clear();
                    List<SimpleExam> exams = ExamModel.GetSimpleExamList(getContext());
                    if (exams != null && exams.size() > 0) {
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
