package com.shaohong.thesethree.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by shaohong on 2017/8/29.
 */

public class ListViewForRecyclerView extends ListView {
    public ListViewForRecyclerView(Context context) {
        super(context);
    }

    public ListViewForRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}