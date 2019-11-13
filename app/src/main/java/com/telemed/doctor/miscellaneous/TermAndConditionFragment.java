package com.telemed.doctor.miscellaneous;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermAndConditionFragment extends Fragment {

    public static TermAndConditionFragment newInstance() {
        return new TermAndConditionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_term_and_condition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        v.findViewById(R.id.ibtn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null)
                    ((HomeActivity)getActivity()).popTopMostFragment();
            }
        });

        TextView textView = (TextView) v.findViewById(R.id.tv_text);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }



}
