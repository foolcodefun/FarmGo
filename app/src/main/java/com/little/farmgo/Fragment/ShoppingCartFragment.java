package com.little.farmgo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.little.farmgo.Activity.ProductDetailActivity;
import com.little.farmgo.Adapter.ShoppingCartAdapter;
import com.little.farmgo.Data.ShoppingCart.ShoppingCartList;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;
import com.little.farmgo.R;
import com.little.farmgo.View.ListItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

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
        //todo sendOrder
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

    }

    private class Adapter extends ShoppingCartAdapter{
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
