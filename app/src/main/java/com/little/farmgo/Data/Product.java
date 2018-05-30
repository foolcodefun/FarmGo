package com.little.farmgo.Data;

/**
 * Created by sarah on 22/03/2018.
 */

public class Product {

    private String title;
    private int price;
    private String subtitle;
    private String image_url;

    public  Product(){

    }

    public Product(String title, int price, String mSubtitle, String imageURL) {
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

    public int getPrice() {
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
