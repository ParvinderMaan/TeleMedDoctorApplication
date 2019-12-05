package com.telemed.doctor.password.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.base.BaseTextWatcher;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.viewmodel.OneTimePasswordViewModel;


public class OneTimePasswordFragment extends BaseFragment {
    private AppCompatEditText edtOtpOne, edtOtpTwo, edtOtpThree, edtOtpFour;
    private AppCompatTextView tvCancel;

    private RouterFragmentSelectedListener mFragmentListener;
    private AppCompatButton btnContinue;
    private OneTimePasswordViewModel mViewModel;
    private ProgressBar progressBar;

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
        mViewModel = ViewModelProviders.of(this).get(OneTimePasswordViewModel.class);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeTwo);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_one_time_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();


    }

    private void initView(View v) {
        edtOtpOne = v.findViewById(R.id.edt_otp_one);
        edtOtpTwo = v.findViewById(R.id.edt_otp_two);
        edtOtpThree = v.findViewById(R.id.edt_otp_three);
        edtOtpFour = v.findViewById(R.id.edt_otp_four);
        btnContinue=v.findViewById(R.id.btn_continue);
        tvCancel=v.findViewById(R.id.tv_cancel);

        progressBar=v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorBlue), android.graphics.PorterDuff.Mode.SRC_IN);


    }

    private void initListener() {
        btnContinue.setOnClickListener(mClickListener);
        tvCancel.setOnClickListener(mClickListener);

        edtOtpOne.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count == 1) edtOtpTwo.requestFocus();
            }
        });

        edtOtpTwo.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count == 1) edtOtpThree.requestFocus();
                if(count==0) edtOtpOne.requestFocus();
            }
        });

        edtOtpThree.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count == 1) edtOtpFour.requestFocus();
                if(count==0) edtOtpTwo.requestFocus();
            }
        });

        edtOtpFour.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count==0) edtOtpThree.requestFocus();
            }
        });

        edtOtpFour.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO){
                attemptVerfication();
            }
            return false;
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

    private View.OnClickListener mClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           switch (v.getId()){

               case R.id.btn_continue:
                   if(mFragmentListener!=null)
                       mFragmentListener.showFragment("SignUpIIFragment",null);
                   break;

               case R.id.tv_cancel:
                   if(mFragmentListener!=null)
                       mFragmentListener.popTopMostFragment();
                   break;
           }


        }
    };


    @Override
    public void onDestroyView() {
        releaseResource();
        super.onDestroyView();

    }

    private void releaseResource() {
        btnContinue.setOnClickListener(null);
        tvCancel.setOnClickListener(null);
        edtOtpOne.addTextChangedListener(null);
        edtOtpTwo.addTextChangedListener(null);
        edtOtpThree.addTextChangedListener(null);
        edtOtpFour.addTextChangedListener(null);
        edtOtpFour.setOnEditorActionListener(null);
        mClickListener=null;

    }
}
