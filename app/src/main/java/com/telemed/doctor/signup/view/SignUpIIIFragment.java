package com.telemed.doctor.signup.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.signup.model.SignUpIIIRequest;
import com.telemed.doctor.signup.viewmodel.SignUpIIIViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;
import java.util.Map;


public class SignUpIIIFragment extends BaseFragment {
    private final String TAG=SignUpIIIFragment.class.getSimpleName();
    private AppCompatEditText edtMedicalDegree, edtMdWhere, edtOtherDegree, edtMdOtrWhere, edtDea, edtNpiNo;
    private TextView tvCancel;
    private Button btnContinue;
    private RouterFragmentSelectedListener mFragmentListener;
    private AppCompatCheckBox cboxReadAndAccept;
    private String mMedicalDegree, mMdWhere, mOtherDegree, mMdOtrWhere, mDea, mNpiNo;
    private boolean isReadAndAccept;
    private String mAccessToken;
    private SignUpIIIViewModel mViewModel;
    private ProgressBar progressBar;
    private LinearLayout llRoot;
    private CustomAlertTextView tvAlertView;


    public SignUpIIIFragment() {
        // Required empty public constructor
    }

    public static SignUpIIIFragment newInstance(Object payload) {
        SignUpIIIFragment fragment=new SignUpIIIFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("KEY_ACCESS_TOKEN", (UserInfoWrapper) payload);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //collect our intent
        if(getArguments()!=null){
            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_ACCESS_TOKEN");
            if (objInfo != null) mAccessToken =objInfo.getAccessToken();

            Log.e(TAG,mAccessToken);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpIIIViewModel.class);

        initView(v);
        initListener(v);

        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getViewEnabled()
                .observe(getViewLifecycleOwner(), this::resetEnableView);


        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){
                            //data = response.getData().getData(); // adding Additional Info
                           //  data.setEmail(mUserEmail);
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            UserInfoWrapper in=new UserInfoWrapper();
                            in.setAccessToken(mAccessToken);
//                            mFragmentListener.showFragment("SignUpIVFragment", in);
                            Message msg = new Message();
                            msg.obj = in;
                            msg.what = 1;
                            mHandler.sendMessageDelayed(msg,1500);
                        }

                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;

            }

        });

        edtNpiNo.setOnEditorActionListener((v1, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (edtNpiNo.isFocused()) edtNpiNo.clearFocus();
                mFragmentListener.hideSoftKeyboard();
                return true;
            }
            return false;
        });

    }

    private void resetEnableView(Boolean isView) {
        btnContinue.setEnabled(isView);
        edtMedicalDegree.setEnabled(isView);
        edtMdWhere.setEnabled(isView);
        edtOtherDegree.setEnabled(isView);
        edtMdOtrWhere.setEnabled(isView);
        edtDea.setEnabled(isView);
        edtNpiNo.setEnabled(isView);
        cboxReadAndAccept.setEnabled(isView);

    }

    private void initListener(View v) {
        tvCancel.setOnClickListener(v1 -> {

            if(mFragmentListener!=null)
                mFragmentListener.abortSignUpDialog();


        });

        btnContinue.setOnClickListener(v12 -> {

//            if(!isNetAvail()){
//                tvAlertView.showTopAlert("No Internet");
//                return;
//            }

            if(isFormValid()){

                SignUpIIIRequest in=new SignUpIIIRequest.Builder()
                        .setMedicalDegree(mMedicalDegree)
                        .setDegreeFrom(mMdWhere)
                        .setOtherDegree(mOtherDegree)
                        .setOtherDegreeFrom(mMdOtrWhere)
                        .setDeaNumber(mDea)
                        .setNpiNumber(mNpiNo)
                        .build();

                Map<String, String> map = new HashMap<>();
                map.put("content-type", "application/json");
                map.put("Authorization","Bearer "+mAccessToken);

                mViewModel.attemptSignUp(in,map);
            }


        });

    }

    private void initView(View v) {
        llRoot=v.findViewById(R.id.ll_root);
        tvCancel=v.findViewById(R.id.tv_cancel);
        btnContinue =v.findViewById(R.id.btn_continue);

        edtMedicalDegree=v.findViewById(R.id.edt_medical_degree);
        edtMdWhere=v.findViewById(R.id.edt_md_where);
        edtOtherDegree=v.findViewById(R.id.edt_other_degree);
        edtMdOtrWhere=v.findViewById(R.id.edt_md_otr_where);
        edtDea=v.findViewById(R.id.edt_dea);
        edtNpiNo=v.findViewById(R.id.edt_npi_no);
        cboxReadAndAccept=v.findViewById(R.id.cbox_read_and_accept);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);


//----------------------------------------------------------------

    }


    private boolean isFormValid() {

        mMedicalDegree=edtMedicalDegree.getText().toString();
        mMdWhere=edtMdWhere.getText().toString();;
        mOtherDegree=edtOtherDegree.getText().toString().trim();
        mMdOtrWhere=edtMdOtrWhere.getText().toString().trim();
        mDea=edtDea.getText().toString();;
        mNpiNo=edtNpiNo.getText().toString();;
        isReadAndAccept=cboxReadAndAccept.isChecked();


        if (TextUtils.isEmpty(mMedicalDegree)) {
            edtMedicalDegree.setError("Enter medical degree");
            return false;
        }

//        if (mMedicalDegree.contains(" ")) {
//            edtMedicalDegree.setError("No Spaces Allowed");
//            return false;
//        }

        if (TextUtils.isEmpty(mMdWhere)) {
            edtMdWhere.setError("Enter medical degree place");
            return false;
        }

//        if (mMdWhere.contains(" ")) {
//            edtMdWhere.setError("No Spaces Allowed");
//            return false;
//        }


        if (TextUtils.isEmpty(mDea)) {
            edtDea.setError("Enter Dea");
            return false;
        }

//        if (mDea.contains(" ")) {
//            edtDea.setError("No Spaces Allowed");
//            return false;
//        }


        if (TextUtils.isEmpty(mNpiNo)) {
            edtNpiNo.setError("Enter Npi");
            return false;
        }

//        if (mNpiNo.contains(" ")) {
//            edtNpiNo.setError("No Spaces Allowed");
//            return false;
//        }

        if (!isReadAndAccept) {
//            makeToast("Please read the terms and conditions");
            tvAlertView.showTopAlert("Please read the terms and conditions");
            return false;
        }

        return true;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                UserInfoWrapper in = (UserInfoWrapper) msg.obj;
                if(mFragmentListener!=null){
                    mFragmentListener.showFragment("SignUpIVFragment", in);
                }
            }



        }
    };

    @Override
    public void onDestroy() {
        mHandler.removeMessages(1);
        super.onDestroy();
    }
}
