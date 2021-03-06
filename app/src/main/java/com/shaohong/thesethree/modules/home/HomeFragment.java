package com.shaohong.thesethree.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.activities.CommonActivity;
import com.shaohong.thesethree.base.BaseFragment;
import com.shaohong.thesethree.bean.CommonData;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.model.HomeModel;
import com.shaohong.thesethree.myview.MyGridView;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;
import com.shaohong.thesethree.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {

    private List<CommonData> list;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.common_grid_view)
    MyGridView myGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        new LoadDataThread().start();
        initGridView();
        return view;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (list.size() > 0) {
                        List<String> images = new ArrayList<>();
                        List<String> titles = new ArrayList<>();
                        for (CommonData item : list) {
                            images.add(item.getImage());
                            titles.add(item.getTitle());
                        }
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                        banner.setImageLoader(new GlideImageLoader());
                        banner.setImages(images);
                        banner.setBannerAnimation(Transformer.Default);
                        banner.setBannerTitles(titles);
                        banner.isAutoPlay(true);
                        banner.setDelayTime(3000);
                        banner.setIndicatorGravity(BannerConfig.CENTER);
                        banner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {

                            }
                        });
                        //banner设置方法全部调用完毕时最后调用
                        banner.start();
                    }
                    break;
            }
        }
    };
    class LoadDataThread extends Thread{
        @Override
        public void run() {
            try {
                list=HomeModel.getBanner(getContext());
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

    //初始化九宫格
    private void initGridView() {
        final ArrayList<HashMap<String, Object>> list= ConstantUtils.COMMON_FUNCTIONS();
        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(),
                list,
                R.layout.item_grid_view,
                new String[]{ConstantUtils.ITEM_IMAGE, ConstantUtils.ITEM_TEXT},
                new int[]{R.id.img_image_view, R.id.title_text_view});
        myGridView.setAdapter(adapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> item=list.get(position);
                if(item!=null){
                    int itemId=Integer.parseInt(String.valueOf(item.get(ConstantUtils.ITEM_IMAGE)));
                    Intent intent=new Intent(getActivity(), CommonActivity.class);
                    intent.putExtra(ConstantUtils.COMMON_ARG,itemId);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

}
