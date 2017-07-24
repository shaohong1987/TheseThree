package com.shaohong.thesethree.modules.course;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.KJ;
import com.shaohong.thesethree.model.CourseModel;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseDetailInfoFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout_course_detail)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.kj_course_detail_list_view)
    ListView mListView;
    MyAdapter adapter=new MyAdapter();
    public int courseId = 1;
    private List<KJ> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_detail_info, container, false);
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
        mListView.setAdapter(adapter);
        //添加点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打开附件
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
                    }
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
                    List< KJ> kjs = CourseModel.getEduDetail(courseId);
                    if(kjs!=null&&kjs.size()>0){
                        data.addAll(kjs);
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}