package com.shaohong.thesethree.modules.exam.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.utils.ContextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by shaohong on 2017/5/m10.
 */

public class ExamRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
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
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_exam, parent,
                false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Resources resource = context.getResources();
            if(!data.isEmpty()&&data.size()>position)
            {
                Exam exam = (Exam) data.get(position);
                ((ItemViewHolder) holder).nameTextView.setText(exam.getTitle());
                ((ItemViewHolder) holder).nameTextView.setTextColor(resource.getColorStateList(R.color.colorBlack));
                ((ItemViewHolder) holder).datetimeTextView.setText(exam.getStartTime());
                ((ItemViewHolder) holder).addressTextView.setText(exam.getAddress());
                ((ItemViewHolder) holder).imageImageView.setImageResource(getImageId(exam.getStartTime()));
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
    }

    int getImageId(String dt){
        int result=0;
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dt);
            int i=date.getMonth()+1;
            switch (i) {
                case 1:
                    result = R.drawable.m1;
                    break;
                case 2:
                    result = R.drawable.m2;
                    break;
                case 3:
                    result = R.drawable.m3;
                    break;
                case 4:
                    result = R.drawable.m4;
                    break;
                case 5:
                    result = R.drawable.m5;
                    break;
                case 6:
                    result = R.drawable.m6;
                    break;
                case 7:
                    result = R.drawable.m7;
                    break;
                case 8:
                    result = R.drawable.m8;
                    break;
                case 9:
                    result = R.drawable.m9;
                    break;
                case 10:
                    result = R.drawable.m10;
                    break;
                case 11:
                    result = R.drawable.m11;
                    break;
                case 12:
                    result = R.drawable.m12;
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView datetimeTextView;
        TextView addressTextView;
        TextView statusTextView;
        ImageView imageImageView;
        public ItemViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.name_exam);
            datetimeTextView = (TextView) view.findViewById(R.id.datetime_exam);
            addressTextView = (TextView) view.findViewById(R.id.address_exam);
            statusTextView = (TextView) view.findViewById(R.id.status_exam);
            imageImageView= (ImageView) view.findViewById(R.id.image_item_recycler_view_exam);
        }
    }
}