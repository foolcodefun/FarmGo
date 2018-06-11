package com.little.farmgo.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sarah on 07/06/2018.
 */

public class ShoppingCartProvider extends ContentProvider {

    private ShoppingCartHelper mHelper;

    private UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int URI_WITH_ID = 10;

    private static final int URI_WITHOUT_ID = 11;

    {
        mUriMatcher.addURI(DatabaseContract.AUTHORITY
                , DatabaseContract.TABLE_SHOPPING_CART
                , URI_WITH_ID);

        mUriMatcher.addURI(DatabaseContract.AUTHORITY
                , DatabaseContract.TABLE_SHOPPING_CART
                , URI_WITHOUT_ID);
    }

    @Override
    public boolean onCreate() {
        mHelper = new ShoppingCartHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor = null;
        switch (mUriMatcher.match(uri)) {
            case URI_WITH_ID:
                //TODO
                long id = ContentUris.parseId(uri);
                selection = selection == null ? DatabaseContract.TABLE_SHOPPING_CART + " = " + id
                        : selection + " AND " + DatabaseContract.TABLE_SHOPPING_CART + " = " + id;
                break;
            case URI_WITHOUT_ID:
                break;
            default:
                break;
        }
        mHelper.getReadableDatabase()
                .query(DatabaseContract.TABLE_SHOPPING_CART
                        , projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long insert = mHelper.getWritableDatabase()
                .insert(DatabaseContract.TABLE_SHOPPING_CART
                        , null, values);
        return ContentUris.withAppendedId(uri, insert);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return mHelper.getWritableDatabase()
                .delete(DatabaseContract.TABLE_SHOPPING_CART
                        , selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //TODO 數量改變時
        return mHelper.getWritableDatabase()
                .delete(DatabaseContract.TABLE_SHOPPING_CART
                        , selection
                        , selectionArgs);
    }
}
