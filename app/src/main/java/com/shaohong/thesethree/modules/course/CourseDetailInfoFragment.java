package com.shaohong.thesethree.modules.course;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.KJ;
import com.shaohong.thesethree.model.CourseModel;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CourseDetailInfoFragment extends Fragment {
    ListView listView;
    ArrayAdapter<String> adapter;
    public int courseId = 1;
    private List<KJ> mKJs = new ArrayList<>();
    private List<String> data=new ArrayList<>();

    public CourseActivity mCourseActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_detail_info, container, false);
        ButterKnife.bind(this, view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,data);
        listView = (ListView) view.findViewById(R.id.kj_course_detail_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(mKJs.get(position).kjurl);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        new LoadDataThread().start();
        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(data!=null&&data.size()>0){
                        adapter.notifyDataSetChanged();
                    }
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
                    mKJs.clear();
                    List<KJ> kjs = CourseModel.getEduDetail(getContext(), courseId);
                    if(kjs!=null&&kjs.size()>0){
                        for (int i=0;i<kjs.size();i++){
                            mKJs.add(kjs.get(i));
                            data.add(kjs.get(i).kjname);
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }
}