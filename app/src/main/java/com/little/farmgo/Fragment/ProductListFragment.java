package com.little.farmgo.Fragment;


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

    private RecyclerView mMerchandisesView;
    private MerchandiseAdapter mMerchandiseAdapter;

    private DatabaseReference mDbRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDbMerchandises = mDbRootRef.child(Product.PRODUCTS);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchandise_list, container, false);
        mMerchandisesView = view.findViewById(R.id.recyclerView);
        mMerchandiseAdapter = new MerchandiseAdapter(new ArrayList<Product>());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMerchandisesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMerchandisesView.setAdapter(mMerchandiseAdapter);
        getFirebaseMerchandises();

    }

    private void getFirebaseMerchandises(){
        mDbMerchandises.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                /*String title = (String) dataSnapshot.child(Product.TITLE).getValue();
                long price = (long)dataSnapshot.child(Product.PRICE).getValue();
                String subtitle =  (String)dataSnapshot.child(Product.SUBTITLE).getValue();
                String imageURL = (String)dataSnapshot.child(Product.IMAGE_URL).getValue();
                Product product = new Product(title,price,subtitle,imageURL);*/
                Product product = dataSnapshot.getValue(Product.class);
                mMerchandiseAdapter.add(product);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class MerchandiseAdapter extends RecyclerView.Adapter<MerchandiseViewHolder> {

        private List<Product> mProducts;

        public MerchandiseAdapter(List<Product> products) {
            mProducts = products;
        }

        public void add(Product product){
            mProducts.add(product);
            notifyItemChanged(mProducts.size());
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
            Product product = mProducts.get(position);
            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

    private class MerchandiseViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mPrice;
        TextView mSubtitle;
        ImageView mImage;

        public MerchandiseViewHolder(View itemView) {
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
