package com.little.farmgo.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.little.farmgo.View.ListItemTouchHelper;
import com.little.farmgo.View.SwipeableViewHolder;

import java.util.HashMap;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable.COLUMN_IMAGE_URL;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private Button mSendOrderButton;
    private TextView mAmountTextView;
    private ShoppingCartAdapter mAdapter;
    private int mAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_chart_list, container, false);
        mRecyclerView = view.findViewById(R.id.shopping_cart_list);
        mSendOrderButton = view.findViewById(R.id.send_order);
        mSendOrderButton.setOnClickListener(this);
        mAmountTextView = view.findViewById(R.id.amount);


        ShoppingListRepository shoppingListRepository = new ShoppingListRepository(getContext());
        mAdapter = new ShoppingCartAdapter(shoppingListRepository);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        hasProduct(shoppingListRepository);
        enableSwipeDelete();

        return view;
    }

    private void hasProduct(ShoppingListRepository shoppingListRepository) {
        if (shoppingListRepository.hasProduct()) {
            mSendOrderButton.setVisibility(View.VISIBLE);
            mAmountTextView.setVisibility(View.VISIBLE);
        } else {
            mSendOrderButton.setVisibility(View.INVISIBLE);
            mAmountTextView.setVisibility(View.INVISIBLE);
        }
    }


    private void enableSwipeDelete() {
        ListItemTouchHelper simpleCallback = new ListItemTouchHelper(0, LEFT, new ListItemTouchHelper.ListItemTouchHelperListener() {
            @Override
            public void onSwipe(RecyclerView.ViewHolder viewHolder) {
                mAdapter.remove(viewHolder.getAdapterPosition());
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(View v) {
//todo sendOrder
    }

    public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

        ShoppingListRepository mRepository;
        HashMap<Product, Integer> shoppingList;


        public ShoppingCartAdapter(ShoppingListRepository repository) {
            mRepository = repository;
            this.shoppingList = repository.getOreders();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.shopping_cart_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Product order = (Product) shoppingList.keySet().toArray()[position];
            int num = shoppingList.get(order);
            holder.bind(order, num);
        }

        @Override
        public int getItemCount() {
            return shoppingList.size();
        }

        public void remove(int position) {
            Product order = (Product) shoppingList.keySet().toArray()[position];
            int num = shoppingList.get(order);
            mAmount -= order.getPrice() * num;
            mAmountTextView.setText(getString(R.string.amount) + ": "
                    + mAmount
                    + getString(R.string.dollar));
            mRepository.delete(order);
            hasProduct(mRepository);
            notifyDataSetChanged();

        }

        public class ViewHolder extends SwipeableViewHolder implements View.OnClickListener {

            private final ImageView mImageView;
            private final TextView mTitle;
            private final TextView mPrice;
            private final TextView mBuyNum;
            private final TextView mSubtitle;
            private final TextView mPlus;
            private final TextView mMinus;
            private final TextView mSubtotal;

            private Product mProduct;
            public final View mForeground;
            public final View mBackground;

            public ViewHolder(View itemView) {
                super(itemView);

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
                mPlus = itemView.findViewById(R.id.plus);
                mMinus = itemView.findViewById(R.id.minus);
                mForeground = itemView.findViewById(R.id.foreground);
                mBackground = itemView.findViewById(R.id.background);
                mSubtotal = itemView.findViewById(R.id.subtotal);

                mPlus.setOnClickListener(this);
                mMinus.setOnClickListener(this);
            }

            public void bind(Product order, int num) {

                mProduct = order;

                int subtotal = mProduct.getPrice() * num;
                mAmount += subtotal;

                mTitle.setText(mProduct.getTitle());
                mSubtitle.setText(mProduct.getSubtitle());
                mPrice.setText(mProduct.getPrice() + getString(R.string.dollar));
                mBuyNum.setText(num + "");
                mSubtotal.setText(getString(R.string.subtotal) + ": " + subtotal + getString(R.string.dollar));
                Glide.with(itemView)
                        .load(mProduct.getImageUrl())
                        .into(mImageView);

                mAmountTextView.setText(getString(R.string.amount) + ": "
                        + mAmount
                        + getString(R.string.dollar));
            }

            @Override
            public void onClick(View v) {

                refreshProduct(v);
            }

            private synchronized void updateBuyNum(View v) {
                int num = mProduct.getNumber();
                int buyNum = Integer.parseInt(mBuyNum.getText().toString());
                switch (v.getId()) {
                    case R.id.plus:
                        if (buyNum >= num) {
                            buyNum = num;
                            showToast("已達庫存上限");
                        } else {
                            buyNum++;
                            mAmount += mProduct.getPrice();
                        }
                        break;
                    case R.id.minus:
                        if (buyNum <= 1) {
                            buyNum = 1;
                            showToast("購物數量下限為 1");
                        } else {
                            buyNum--;
                            mAmount -= mProduct.getPrice();
                        }
                        break;
                    default:
                        break;
                }
                mBuyNum.setText(buyNum + "");
                mSubtotal.setText(getString(R.string.subtotal)
                        + ": " + String.valueOf(mProduct.getPrice() * buyNum)
                        + getString(R.string.dollar));
                mAmountTextView.setText(getString(R.string.amount) + ": "
                        + mAmount
                        + getString(R.string.dollar));
                mRepository.update(mProduct, buyNum);
            }

            private void showToast(String text) {
                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
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
        }
    }

}
