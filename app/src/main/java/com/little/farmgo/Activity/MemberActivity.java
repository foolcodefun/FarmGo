package com.little.farmgo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.little.farmgo.R;

public class MemberActivity extends AppCompatActivity implements TextWatcher{

    private static final String TAG = MemberActivity.class.getSimpleName();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser mCurrentUser;
    private EditText mFamilyName;
    private EditText mFirstName;
    private Spinner mCounty;
    private Spinner mDistrict;
    private EditText mAddress;
    private EditText mPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        findViews();

    }

    private void findViews() {
        mFamilyName = findViewById(R.id.family_name);
        mFirstName = findViewById(R.id.first_name);
        mCounty = findViewById(R.id.county);
        mDistrict = findViewById(R.id.district);
        mAddress = findViewById(R.id.address);
        mPhone = findViewById(R.id.phone);


    }

    public void sendMemberInfo(View view) {

        //todo check if is filled
        /*if (isFilled()) {

        }*/

        //todo save recipient info to SQLiteDatabase 

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    //todo if not filled up, show alert or toast to remind that it wasn't filled up
    /*private boolean isFilled() {
        if () {

        }
        return true;
    }*/
}
