package com.little.farmgo.Data.ShoppingCart;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.little.farmgo.Data.Product;

import java.util.HashMap;

/**
 * Created by sarah on 2018/6/20.
 */

public class ShoppingListRepository implements LoaderManager.LoaderCallbacks<Cursor> {

    Context mContext;
    private HashMap<Product, Integer> mOrders;

    public ShoppingListRepository(Context context) {
        mContext = context;
        mOrders = ShoppingCartList.getInstance().getOrders();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, DatabaseContract.CONTENT_SHOPPING_CART_URI
                , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ShoppingCartList shoppingCartList = ShoppingCartList.getInstance();
        HashMap<Product, Integer> orders = shoppingCartList.getOrders();
        orders.clear();
        while (data.moveToNext()) {
            String title = data.getString(data.getColumnIndex(DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_TITLE));
            String subtitle = data.getString(data.getColumnIndex(DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_SUBTITLE));
            String imageUrl = data.getString(data.getColumnIndex(DatabaseContract.ShoppingCartTable.COLUMN_IMAGE_URL));
            int price = data.getInt(data.getColumnIndex(DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_PRICE));
            int num = data.getInt(data.getColumnIndex(DatabaseContract.ShoppingCartTable.COLUMN_NUMBER));

            Product product = new Product();
            product.setTitle(title);
            product.setImage_url(imageUrl);
            product.setSubtitle(subtitle);
            product.setPrice(price);
            orders.put(product, num);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public HashMap<Product, Integer> getOreders(){
        return mOrders;
    }

    public void add(Product order, int num) {

        if (mOrders.containsKey(order))
            return;

        mOrders.put(order, num);
        ContentValues values = getContentValues(order, num);
        ShoppingCartService.insert(mContext, values);
    }

    public void update(Product order, int buyNum) {
        if (!mOrders.containsKey(order))
            return;

        mOrders.put(order, buyNum);
        String where = getWhere(order);
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ShoppingCartTable.COLUMN_NUMBER, buyNum);
        ShoppingCartService.update(mContext, values, where, null);
    }

    public void delete(Product order) {

        if (!mOrders.containsKey(order))
            return;

        mOrders.remove(order);
        String where = getWhere(order);
        ShoppingCartService.delete(mContext,where,null);
    }

    public ContentValues getContentValues(Product order, int num) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ShoppingCartTable.COLUMN_IMAGE_URL, order.getImage_url());
        values.put(DatabaseContract.ShoppingCartTable.COLUMN_NUMBER, num);
        values.put(DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_PRICE, order.getPrice());
        values.put(DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_TITLE, order.getTitle());
        values.put(DatabaseContract.ShoppingCartTable.COLUMN_PRODUCT_SUBTITLE, order.getSubtitle());
        return values;
    }


    public String getWhere(Product order) {
        StringBuilder where = new StringBuilder();
        where.append(DatabaseContract.ShoppingCartTable.COLUMN_IMAGE_URL)
                .append(" = \"")
                .append(order.getImage_url())
                .append("\"");

        return where.toString();
    }
}
