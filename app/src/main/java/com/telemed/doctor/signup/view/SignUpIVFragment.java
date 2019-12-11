package com.telemed.doctor.signup.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.signup.model.SignUpIVRequest;
import com.telemed.doctor.signup.model.SignUpIVResponse;
import com.telemed.doctor.signup.viewmodel.SignUpIVViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpIVFragment extends BaseFragment {
    private final String TAG=SignUpIVFragment.class.getSimpleName();
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private RouterFragmentSelectedListener mFragmentListener;
    private Button btnContinue;
    private TextView tvCancel;
    private AppCompatEditText edtRoutingNumber, edtAccountNumber, edtCity, edtPostCode;
    private String mRoutingNumber,mAccountNumber,mCity,mPostCode;
    private SignUpIVViewModel mViewModel;
    private LinearLayout llRoot;
    private String mAccessToken;

    public SignUpIVFragment() {
        // Required empty public constructor
    }

    public static SignUpIVFragment newInstance(Object payload) {
        SignUpIVFragment fragment=new SignUpIVFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY_ACCESS_TOKEN", ( String ) payload);
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
        // collect our intent
        if(getArguments()!=null){
            mAccessToken = getArguments().getString("KEY_ACCESS_TOKEN");
            Log.e(TAG,mAccessToken);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SignUpIVViewModel.class);
        return inflater.inflate(R.layout.fragment_sign_up_iv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener(v);

        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getViewClickable()
                .observe(this, isView -> llRoot.setClickable(isView));

        mViewModel.getResultant().observe(this, new Observer<ApiResponse<SignUpIVResponse>>() {
            @Override
            public void onChanged(ApiResponse<SignUpIVResponse> response) {
                switch (response.getStatus()) {
                    case SUCCESS:
                        if (response.getData() != null) {
                            if (mFragmentListener != null){
                                SignUpIVResponse.Data data = response.getData().getData(); // adding Additional Info
                                tvAlertView.showTopAlert(response.getData().getMessage());
                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                mFragmentListener.showFragment("SignUpVFragment", mAccessToken);

                            }

                        }

                        break;

                    case FAILURE:
                        if (response.getErrorMsg() != null) {
                            tvAlertView.showTopAlert(response.getErrorMsg());
                        }
                        break;

                }


            }
        });

    }

    private void initListener(View v) {

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!isNetAvail()){
                    tvAlertView.showTopAlert("No Internet");
                    return;
                }

                if(isFormValid()){

                    SignUpIVRequest in=new SignUpIVRequest.Builder()
                            .setRoutingNumber(mRoutingNumber)
                            .setAccountNumber(Integer.parseInt(mAccountNumber))
                            .setCity(mCity)
                            .setAddress("no_need_yet")  // replace it !!!
                            .setPostCode(mPostCode)
                            .build();

                    Map<String, String> map = new HashMap<>();
                    map.put("content-type", "application/json");
                    map.put("Authorization","Bearer "+mAccessToken);

                    mViewModel.attemptSignUp(in,map);

                }

//                if (mFragmentListener != null)
//                    mFragmentListener.showFragment("SignUpVFragment", null);

            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFragmentListener != null)
                    mFragmentListener.abortSignUpDialog();

            }
        });
    }

    private void initView(View v) {
        llRoot = v.findViewById(R.id.ll_root);
        btnContinue = v.findViewById(R.id.btn_continue);
        tvCancel = v.findViewById(R.id.tv_cancel);

        edtRoutingNumber= v.findViewById(R.id.edt_routing_number);
        edtAccountNumber= v.findViewById(R.id.edt_account_number);
        edtCity = v.findViewById(R.id.edt_city);
        edtPostCode = v.findViewById(R.id.edt_post_code);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);


        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private boolean isFormValid() {
        mRoutingNumber=edtRoutingNumber.getText().toString();
        mAccountNumber=edtAccountNumber.getText().toString();
        mCity=edtCity.getText().toString();
        mPostCode=edtPostCode.getText().toString();



        if (TextUtils.isEmpty(mRoutingNumber)) {
            edtRoutingNumber.setError("Enter Routing number");
            return false;
        }

        if (mRoutingNumber.contains(" ")) {
            edtRoutingNumber.setError("No Spaces Allowed");
            return false;
        }

        if (TextUtils.isEmpty(mAccountNumber)) {
            edtAccountNumber.setError("Enter account number");
            return false;
        }

        if (mAccountNumber.contains(" ")) {
            edtAccountNumber.setError("No Spaces Allowed");
            return false;
        }




        if (TextUtils.isEmpty(mCity)) {
            edtCity.setError("Enter City");
            return false;
        }

        if (mCity.contains(" ")) {
            edtCity.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mPostCode)) {
            edtPostCode.setError("Enter post code");
            return false;
        }

        if (mPostCode.contains(" ")) {
            edtPostCode.setError("No Spaces Allowed");
            return false;
        }



        return true;
    }
}
