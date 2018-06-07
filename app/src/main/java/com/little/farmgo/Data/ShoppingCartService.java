package com.little.farmgo.Data;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by sarah on 07/06/2018.
 */

public class ShoppingCartService extends IntentService {
    public ShoppingCartService(){
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

    }
}
