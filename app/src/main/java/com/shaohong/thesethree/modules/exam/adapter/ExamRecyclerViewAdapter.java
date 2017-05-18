package com.shaohong.thesethree.modules.exam.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.utils.ContextUtils;

import java.util.List;

/**
 * Created by shaohong on 2017/5/10.
 */

public class ExamRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List data;


    public ExamRecyclerViewAdapter(List data) {
        this.context = ContextUtils.getInstance();
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_exam, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.progress_bar_recycler_view, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Resources resource = context.getResources();
            Exam exam = (Exam) data.get(position);
            ((ItemViewHolder) holder).nameTextView.setText(exam.getName());
            ((ItemViewHolder) holder).nameTextView.setTextColor(resource.getColorStateList(R.color.colorBlack));
            ((ItemViewHolder) holder).datetimeTextView.setText(exam.getDatetime());
            ((ItemViewHolder) holder).addressTextView.setText(exam.getAddress());
            switch (exam.getStatus()){
                case 0:
                    ((ItemViewHolder) holder).statusTextView.setText("可报名");
                    ((ItemViewHolder) holder).statusTextView.setTextColor(resource.getColorStateList(R.color.colorPink));
                    break;
                case 1:
                    ((ItemViewHolder) holder).statusTextView.setText("已报名");
                    ((ItemViewHolder) holder).statusTextView.setTextColor(resource.getColorStateList(R.color.colorBlack));
                    break;
                case 2:
                    ((ItemViewHolder) holder).statusTextView.setText("监考");
                    ((ItemViewHolder) holder).statusTextView.setTextColor(resource.getColorStateList(R.color.colorBlue));
                    break;
            }

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView datetimeTextView;
        TextView addressTextView;
        TextView statusTextView;

        public ItemViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name_exam);
            datetimeTextView = (TextView) view.findViewById(R.id.datetime_exam);
            addressTextView = (TextView) view.findViewById(R.id.address_exam);
            statusTextView = (TextView) view.findViewById(R.id.status_exam);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }
}