package com.little.farmgo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.little.farmgo.Data.Product;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;
import com.little.farmgo.R;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String PRODUCT = "product";
    private static final int DEFAULT_NUM = 1;
    private TextView mNumber;
    private Product mProduct;
    private TextView mBuyNum;

    public static Intent newIntent(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(PRODUCT, product);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mProduct = getIntent().getParcelableExtra(PRODUCT);

        ImageView imageView = findViewById(R.id.imageView);
        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        TextView price = findViewById(R.id.price);
        mNumber = findViewById(R.id.number);
        TextView description = findViewById(R.id.discrition);
        TextView origin = findViewById(R.id.origin);
        mBuyNum = findViewById(R.id.buying_num);

        title.setText(mProduct.getTitle());
        subtitle.setText(mProduct.getSubtitle());
        price.setText("單價：" + mProduct.getPrice() + "元");
        mNumber.setText("剩餘數量：" + mProduct.getNumber());
        description.setText(mProduct.getDescription());
        origin.setText("產地：" + mProduct.getOrigin());

        Glide.with(getApplicationContext())
                .load(mProduct.getImage_url())
                .into(imageView);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addToShoppingList(View view) {

        new ShoppingListRepository(this).add(mProduct,DEFAULT_NUM);
        Toast.makeText(this, "已加入購物車", Toast.LENGTH_LONG).show();

    }


}
