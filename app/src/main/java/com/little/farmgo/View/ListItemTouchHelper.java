package com.little.farmgo.View;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by sarah on 2018/7/12.
 */

public class ListItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    public interface ListItemTouchHelperListener {
        void onSwipe(RecyclerView.ViewHolder viewHolder);
    }

    private ListItemTouchHelperListener mListener;

    public ListItemTouchHelper(int dragDirs, int swipeDirs, ListItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        mListener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            View view = ((SwipeableViewHolder) viewHolder).mForeground;
            getDefaultUIUtil().onSelected(view);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        getDefaultUIUtil().clearView(((SwipeableViewHolder) viewHolder).mForeground);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDrawOver(c, recyclerView, ((SwipeableViewHolder) viewHolder).mForeground
                , dX, dY, actionState, isCurrentlyActive);
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDraw(c, recyclerView, ((SwipeableViewHolder) viewHolder).mForeground, dX, dY, actionState, isCurrentlyActive);
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onSwipe(viewHolder);
    }
}
