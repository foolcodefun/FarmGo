package com.little.farmgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.little.farmgo.Data.ShoppingCart.ShoppingCartList;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int SHOPPING_LIST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(SHOPPING_LIST,null
                ,new ShoppingListRepository(getApplicationContext(), ShoppingCartList.getInstance().getOrders()));
        startActivity(new Intent(this, MainActivity.class));
    }
}
