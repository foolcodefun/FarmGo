package com.little.farmgo.Data.ShoppingCart;

import android.content.ContentValues;
import android.content.Context;

import com.little.farmgo.Data.Product;

import java.util.ArrayList;
import java.util.HashMap;

import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartList {

    private static ShoppingCartList shoppingCartList;
    private HashMap<Product,Integer> orders;

    private ShoppingCartList() {
        orders = new HashMap<Product,Integer>();
    }

    public static ShoppingCartList getInstance() {
        if (shoppingCartList == null) {
            synchronized (ShoppingCartList.class) {
                if (shoppingCartList == null) {
                    shoppingCartList = new ShoppingCartList();
                }
            }
        }
        return shoppingCartList;
    }

    public HashMap<Product,Integer> getOrders() {
        return orders;
    }
}
