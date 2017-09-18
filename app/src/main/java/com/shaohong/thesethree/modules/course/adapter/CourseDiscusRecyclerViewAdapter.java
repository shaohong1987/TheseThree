package com.shaohong.thesethree.modules.course.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Pinlun;
import com.shaohong.thesethree.myview.ListViewForRecyclerView;
import com.shaohong.thesethree.utils.ContextUtils;

import java.util.List;

/**
 * Created by shaohong on 2017/8/29.
 */

public class CourseDiscusRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List data;
    private Context context;
    private ViewGroup mContext;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CourseDiscusRecyclerViewAdapter(List<Pinlun> pinluns, ViewGroup activity) {
        data = pinluns;
        this.context = ContextUtils.getInstance();
        mContext = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_course_discus, parent,
                false);
        return new CourseDiscusRecyclerViewAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            if (position >= 0 && position < data.size()) {
                final Pinlun pinlun = (Pinlun) data.get(position);
                ((ItemViewHolder) holder).discus_username_text_view.setText(pinlun.name);
                ((ItemViewHolder) holder).discus_content_text_view.setText(pinlun.shoushouText);
                ((ItemViewHolder) holder).discus_time_text_view.setText(pinlun.time.substring(5));
                ((ItemViewHolder) holder).discus_reply_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
                                .INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        mContext.setVisibility(View.VISIBLE);
                        ContextUtils.temp = pinlun.id;

                    }
                });
                ((ItemViewHolder) holder).discus_replys_content_list_view.setAdapter(new ArrayAdapter<>(context,
                        android.R.layout.simple_expandable_list_item_1, pinlun.replys));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView discus_username_text_view;
        TextView discus_content_text_view;
        TextView discus_time_text_view;
        ImageButton discus_reply_button;
        ListViewForRecyclerView discus_replys_content_list_view;

        public ItemViewHolder(View itemView) {
            super(itemView);
            discus_username_text_view = (TextView) itemView.findViewById(R.id.discus_username_text_view);
            discus_content_text_view = (TextView) itemView.findViewById(R.id.discus_content_text_view);
            discus_time_text_view = (TextView) itemView.findViewById(R.id.discus_time_text_view);
            discus_reply_button = (ImageButton) itemView.findViewById(R.id.discus_reply_button);
            discus_replys_content_list_view = (ListViewForRecyclerView) itemView.findViewById(R.id
                    .discus_replys_content_list_view);
        }
    }
}
