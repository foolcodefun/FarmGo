package com.little.farmgo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.little.farmgo.Data.Product;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;
import com.little.farmgo.R;
import com.little.farmgo.View.ShoppingCartViewHolder;

import java.util.HashMap;

/**
 * Created by sarah on 2018/7/13.
 **/


public abstract class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    ShoppingListRepository mRepository;
    HashMap<Product, Integer> shoppingList;
    Context mContext;

    public ShoppingCartAdapter(ShoppingListRepository repository, Context context) {
        mRepository = repository;
        this.shoppingList = repository.getOreders();
        mContext = context;
    }

    public abstract void setAmount(String amount);

    public abstract void setButtonVisible(boolean hasProduct);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.shopping_cart_item, parent, false);
        return new ViewHolder(view, mContext, mRepository);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product order = (Product) shoppingList.keySet().toArray()[position];
        int num = shoppingList.get(order);
        setAmount(getAmount());
        holder.bind(order, num);
    }

    @NonNull
    private String getAmount() {
        return mContext.getString(R.string.amount) + ": "
                + mRepository.getAmount()
                + mContext.getString(R.string.dollar);
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public void remove(ViewHolder holder) {
        Product order = (Product) shoppingList.keySet().toArray()[holder.getAdapterPosition()];
        mRepository.delete(order);

        setAmount(getAmount());

        notifyDataSetChanged();
        setButtonVisible(mRepository.hasProduct());
    }

    public class ViewHolder extends ShoppingCartViewHolder {
        public ViewHolder(View itemView, Context context, ShoppingListRepository repository) {
            super(itemView, context, repository);
        }

        @Override
        public void setAmount() {
            ShoppingCartAdapter.this.setAmount(getAmount());
        }
    }
}
