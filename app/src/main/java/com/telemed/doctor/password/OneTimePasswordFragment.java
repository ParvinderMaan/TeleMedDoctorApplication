package com.telemed.doctor.password;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;


public class OneTimePasswordFragment extends BaseFragment {
    private AppCompatEditText edtOtpOne, edtOtpTwo, edtOtpThree, edtOtpFour;

    private RouterFragmentSelectedListener mFragmentListener;
    private AppCompatButton btnContinue;

    public OneTimePasswordFragment() {
        // Required empty public constructor
    }

    public static OneTimePasswordFragment newInstance(String payload) {
        OneTimePasswordFragment fragment=new OneTimePasswordFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY_",payload);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeTwo);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_one_time_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        edtOtpOne = v.findViewById(R.id.edt_otp_one);
        edtOtpTwo = v.findViewById(R.id.edt_otp_two);
        edtOtpThree = v.findViewById(R.id.edt_otp_three);
        edtOtpFour = v.findViewById(R.id.edt_otp_four);

        btnContinue=v.findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mFragmentListener!=null)
                  mFragmentListener.showFragment("SignUpIIFragment",null);

            }
        });


        edtOtpFour.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_GO){
                            attemptVerfication();
                        }
                        return false;
                    }
                });

        edtOtpOne.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 1) edtOtpTwo.requestFocus();
            }
        });

        edtOtpTwo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 1) edtOtpThree.requestFocus();
                if(s.length()==0) edtOtpOne.requestFocus();
            }
        });

        edtOtpThree.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 1) edtOtpFour.requestFocus();
                if(s.length()==0) edtOtpTwo.requestFocus();
            }
        });
        edtOtpFour.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0) edtOtpThree.requestFocus();
            }
        });
    }



    private void attemptVerfication() {

        String a=edtOtpOne.getText().toString();
        String b=edtOtpTwo.getText().toString();
        String c=edtOtpThree.getText().toString();
        String d=edtOtpFour.getText().toString();

        String result=a+b+c+d;

        if(TextUtils.isEmpty(a) && TextUtils.isEmpty(b) && TextUtils.isEmpty(c) && TextUtils.isEmpty(d) ){
            Toast.makeText(getActivity(), "Enter the OTP", Toast.LENGTH_SHORT).show();
            return;
        }



//        if(!(DUMMY_OTP==Integer.parseInt(result))){
//
//            Toast.makeText(getActivity(), "OTP mismatch", Toast.LENGTH_SHORT).show();
//            return;
//
////            edtOtpOne.setText("");
////            edtOtpTwo.setText("");
////            edtOtpThree.setText("");
////            edtOtpFour.setText("");
//        }
//
//        Intent in=new Intent(getActivity(), HomeActivity.class);
//        startActivity(in);
//        if(getActivity()!=null){
//            getActivity().finish();
//        }
//

    }

}
