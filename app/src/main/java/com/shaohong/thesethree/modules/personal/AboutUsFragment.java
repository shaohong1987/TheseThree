package com.shaohong.thesethree.modules.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.utils.SystemHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_about_us, container, false);
        TextView version_TextView = (TextView) view.findViewById(R.id.version_name_personal_about_us);
        version_TextView.setText(SystemHelper.getAppVersionName(getContext()));
        return view;
    }

}
