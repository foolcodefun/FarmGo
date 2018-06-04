package com.little.farmgo.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.little.farmgo.Data.Product;
import com.little.farmgo.R;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String PRODUCT = "product";

    public static Intent newIntent(Context context, Product product){
        Intent intent =new Intent(context, ProductDetailActivity.class);
        intent.putExtra(PRODUCT,product);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Product product = getIntent().getParcelableExtra(PRODUCT);

        ImageView imageView = findViewById(R.id.imageView);
        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        TextView price = findViewById(R.id.price);
        TextView number =  findViewById(R.id.number);
        TextView description =  findViewById(R.id.discrition);
        TextView origin = findViewById(R.id.origin);

        title.setText(product.getTitle());
        subtitle.setText(product.getSubtitle());
        price.setText("單價："+product.getPrice()+"元");
        number.setText("剩餘數量"+product.getNumber());
        description.setText(product.getDescription());
        origin.setText(product.getOrigin());

        Glide.with(getApplicationContext())
                .load(product.getImage_url())
                .into(imageView);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
