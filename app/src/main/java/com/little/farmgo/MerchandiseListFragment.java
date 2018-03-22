package com.little.farmgo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sarah on 22/03/2018.
 */

public class MerchandiseListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchandise_list,container,false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private class MerchandiseAdapter extends RecyclerView.Adapter<MerchandiseViewHolder>{

        private List<Merchandise> mMerchandises;

        @Override
        public MerchandiseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.merchandise_item,parent,false);
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
            return 0;
        }
    }

    private class MerchandiseViewHolder extends RecyclerView.ViewHolder{

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
            mPrice.setText(merchandise.getPrice());
            mDescribe.setText(merchandise.getDescribe());
        }
    }
}
