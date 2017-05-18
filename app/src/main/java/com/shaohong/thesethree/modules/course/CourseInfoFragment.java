package com.shaohong.thesethree.modules.course;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Course;
import com.shaohong.thesethree.modules.course.adapter.CourseRecyclerViewAdapter;
import com.shaohong.thesethree.utils.ConstantUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseInfoFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout_course)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView_course)
    RecyclerView recyclerView;

    public int courseType = 1;
    boolean isLoading;
    private Handler handler = new Handler();
    private List<Course> data = new ArrayList<>();
    private CourseRecyclerViewAdapter adapter = new CourseRecyclerViewAdapter(data);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        getData();
                    }
                }, 2000);
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
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

        //添加点击事件
        adapter.setOnItemClickListener(new CourseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Course course = data.get(position);
                Intent intent = new Intent(getActivity(), CourseActivity.class);
                intent.putExtra(ConstantUtils.COURSE_INFO, course);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1500);
    }

    private void getData() {
        String tag = "";
        switch (courseType) {
            case 0:
                tag = "院内培训";
                break;
            case 1:
                tag = "院外培训";
                break;
            case 2:
                tag = "我参加的";
                break;
        }
        int size = data.size();
        for (int i = 0; i < size + 1; i++) {
            Course course = new Course();
            course.setName(tag + "-三基考试" + i);
            course.setPublishDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date().getTime()));
            course.setAddress("第" + i + "会议室");
            course.setStatus("已报名");
            course.setPublisher("护理部");
            course.setCent("100分");
            course.setDate("2017-05-19~2017-05-29");
            data.add(course);
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyItemRemoved(adapter.getItemCount());
    }
}