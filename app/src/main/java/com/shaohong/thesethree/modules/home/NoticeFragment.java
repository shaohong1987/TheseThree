package com.shaohong.thesethree.modules.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.shaohong.thesethree.bean.HistoryListItemObject;
import com.shaohong.thesethree.database.DbManager;
import com.shaohong.thesethree.model.HomeModel;
import com.shaohong.thesethree.modules.home.Adapter.HistoryListViewAdapter;
import com.shaohong.thesethree.myview.CustomSwipeListView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static int rowId = 0;
    private CustomSwipeListView mListView;
    private List<HistoryListItemObject> mMessageItems = new ArrayList<>();
    private HistoryListViewAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (swipeRefreshLayout.isRefreshing()) {
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };

    public NoticeFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        DbManager dbManager = new DbManager(getContext());
        dbManager.openDB();
        mMessageItems.clear();
        List<HistoryListItemObject> data = dbManager.queryNotices();
        if (data != null && data.size() > 0) {
            mMessageItems.addAll(data);
        }
        rowId = dbManager.getMaxNoticeId();
        dbManager.closeDB();
        new LoadDataThread().start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_notice, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_notice);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataThread().start();
            }
        });
        mAdapter = new HistoryListViewAdapter(getContext(), mMessageItems);
        mListView = (CustomSwipeListView) view.findViewById(R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        swipeRefreshLayout.setRefreshing(true);
        DbManager dbManager = new DbManager(getContext());
        dbManager.openDB();
        List<HistoryListItemObject> data = dbManager.queryNotices();
        if (data != null && data.size() > 0) {
            mMessageItems.addAll(data);
        }
        rowId = dbManager.getMaxNoticeId();
        dbManager.closeDB();
        new LoadDataThread().start();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HistoryListItemObject object = mMessageItems.get(position);
        Intent intent = new Intent(getActivity(), NoticeActivity.class);
        intent.putExtra(ConstantUtils.NOTICE, object);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            new LoadDataThread().start();
        }
    }

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    List<HistoryListItemObject> data = HomeModel.getNotice(getContext(), rowId);
                    if (data != null && data.size() > 0) {
                        DbManager dbManager = new DbManager(getContext());
                        dbManager.openDB();
                        for (int i = 0; i < data.size(); i++) {
                            if (mMessageItems.size() > 0) {
                                boolean flag = true;
                                for (int j = 0; j < mMessageItems.size(); j++) {
                                    if (mMessageItems.get(j).getId() == data.get(i).getId()) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    dbManager.insertNotice(data.get(i));
                                    mMessageItems.add(data.get(i));
                                }
                            } else {

                            }
                        }
                        rowId = dbManager.getMaxNoticeId();
                        dbManager.closeDB();
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }
}
