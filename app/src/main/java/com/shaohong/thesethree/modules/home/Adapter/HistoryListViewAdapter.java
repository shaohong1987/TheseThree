package com.shaohong.thesethree.modules.home.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.HistoryListItemObject;
import com.shaohong.thesethree.database.DbManager;
import com.shaohong.thesethree.myview.CustomSwipeListView;
import com.shaohong.thesethree.myview.SwipeItemView;
import com.shaohong.thesethree.utils.ContextUtils;

import java.util.List;

/**
 * Created by shaohong on 2017/7/17.
 */

public class HistoryListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<HistoryListItemObject> mMessageItems;
    private Context context;
    private SwipeItemView mLastSlideViewWithStatusOn;
    public HistoryListViewAdapter(Context context,List<HistoryListItemObject> mMessageItems) {
        mInflater = LayoutInflater.from(context);
        this.mMessageItems=mMessageItems;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mMessageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SwipeItemView slideView = (SwipeItemView) convertView;
        if (slideView == null) {
            View itemView = mInflater.inflate(R.layout.history_listview_items, null);

            slideView = new SwipeItemView(context);
            slideView.setContentView(itemView);

            holder = new ViewHolder(slideView);
            slideView.setOnSlideListener(new SwipeItemView.OnSlideListener() {

                @Override
                public void onSlide(View view, int status) {
                    // TODO Auto-generated method stub
                    if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
                        mLastSlideViewWithStatusOn.shrink();
                    }

                    if (status == SLIDE_STATUS_ON) {
                        mLastSlideViewWithStatusOn = (SwipeItemView) view;
                    }
                }
            });
            slideView.setTag(holder);
        } else {
            holder = (ViewHolder) slideView.getTag();
        }
        HistoryListItemObject item = mMessageItems.get(position);
        if(CustomSwipeListView.mFocusedItemView!=null){
            CustomSwipeListView.mFocusedItemView.shrink();
        }

        holder.noticeType.setText(item.getType());
        holder.title.setText(item.getTitle());
        holder.dt.setText(item.getDt());
        holder.status.setText(item.getStatus());
        holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mMessageItems.remove(position);
                notifyDataSetChanged();
                DbManager dbManager=new DbManager(ContextUtils.getInstance());
                dbManager.openDB();
                dbManager.updateNoticeShow(mMessageItems.get(position).getId());
                dbManager.closeDB();
            }
        });

        return slideView;
    }
    private static class ViewHolder {
        public TextView noticeType;
        public TextView title;
        public TextView dt;
        public TextView status;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {
            noticeType = (TextView) view.findViewById(R.id.notice_type_text_view);
            title = (TextView) view.findViewById(R.id.notice_title_text_view);
            dt = (TextView) view.findViewById(R.id.dt_text_view);
            status = (TextView) view.findViewById(R.id.status_text_view);
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }
}