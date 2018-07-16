package com.little.farmgo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.little.farmgo.R;

public class MemberActivity extends AppCompatActivity {

    private static final String TAG = MemberActivity.class.getSimpleName();
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserReference =mReference.child("users");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        //TODO findViews

        mCurrentUser = mFirebaseAuth.getCurrentUser();
        //todo isSignedIn
        if(mCurrentUser!=null){
            //todo setUserFromFirebase
            Log.d(TAG, "onCreate: ");
        }else {
            //todo alert sign or out
        }

    }

    public void sendMemberInfo(View view) {

    }
}
