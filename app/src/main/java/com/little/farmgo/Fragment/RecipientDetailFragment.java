package com.little.farmgo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by sarah on 2018/7/17.
 *
 */

public class RecipientDetailFragment extends Fragment {

    public static final String RECIPIENT_ID = "recipientId";

    public static RecipientDetailFragment newInstance(UUID recipientId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPIENT_ID,recipientId);
        RecipientDetailFragment fragment = new RecipientDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
