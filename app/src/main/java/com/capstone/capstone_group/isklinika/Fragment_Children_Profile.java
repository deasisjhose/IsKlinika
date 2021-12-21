package com.capstone.capstone_group.isklinika;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Fragment_Children_Profile extends Fragment {

    public Fragment_Children_Profile() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_children_profile, container, false);

        Activity_Landing activity_landing = (Activity_Landing) getActivity() ;


        return view;
    }

}
