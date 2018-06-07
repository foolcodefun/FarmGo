package com.little.farmgo.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.little.farmgo.Data.ShoppingCartHelper;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        ShoppingCartHelper helper = new ShoppingCartHelper(getApplicationContext());
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        //TODO query data from SQLiteDatabase and put it in shoppingcartlist
    }
}
