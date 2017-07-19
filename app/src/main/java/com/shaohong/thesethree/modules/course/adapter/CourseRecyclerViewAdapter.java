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
import com.shaohong.thesethree.bean.Edu;
import com.shaohong.thesethree.bean.EduDetail;
import com.shaohong.thesethree.utils.ContextUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by shaohong on 2017/5/18.
 */

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
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
        return data.size() == 0 ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_course, parent,
                false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            EduDetail course = (EduDetail) data.get(position);
            ((ItemViewHolder) holder).nameTextView.setText(course.zhuti);
            ((ItemViewHolder) holder).dateTextView.setText(course.time);
            ((ItemViewHolder) holder).addressTextView.setText(course.adress);
            ((ItemViewHolder) holder).statusTextView.setText(course.state>0?"已报名":"未报名");
            ((ItemViewHolder) holder).centTextView.setText(String.valueOf(course.score));
            ((ItemViewHolder) holder).publisherTextView.setText(course.org);
            ((ItemViewHolder) holder).publishDateTextView.setText(course.recordtime);
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
}