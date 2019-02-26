package com.little.farmgo.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.little.farmgo.Data.Order.Order;
import com.little.farmgo.Data.Product;
import com.little.farmgo.Data.ShoppingCart.ShoppingCartList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.little.farmgo.Data.Order.Order.ORDER_STATE_RECEIVE;

/**
 * Created by sarah on 2019/2/26.
 */

public class OrderUtility {
    public static final String ORDERS = "orders";
    private static final String TAG = OrderUtility.class.getSimpleName();
    private OnSendOrderCallback onSendOrderCallback;

    public interface OnSendOrderCallback {
        void onSuccess();

        void onFailure();

    }

    private DatabaseReference mDatabase;

    public OrderUtility(OnSendOrderCallback callback) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        onSendOrderCallback = callback;
    }

    // todo 1. minus the number of product in database
    public void sendOrder(String userUid) {
        String userPhone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        UUID uuid = UUID.randomUUID();
        List<Product> orders = setShoppingCartList();
        String stDate = getCurrentDate();
        Order order = new Order(userUid
                , "test address"
                , orders
                , ORDER_STATE_RECEIVE
                , userPhone
                , stDate);

        mDatabase.child(ORDERS).child(uuid.toString()).setValue(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: ");
                        if (onSendOrderCallback != null) {
                            onSendOrderCallback.onSuccess();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed reason
                        Log.d(TAG, "onFailure: ");
                        if (onSendOrderCallback != null) {
                            onSendOrderCallback.onFailure();
                        }
                    }
                });
    }

    private String getCurrentDate() {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = Calendar.getInstance().getTime();
        return sdFormat.format(date);
    }

    @NonNull
    private List<Product> setShoppingCartList() {
        HashMap<Product, Integer> shoppingCartOrders = ShoppingCartList.getInstance().getOrders();
        Iterator<Product> iterator = shoppingCartOrders.keySet().iterator();
        List<Product> shoppingCartList = new ArrayList<>();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            int count = shoppingCartOrders.get(product);
            product.setNumber(count);
            shoppingCartList.add(product);
        }
        return shoppingCartList;
    }

}
