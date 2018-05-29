package com.little.farmgo.Data;

/**
 * Created by sarah on 22/03/2018.
 */

public class Product {

    public static final String PRODUCTS = "products";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String IMAGE_URL = "image_url";
    public static final String PRICE = "price";

    private String title;
    private long price;
    private String subtitle;
    private String image_url;

    public  Product(){

    }

    public Product(String title, long price, String mSubtitle, String imageURL) {
        this.title = title;
        this.price = price;
        this.subtitle = mSubtitle;
        image_url = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
