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
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.little.farmgo.Fragment.ChatFragment;
import com.little.farmgo.Fragment.OrderListFragment;
import com.little.farmgo.Fragment.ProductListFragment;
import com.little.farmgo.Fragment.ShoppingCartFragment;
import com.little.farmgo.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        FirebaseAuth.AuthStateListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 23;
    private BottomNavigationView navigationView;
    private FirebaseAuth auth;
    private Menu mMenu;
    public static final String FRAGMEMT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.bottom_view);
        navigationView.setOnNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {

            //TODO refactor startActivityforResult from fragment
            int fragmentId = getIntent().getIntExtra(FRAGMEMT, 0);
            if (fragmentId != 0) {
                navigationView.setSelectedItemId(fragmentId);
                fragment = new ShoppingCartFragment();
            } else {
                fragment = new ProductListFragment();
            }
            fragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        invalidateOptionsMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switchFragment(item.getItemId());
        return true;
    }

    private void switchFragment(int id) {
        switch (id) {
            case R.id.homepage:
                replaceFragment(new ProductListFragment());
                break;
            case R.id.chat:
                replaceFragment(new ChatFragment());
                break;
            case R.id.shopping_cart:
                replaceFragment(new ShoppingCartFragment());
                break;
            case R.id.purchase_records:
                replaceFragment(new OrderListFragment());
                break;
            //TODO
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");

        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, mMenu);

        setItemsTitles(mMenu);

        return true;
    }

    private void setItemsTitles(Menu menu) {
        MenuItem sign = menu.findItem(R.id.sign_in_or_out);
        MenuItem delete = menu.findItem(R.id.delete_account);
        MenuItem member = menu.findItem(R.id.member_data);
        if (auth.getCurrentUser() != null) {
            sign.setTitle(R.string.signOut);
            delete.setVisible(true);
            delete.setTitle(R.string.deleteAccount);
            member.setTitle(R.string.edit_recipient_data);
        } else {
            sign.setTitle(R.string.signIn);
            delete.setVisible(false);
            member.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()) {
            case R.id.sign_in_or_out:
                signInOrOut();
                break;
            case R.id.delete_account:
                deleteAccount();
                break;
            case R.id.member_data:
                editMemberData();
        }
        return true;
    }

    private void editMemberData() {
        //TODO: start an activity to edit member's data
       /* Intent intent = new Intent(this, RecipientListActivity.class);
        startActivity(intent);*/
    }

    private void deleteAccount() {
        if (auth.getCurrentUser() != null)
            new AlertDialog.Builder(this)
                    .setTitle(R.string.deleteAccountOrNot)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            auth.getCurrentUser().delete();
                            auth.signOut();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
    }

    private void signInOrOut() {
        if (auth.getCurrentUser() != null) {
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

    private void showSignAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.signOutOrNot)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auth.signOut();
                        Toast.makeText(getApplicationContext()
                                , R.string.signed_out, Toast.LENGTH_LONG).show();
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
        signInRequest(requestCode);

        //TODO shoppingCartRequest


    }

    private void signInRequest(int requestCode) {
        if (requestCode == RC_SIGN_IN) {
            FirebaseDatabase.getInstance().getReference()
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String phone = (String) dataSnapshot
                                    .child("users")
                                    .child(auth.getUid())
                                    .child("phone")
                                    .getValue();
                            if (phone == null ) {
                                phone = auth.getCurrentUser().getPhoneNumber();
                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .child(auth.getUid())
                                        .child("phone")
                                        .setValue(phone);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }
}
