package com.little.farmgo.Data.ShoppingCart;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable.COLUMN_IMAGE_URL;
import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable.COLUMN_NUMBER;
import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_PRICE;
import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_TITLE;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartList {

    private static ShoppingCartList shoppingCartList;
    private ArrayList<ShoppingCartOrder> orders;
    private Context mContext;

    private ShoppingCartList(Context context) {
        mContext = context;
    }

    public static ShoppingCartList getInstance(Context context) {
        if (shoppingCartList == null) {
            synchronized (ShoppingCartList.class) {
                if (shoppingCartList == null) {
                    shoppingCartList = new ShoppingCartList(context);
                }
            }
        }
        return shoppingCartList;
    }

    public ArrayList<ShoppingCartOrder> getOrders() {
        return orders;
    }

    public void add(ShoppingCartOrder order) {

        orders.add(order);

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_URL, order.getProduct().getImage_url());
        values.put(COLUMN_NUMBER, order.getNumber());
        values.put(COLUMN_PRODUCT_PRICE, order.getProduct().getPrice());
        values.put(COLUMN_PRODUCT_TITLE, order.getProduct().getTitle());
        ShoppingCartService.insert(mContext, values);
    }

    public boolean remove(ShoppingCartOrder order) {

        String where = COLUMN_IMAGE_URL + " = " + order.getProduct().getImage_url();
        ShoppingCartService.delete(mContext, where, null);

        return orders.remove(order);
    }

    public void update(ShoppingCartOrder order) {
        String where = COLUMN_IMAGE_URL + " = " + order.getProduct().getImage_url();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMBER, order.getNumber());
        ShoppingCartService.update(mContext, values, where, null);

    }
}
