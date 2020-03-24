package com.telemed.doctor.miscellaneous;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermAndConditionFragment extends Fragment {

    private ImageButton ibtnClose,ibtnBack;
    private HomeFragmentSelectedListener mFragmentListener;

    public static TermAndConditionFragment newInstance() {
        return new TermAndConditionFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_term_and_condition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);


        ibtnClose=v.findViewById(R.id.ibtn_close);

        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment",0);
        });

        ibtnBack=v.findViewById(R.id.ibtn_back);
        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        TextView textView = (TextView) v.findViewById(R.id.tv_text);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }



}
