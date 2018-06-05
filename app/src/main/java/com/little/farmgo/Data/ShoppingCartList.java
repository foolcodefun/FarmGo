package com.little.farmgo.Data;

import java.util.ArrayList;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartList {

    private static ShoppingCartList shoppingCartList;
    private static ArrayList<Order> orders;

    private ShoppingCartList(){}

    private static ShoppingCartList getInstance(){
        if(shoppingCartList==null){
            shoppingCartList = new ShoppingCartList();
        }
        return shoppingCartList;
    }
}
