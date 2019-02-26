package com.little.farmgo.Data.Order;

import com.little.farmgo.Data.Product;

import java.util.List;

/**
 * Created by sarah on 2019/2/26.
 */

public class Order {
    public static final String ORDER_STATE_RECEIVE = "廠商已收到訂單";
    public static final String ORDER_STATE_DELIVERING = "正在運送貨物";
    public static final String ORDER_STATE_DELIVERED = "貨物已送達";

    private String userUid;
    private String address;
    private List<Product> products;
    private String orderState;
    private String userPhone;
    private String date;

    public Order() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Order(String userUid, String address, List<Product> products, String orderState, String userPhone, String date) {
        this.userUid = userUid;
        this.address = address;
        this.products = products;
        this.orderState = orderState;
        this.userPhone = userPhone;
        this.date = date;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}