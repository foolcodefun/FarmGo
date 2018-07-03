package com.little.farmgo.Data.ShoppingCart;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by sarah on 07/06/2018.
 */

public class ShoppingCartService extends IntentService {
    public static final String OPERATION = "operation";
    public static final int NONE = 1;
    public static final int INSERT = 222;
    public static final int UPDATE = 333;
    public static final int DELETE = 444;
    private static final String CONTENT_VALUES = "contentValues";
    private static final String WHERE = "where";
    private static final String SELECTION_ARGS = "selectionArgs";


    public ShoppingCartService() {
        super(ShoppingCartList.class.getSimpleName());
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ShoppingCartService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int operation = intent.getIntExtra(OPERATION, NONE);
        ContentValues values;
        String where = null;
        String[] selectionArgs = null;
        switch (operation) {
            case INSERT:
                values = intent.getParcelableExtra(CONTENT_VALUES);
                getContentResolver().insert(DatabaseContract.CONTENT_SHOPPING_CART_URI, values);
                break;
            case UPDATE:
                values = intent.getParcelableExtra(CONTENT_VALUES);
                where = intent.getStringExtra(WHERE);
                selectionArgs = intent.getStringArrayExtra(SELECTION_ARGS);
                int update = getContentResolver().update(DatabaseContract.CONTENT_SHOPPING_CART_URI, values, where, selectionArgs);
                break;
            case DELETE:
                where = intent.getStringExtra(WHERE);
                selectionArgs = intent.getStringArrayExtra(SELECTION_ARGS);
                getContentResolver().delete(DatabaseContract.CONTENT_SHOPPING_CART_URI, where, selectionArgs);
                break;
            default:
                break;
        }

    }

    public static void insert(Context context, ContentValues values) {
        Intent intent = new Intent(context, ShoppingCartService.class);
        intent.putExtra(OPERATION, INSERT);
        intent.putExtra(CONTENT_VALUES, values);
        context.startService(intent);
    }

    public static void update(Context context, ContentValues values, String where, String[] selectionArgs) {
        Intent intent = new Intent(context, ShoppingCartService.class);
        intent.putExtra(OPERATION, UPDATE);
        intent.putExtra(CONTENT_VALUES,values);
        intent.putExtra(WHERE,where);
        intent.putExtra(SELECTION_ARGS,selectionArgs);
        context.startService(intent);
    }

    public static void delete(Context context, String where, String[] selectionArgs) {
        Intent intent = new Intent(context, ShoppingCartService.class);
        intent.putExtra(OPERATION, DELETE);
        intent.putExtra(WHERE,where);
        intent.putExtra(SELECTION_ARGS,selectionArgs);
        context.startService(intent);
    }
}
