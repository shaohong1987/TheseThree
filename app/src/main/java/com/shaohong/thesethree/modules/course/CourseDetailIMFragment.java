package com.shaohong.thesethree.modules.course;


import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Pinlun;
import com.shaohong.thesethree.model.CourseModel;
import com.shaohong.thesethree.modules.course.adapter.CourseDiscusRecyclerViewAdapter;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseDetailIMFragment extends Fragment {

    public int courseId;
    private List<Pinlun> data = new ArrayList<>();
    public ViewGroup comment;

    @BindView(R.id.swipeRefreshLayout_course_discus)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView_course_discus)
    RecyclerView recyclerView;
    private CourseDiscusRecyclerViewAdapter adapter;
    EditText comment_content;
    Button comment_publish;
    Button comment_send;
    private int type = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_detail_im, container, false);
        ButterKnife.bind(this, view);
        comment = (ViewGroup) view.findViewById(R.id.rl_comment);
        comment_content = (EditText) view.findViewById(R.id.comment_content);
        comment_publish = (Button) view.findViewById(R.id.comment_publish);
        comment_send = (Button) view.findViewById(R.id.comment_send);
        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PublishCommentThread().start();
            }
        });
        comment_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                comment.setVisibility(View.VISIBLE);
                ContextUtils.temp = 0;
            }
        });
        TextView textView = (TextView) view.findViewById(R.id.hide_down);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.setVisibility(View.GONE);
                InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
            }
        });
        adapter = new CourseDiscusRecyclerViewAdapter(data, comment);
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
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (swipeRefreshLayout.isRefreshing()) {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    break;
                case 2: {
                    comment.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(true);
                    new LoadDataThread().start();
                    comment_content.setText("");
                    InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                    break;
                }
            }
        }
    };

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    data.clear();
                    List<Pinlun> pinluns = CourseModel.getDiscus(String.valueOf(courseId));
                    if (pinluns != null && pinluns.size() > 0) {
                        data.addAll(pinluns);
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException | ParseException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

    class PublishCommentThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    if (ContextUtils.temp == 0)
                        CourseModel.adddiscus(getContext(), String.valueOf(courseId), String.valueOf(comment_content
                                .getText()));
                    else {
                        CourseModel.addreplys(getContext(), String.valueOf(comment_content.getText()), ContextUtils
                                .temp);
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(2);
        }
    }
}
