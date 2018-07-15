package com.little.farmgo.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.little.farmgo.Data.Product;
import com.little.farmgo.Data.ShoppingCart.ShoppingCartList;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;
import com.little.farmgo.R;

import static com.little.farmgo.Activity.MainActivity.FRAGMEMT;

public class ProductDetailActivity extends AppCompatActivity {

    //TODO改成fragmant比較好

    private static final String PRODUCT = "product";
    private static final int DEFAULT_NUM = 1;
    private static final String TAG = ProductDetailActivity.class.getSimpleName();
    private TextView mNumber;
    private Product mProduct;
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mPrice;
    private TextView mDescription;
    private TextView mOrigin;
    private TextView mExpirationDate;

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

        findViews();

        initViews();


    }

    private void initViews() {

        mTitle.setText(mProduct.getTitle());
        mSubtitle.setText(mProduct.getSubtitle());
        mPrice.setText("單價：" + mProduct.getPrice() + "元");
        mNumber.setText("剩餘數量：" + mProduct.getNumber());
        mDescription.setText(mProduct.getDescription());
        mOrigin.setText("產地：" + mProduct.getOrigin());
        mExpirationDate.setText("保存期限：" + mProduct.getExpirationDate());

        Glide.with(getApplicationContext())
                .load(mProduct.getImageUrl())
                .into(mImageView);

        getSupportActionBar().setTitle(R.string.product_information);
    }

    private void findViews() {
        mImageView = findViewById(R.id.imageView);
        mTitle = findViewById(R.id.title);
        mSubtitle = findViewById(R.id.subtitle);
        mPrice = findViewById(R.id.price);
        mNumber = findViewById(R.id.number);
        mDescription = findViewById(R.id.description);
        mOrigin = findViewById(R.id.origin);
        mExpirationDate = findViewById(R.id.expiration_date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(FRAGMEMT,R.id.shopping_cart);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addToShoppingList(View view) {

        new ShoppingListRepository(this
                , ShoppingCartList.getInstance().getOrders())
                .add(mProduct, DEFAULT_NUM);
        Toast.makeText(this, "已加入購物車", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
