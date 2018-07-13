package com.little.farmgo.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by sarah on 22/03/2018.
 */

public class Product implements Parcelable {

    private String title;
    private int price;
    private int number;
    private String subtitle;
    private String imageUrl;
    private String description;
    private String origin;
    private String expirationDate;

    public Product() {

    }

    public Product(String title, int price, String mSubtitle
            , String imageURL, String description, String origin) {
        this.title = title;
        this.price = price;
        this.subtitle = mSubtitle;
        imageUrl = imageURL;
        this.description = description;
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl.trim();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.price);
        dest.writeInt(this.number);
        dest.writeString(this.subtitle);
        dest.writeString(this.imageUrl);
        dest.writeString(this.description);
        dest.writeString(this.origin);
    }

    protected Product(Parcel in) {
        this.title = in.readString();
        this.price = in.readInt();
        this.number = in.readInt();
        this.subtitle = in.readString();
        this.imageUrl = in.readString();
        this.description = in.readString();
        this.origin = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        Product pro = (Product) obj;
        return getImageUrl().equals(pro.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getImageUrl());
    }
}
