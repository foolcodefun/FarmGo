package com.little.farmgo.Data.ShoppingCart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable;

import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.*;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FarmGo.db";

    private static final String CREATE_TABLE_SHOPPING_CART = "CREATE TABLE " +
            TABLE_SHOPPING_CART+" (" +
            ShoppingCartTable._ID+ " INTEGER PRIMARY KEY, " +
            ShoppingCartTable.COLUMN_IMAGE_URL +" TEXT NOT NULL, "+
            ShoppingCartTable.COLUMN_PRODUCT_TITLE +" TEXT NOT NULL, " +
            ShoppingCartTable.COLUMN_PRODUCT_PRICE +" INTEGER NOT NULL, " +
            ShoppingCartTable.COLUMN_NUMBER +" INTEGER NOT NULL)";

    public ShoppingCartHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHOPPING_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
