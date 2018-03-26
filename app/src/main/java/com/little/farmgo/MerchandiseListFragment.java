package com.little.farmgo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.little.farmgo.Data.Merchandise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarah on 22/03/2018.
 */

public class MerchandiseListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchandise_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*List<Merchandise> merchandises = new ArrayList<>();
        merchandises.add(new Merchandise("#1",10,"delicious","https://firebasestorage.googleapis.com/v0/b/farmshop-a5d17.appspot.com/o/Screen%20Shot%202018-02-11%20at%203.29.52%20PM.png?alt=media&token=00142343-70da-40e3-9a4e-acc406031e4b"));
        merchandises.add(new Merchandise("#2",20,"delicious","https://firebasestorage.googleapis.com/v0/b/farmshop-a5d17.appspot.com/o/Screen%20Shot%202018-02-11%20at%203.29.52%20PM.png?alt=media&token=00142343-70da-40e3-9a4e-acc406031e4b"));
        mRecyclerView.setAdapter(new MerchandiseAdapter(merchandises));*/

    }

    private class MerchandiseAdapter extends RecyclerView.Adapter<MerchandiseViewHolder> {

        private List<Merchandise> mMerchandises;

        public MerchandiseAdapter(List<Merchandise> merchandises) {
            mMerchandises = merchandises;
        }

        @Override
        public MerchandiseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.merchandise_item, parent, false);
            MerchandiseViewHolder viewHolder = new MerchandiseViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MerchandiseViewHolder holder, int position) {
            Merchandise merchandise = mMerchandises.get(position);
            holder.bind(merchandise);
        }

        @Override
        public int getItemCount() {
            return mMerchandises.size();
        }
    }

    private class MerchandiseViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mPrice;
        TextView mDescribe;
        ImageView mImage;

        public MerchandiseViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mPrice = itemView.findViewById(R.id.price);
            mDescribe = itemView.findViewById(R.id.describe);
            mImage = itemView.findViewById(R.id.imageView);
        }

        public void bind(Merchandise merchandise) {
            mTitle.setText(merchandise.getTitle());
            mPrice.setText(merchandise.getPrice() + "");
            mDescribe.setText(merchandise.getDescribe());
            Glide.with(itemView)
                    .load(merchandise.getImageURL())
                    .into(mImage);
        }
    }
}
