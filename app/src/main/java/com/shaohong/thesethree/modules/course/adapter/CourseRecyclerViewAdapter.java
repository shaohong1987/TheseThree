package com.shaohong.thesethree.modules.course.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Course;
import com.shaohong.thesethree.utils.ContextUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by shaohong on 2017/5/18.
 */

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List data;


    public CourseRecyclerViewAdapter(List data) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_course, parent,
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
            Course course = (Course) data.get(position);
            ((ItemViewHolder) holder).nameTextView.setText(course.getName());
            ((ItemViewHolder) holder).dateTextView.setText(course.getDate());
            ((ItemViewHolder) holder).addressTextView.setText(course.getAddress());
            ((ItemViewHolder) holder).statusTextView.setText(course.getStatus());
            ((ItemViewHolder) holder).centTextView.setText(course.getCent());
            ((ItemViewHolder) holder).publisherTextView.setText(course.getPublisher());
            ((ItemViewHolder) holder).publishDateTextView.setText(course.getPublishDate());
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
        TextView dateTextView;
        TextView addressTextView;
        TextView statusTextView;
        TextView centTextView;
        TextView publisherTextView;
        TextView publishDateTextView;

        public ItemViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name_course);
            dateTextView = (TextView) view.findViewById(R.id.date_course);
            addressTextView = (TextView) view.findViewById(R.id.address_course);
            statusTextView = (TextView) view.findViewById(R.id.status_course);
            centTextView = (TextView) view.findViewById(R.id.cent_course);
            publisherTextView = (TextView) view.findViewById(R.id.publisher_course);
            publishDateTextView = (TextView) view.findViewById(R.id.publish_date_course);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }
}