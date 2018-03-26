package com.little.farmgo.Data;

/**
 * Created by sarah on 22/03/2018.
 */

public class Merchandise {

    public static final String PRODUCTS = "products";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String IMAGE_URL = "image_url";
    public static final String PRICE = "price";

    private String mTitle;
    private long mPrice;
    private String mSubtitle;
    private String mImageURL;

    public Merchandise(String title, long price, String mSubtitle, String imageURL) {
        mTitle = title;
        mPrice = price;
        this.mSubtitle = mSubtitle;
        mImageURL = imageURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }
}
