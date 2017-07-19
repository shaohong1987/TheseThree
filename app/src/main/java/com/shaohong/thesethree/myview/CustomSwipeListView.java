package com.shaohong.thesethree.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by shaohong on 2017/7/17.
 */

public class CustomSwipeListView extends ListView {
    private static final String TAG = "ListViewCompat";

    public static SwipeItemView mFocusedItemView;

    public CustomSwipeListView(Context context) {
        super(context);
    }

    public CustomSwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwipeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);

        if (item != null) {
            try {
                ((SwipeItemView) item).shrink();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int position = pointToPosition(x, y);
                if (position != INVALID_POSITION) {
                    int firstPos = getFirstVisiblePosition();
                    mFocusedItemView = (SwipeItemView) getChildAt(position - firstPos);
                }
            }
            default:
                break;
        }
        if (mFocusedItemView != null) {
            mFocusedItemView.onRequireTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
}
