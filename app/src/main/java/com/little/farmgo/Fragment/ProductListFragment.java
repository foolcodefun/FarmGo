package com.little.farmgo.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.little.farmgo.Data.Product;
import com.little.farmgo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarah on 22/03/2018.
 */

public class ProductListFragment extends Fragment {

    public static final String PRODUCTS = "products";

    private RecyclerView mMerchandisesView;
    private FirebaseRecyclerAdapter mAdapter;

    private DatabaseReference mDbRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDbMerchandises = mDbRootRef.child(PRODUCTS);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchandise_list, container, false);
        mMerchandisesView = view.findViewById(R.id.recyclerView);

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>().setQuery(mDbMerchandises,Product.class).build();
        mAdapter = new FirebaseRecyclerAdapter<Product,ProductViewHolder>(options){

            @Override
            public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.merchandise_item,parent,false);
                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
                holder.bind(model);
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.stopListening();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMerchandisesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMerchandisesView.setAdapter(mAdapter);
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mPrice;
        TextView mSubtitle;
        ImageView mImage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mPrice = itemView.findViewById(R.id.price);
            mSubtitle = itemView.findViewById(R.id.subtitle);
            mImage = itemView.findViewById(R.id.imageView);
        }

        public void bind(Product product) {
            mTitle.setText(product.getTitle());
            mPrice.setText(product.getPrice() + getString(R.string.dollar));
            mSubtitle.setText(product.getSubtitle());
            Glide.with(itemView)
                    .load(product.getImage_url())
                    .into(mImage);
        }
    }
}
