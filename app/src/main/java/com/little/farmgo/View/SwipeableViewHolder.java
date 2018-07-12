package com.little.farmgo.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.little.farmgo.Fragment.ShoppingCartFragment;
import com.little.farmgo.R;

/**
 * Created by sarah on 2018/7/12.
 */

public class SwipeableViewHolder extends RecyclerView.ViewHolder {
    public final View mForeground;
    public final View mBackground;

    public SwipeableViewHolder(View itemView) {
        super(itemView);
        mForeground = itemView.findViewById(R.id.foreground);
        mBackground = itemView.findViewById(R.id.background);
    }
}
