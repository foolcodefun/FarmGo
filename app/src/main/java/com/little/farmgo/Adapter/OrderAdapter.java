package com.little.farmgo.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.little.farmgo.Data.Order.Order;

import java.util.ArrayList;

/**
 * Created by sarah on 2018/10/21.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    ArrayList<Order> orders;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
