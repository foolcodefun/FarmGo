package com.little.farmgo.Data;

import android.provider.BaseColumns;

/**
 * Created by sarah on 05/06/2018.
 */

public final class DatabaseContract {

    public static final String TABLE_SHOPPING_CART = "shopping_cart";

    public static class ShoppingCartTable implements BaseColumns{

        public static final String COLUMN_PRUDUCT_TITILE = "title";
        public static final String COLUMN_PRUDUCT_PRICE = "price";
        public static final String COLUMN_ORDER_NUMBER = "number";
        public static final String COLUMN_ORDER_IMAGE_URL = "image_url";

    }
}
