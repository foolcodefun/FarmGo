package com.little.farmgo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.little.farmgo.Adapter.ShoppingCartAdapter;
import com.little.farmgo.Data.Product;
import com.little.farmgo.Data.ShoppingCart.ShoppingCartList;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;
import com.little.farmgo.Firebase.OrderUtility;
import com.little.farmgo.R;
import com.little.farmgo.View.ListItemTouchHelper;

import java.util.HashMap;
import java.util.Iterator;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ShoppingCartList.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private Button mSendOrderButton;
    private TextView mAmountTextView;
    private Adapter mAdapter;
    private Toolbar mToolbar;
    private TextView mNotice;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_chart_list, container, false);
        ShoppingListRepository shoppingListRepository
                = new ShoppingListRepository(getContext()
                , ShoppingCartList.getInstance().getOrders());
        findViews(view, shoppingListRepository);

        return view;
    }

    private void findViews(View view, ShoppingListRepository shoppingListRepository) {
        mRecyclerView = view.findViewById(R.id.shopping_cart_list);
        mSendOrderButton = view.findViewById(R.id.send_order);
        mSendOrderButton.setOnClickListener(this);
        mAmountTextView = view.findViewById(R.id.amount);
        mToolbar = view.findViewById(R.id.toolbar);
        mNotice = view.findViewById(R.id.notice);

        mAdapter = new Adapter(shoppingListRepository, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setButtonVisible(shoppingListRepository.hasProduct());
        enableSwipeDelete();
    }

    private void setButtonVisible(boolean visible) {
        if (visible) {
            mSendOrderButton.setVisibility(View.VISIBLE);
            mAmountTextView.setVisibility(View.VISIBLE);
            mNotice.setVisibility(View.GONE);
        } else {
            mSendOrderButton.setVisibility(View.INVISIBLE);
            mAmountTextView.setVisibility(View.INVISIBLE);
            mNotice.setVisibility(View.VISIBLE);
        }
    }

    private void enableSwipeDelete() {
        ListItemTouchHelper simpleCallback = new ListItemTouchHelper(0, LEFT, new ListItemTouchHelper.ListItemTouchHelperListener() {
            @Override
            public void onSwipe(RecyclerView.ViewHolder viewHolder) {
                mAdapter.remove((Adapter.ViewHolder) viewHolder);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(View v) {
        //TODO show progress bar
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = (String) snapshot.getKey();
                    Log.d(TAG, "onDataChange: " +
                            "\nproduct key: " + key);
                    Product product = snapshot.getValue(Product.class);
                    Log.d(TAG, "onDataChange: " +
                            "\nproduct number: " + product.getNumber());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        HashMap<Product, Integer> orders = ShoppingCartList.getInstance().getOrders();
        Iterator<Product> iterator = orders.keySet().iterator();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");

        while (iterator.hasNext()) {
            Product product = iterator.next();
            Log.d(TAG, "onClick: " + product.getImageUrl());
            reference.orderByChild("imageUrl").equalTo(product.getImageUrl()).addListenerForSingleValueEvent(postListener);
        }

        sendOrder();


    }

    private void sendOrder() {
        // todo 2. if is log in,send to database
        OrderUtility orderUtility = new OrderUtility(new OrderUtility.OnSendOrderCallback() {
            @Override
            public void onSuccess() {
                //TODO hide progress bar
                //TODO Navigate to OrderListFragment

            }

            @Override
            public void onFailure() {
                //TODO hide progress bar
                //TODO alertDialog
                //TODO should add products number
            }
        });
        String userUid = FirebaseAuth.getInstance().getUid();
        if (userUid != null) {
            orderUtility.sendOrder(userUid);
        } else {
            //todo 3. if is not log in, show alert dialog to log in
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

    }

    private class Adapter extends ShoppingCartAdapter {
        private Adapter(ShoppingListRepository repository, Context context) {
            super(repository, context);
        }

        @Override
        public void setAmount(String amount) {
            mAmountTextView.setText(amount);
        }

        @Override
        public void setButtonVisible(boolean hasProduct) {
            ShoppingCartFragment.this.setButtonVisible(hasProduct);
        }
    }
}
