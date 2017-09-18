package com.shaohong.thesethree.modules.personal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.SimpleExam;
import com.shaohong.thesethree.utils.ContextUtils;

import java.util.List;

/**
 * Created by shaohong on 2017/8/5.
 */

public class SimpleExamRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SimpleExam> data;

    public SimpleExamRecyclerViewAdapter(List<SimpleExam> exams) {
        data = exams;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ContextUtils.getInstance()).inflate(R.layout.item_recycler_view_exam_mistake,
                parent,
                false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            if (!data.isEmpty() && data.size() > position) {
                SimpleExam exam = data.get(position);
                ((ItemViewHolder) holder).nameTextView.setText(exam.getExamName());
                if (onItemClickListener != null) {
                    ((ItemViewHolder) holder).nameTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getLayoutPosition();
                            onItemClickListener.onItemClick(holder.itemView, position);
                        }
                    });

                    ((ItemViewHolder) holder).nameTextView.setOnLongClickListener(new View.OnLongClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public ItemViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name_exam_mistake);
        }
    }
}
