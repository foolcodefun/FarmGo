package com.little.farmgo.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.little.farmgo.Fragment.MerchandiseListFragment;
import com.little.farmgo.R;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.bottom_view);
        mNavigationView.setOnNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
        if(fragment==null){
            fragment = new MerchandiseListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.container,fragment)
                    .commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.homepage:
                break;
            case R.id.member_data:
                break;
            case R.id.shopping_cart:
                break;
            case R.id.purchase_records:
                break;
            default:
                break;
        }
        return true;
    }
}
