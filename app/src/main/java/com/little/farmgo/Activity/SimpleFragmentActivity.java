package com.little.farmgo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.little.farmgo.R;

/**
 * Created by sarah on 2018/7/16.
 */

public abstract class SimpleFragmentActivity extends AppCompatActivity {

    abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.framelayout);
        if(fragment==null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.framelayout,fragment)
                    .commit();
        }
    }
}
