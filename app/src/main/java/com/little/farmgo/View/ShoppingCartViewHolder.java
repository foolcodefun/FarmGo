package com.little.farmgo.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.little.farmgo.Data.Product;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;
import com.little.farmgo.R;

import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable.COLUMN_IMAGE_URL;

/**
 * Created by sarah on 2018/7/12.
 */

public abstract class ShoppingCartViewHolder extends SwipeableViewHolder implements View.OnClickListener {

    private final ImageView mImageView;
    private final TextView mTitle;
    private final TextView mPrice;
    private final TextView mBuyNum;
    private final TextView mSubtitle;
    private final TextView mSubtotal;

    private final Context mContext;
    private final ShoppingListRepository mRepository;

    private Product mProduct;

    public ShoppingCartViewHolder(View itemView, Context context, ShoppingListRepository repository) {
        super(itemView);

        mContext = context;
        mRepository = repository;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mImageView = itemView.findViewById(R.id.image);
        mTitle = itemView.findViewById(R.id.title);
        mSubtitle = itemView.findViewById(R.id.subtitle);
        mPrice = itemView.findViewById(R.id.price);
        mBuyNum = itemView.findViewById(R.id.buying_num);
        TextView plus = itemView.findViewById(R.id.plus);
        TextView minus = itemView.findViewById(R.id.minus);
        mSubtotal = itemView.findViewById(R.id.subtotal);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
    }

    public void bind(Product order, int num) {

        mProduct = order;

        String price = mProduct.getPrice() + mContext.getString(R.string.dollar);
        String subtotal = getSubtotalString(num);

        mTitle.setText(mProduct.getTitle());
        mSubtitle.setText(mProduct.getSubtitle());
        mPrice.setText(price);
        mBuyNum.setText(String.valueOf(num));
        mSubtotal.setText(subtotal);
        Glide.with(itemView)
                .load(mProduct.getImageUrl())
                .into(mImageView);
    }

    @Override
    public void onClick(View v) {

        refreshProduct(v);
    }

    private synchronized void updateBuyNum(View v) {
        int num = mProduct.getNumber();//TODO 從firebase中再次更新數量
        int buyNum = Integer.parseInt(mBuyNum.getText().toString());
        switch (v.getId()) {
            case R.id.plus:
                if (buyNum >= num) {
                    buyNum = num;
                    showToast("已達庫存上限");
                } else {
                    buyNum++;
                }
                break;
            case R.id.minus:
                if (buyNum <= 1) {
                    buyNum = 1;
                    showToast("購物數量下限為 1");
                } else {
                    buyNum--;
                }
                break;
            default:
                break;
        }


        String subtotal = getSubtotalString(buyNum);
        mSubtotal.setText(subtotal);
        mBuyNum.setText(String.valueOf(buyNum));
        mRepository.update(mProduct, buyNum);

        setAmount();
    }

    @NonNull
    private String getSubtotalString(int buyNum) {
        return mContext.getString(R.string.subtotal)
                + ": " + String.valueOf(mProduct.getPrice() * buyNum)
                + mContext.getString(R.string.dollar);
    }

    private void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    private void refreshProduct(final View v) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("products").orderByChild(COLUMN_IMAGE_URL).equalTo(mProduct.getImageUrl());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mProduct = ds.getValue(Product.class);
                        updateBuyNum(v);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public abstract void setAmount();
}
