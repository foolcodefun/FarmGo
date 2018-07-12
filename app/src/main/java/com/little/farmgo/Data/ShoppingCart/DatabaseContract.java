package com.little.farmgo.Data.ShoppingCart;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sarah on 05/06/2018.
 */

public final class DatabaseContract {

    public static final String TABLE_SHOPPING_CART = "shopping_cart";

    public static class ShoppingCartTable implements BaseColumns{

        public static final String COLUMN_PRODUCT_TITLE = "title";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_IMAGE_URL = "imageUrl";

        public static final String COLUMN_PRODUCT_SUBTITLE = "subtitle";
    }

    public static String AUTHORITY = "com.little.farmgo";

    public static Uri CONTENT_SHOPPING_CART_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_SHOPPING_CART)
            .build();
}
