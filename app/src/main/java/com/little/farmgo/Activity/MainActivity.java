package com.little.farmgo.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.little.farmgo.Data.User;
import com.little.farmgo.Fragment.ProductListFragment;
import com.little.farmgo.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        FirebaseAuth.AuthStateListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 23;
    private BottomNavigationView mNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mNavigationView = findViewById(R.id.bottom_view);
        mNavigationView.setOnNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = new ProductListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        invalidateOptionsMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.menu_main, menu);

        setItemsTitles(menu);

        return true;
    }

    private void setItemsTitles(Menu menu) {
        MenuItem sign = menu.findItem(R.id.sign_in_or_out);
        MenuItem delete = menu.findItem(R.id.delete_account);
        if (mAuth.getCurrentUser() != null) {
            sign.setTitle(R.string.signOut);
            delete.setVisible(true);
            delete.setTitle(R.string.deleteAccount);
        } else {
            sign.setTitle(R.string.signIn);
            delete.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        signInOrOut(item);
        deleteAccount(item);
        return true;
    }

    private void deleteAccount(final MenuItem item) {
        if (item.getItemId() == R.id.delete_account) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.deleteAccountOrNot)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mAuth.getCurrentUser().delete();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }

    private void signInOrOut(MenuItem item) {
        if (item.getItemId() == R.id.sign_in_or_out) {
            if (mAuth.getCurrentUser() != null) {
                showSignAlertDialog();
            } else {
                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.AppTheme)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.PhoneBuilder().
                                        setDefaultCountryIso(getString(R.string.countryIsoTaiwan))
                                        .build())
                        ).build(), RC_SIGN_IN
                );
            }
        }
    }

    private void showSignAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.signOutOrNot)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == RC_SIGN_IN) {
            FirebaseDatabase.getInstance().getReference()
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String phone = (String)dataSnapshot
                                    .child("users")
                                    .child(mAuth.getUid())
                                    .child("phone")
                                    .getValue();
                            if(phone==null){
                                User user = new User();
                                user.setPhone(mAuth.getCurrentUser().getPhoneNumber());
                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .child(mAuth.getUid())
                                        .setValue(user);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

    }
}
