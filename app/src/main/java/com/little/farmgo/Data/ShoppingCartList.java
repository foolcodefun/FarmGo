package com.little.farmgo.Data;

import java.util.ArrayList;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartList {

    private static ShoppingCartList shoppingCartList = new ShoppingCartList();
    private ArrayList<ShoppingCartOrder> orders;

    private ShoppingCartList(){}

    private static ShoppingCartList getInstance(){
        if(shoppingCartList==null){
            synchronized (ShoppingCartList.class){
                if(shoppingCartList==null){
                    shoppingCartList = new ShoppingCartList();
                }
            }
        }
        return shoppingCartList;
    }

    public void add(ShoppingCartOrder order){
        orders.add(order);
    }

    public boolean remove(ShoppingCartOrder order){
        return orders.remove(order);
    }
}
