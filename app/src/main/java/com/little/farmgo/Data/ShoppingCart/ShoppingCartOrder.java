package com.little.farmgo.Data.ShoppingCart;

import com.little.farmgo.Data.Product;

import java.util.UUID;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartOrder {
    private Product mProduct;
    private int number;

    public ShoppingCartOrder(Product product, int number) {
        mProduct = product;
        this.number = number;
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
