package com.shaohong.thesethree.modules.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaohong.thesethree.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpAndFeedBackFragment extends Fragment {


    public HelpAndFeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_help_and_feed_back, container, false);
    }

}
