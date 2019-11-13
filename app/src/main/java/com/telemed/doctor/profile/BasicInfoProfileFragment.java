package com.telemed.doctor.profile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicInfoProfileFragment extends Fragment {


    public BasicInfoProfileFragment() {
        // Required empty public constructor
    }

    public static BasicInfoProfileFragment newInstance() {
        return new BasicInfoProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_info_profile, container, false);
    }

}
