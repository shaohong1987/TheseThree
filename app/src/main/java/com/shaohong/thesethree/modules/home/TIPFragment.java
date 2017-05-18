package com.shaohong.thesethree.modules.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaohong.thesethree.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TIPFragment extends Fragment {


    public TIPFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_tip, container, false);
    }

}
