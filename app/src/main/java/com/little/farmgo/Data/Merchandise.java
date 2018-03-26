package com.little.farmgo.Data;

/**
 * Created by sarah on 22/03/2018.
 */

public class Merchandise {
    private String mTitle;
    private int mPrice;
    private String Describe;
    private String mImageURL;

    public Merchandise(String title, int price, String describe, String imageURL) {
        mTitle = title;
        mPrice = price;
        Describe = describe;
        mImageURL = imageURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }
}
