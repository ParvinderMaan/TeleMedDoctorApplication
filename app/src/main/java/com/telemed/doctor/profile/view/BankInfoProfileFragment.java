package com.telemed.doctor.profile.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Selection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.profile.model.BankInfoRequest;
import com.telemed.doctor.profile.model.BankInfoResponse;
import com.telemed.doctor.profile.model.ProfileUpdateResponse;
import com.telemed.doctor.profile.viewmodel.BankInfoProfileViewModel;
import com.telemed.doctor.profile.viewmodel.ProfessionalInfoProfileViewModel;


public class BankInfoProfileFragment extends Fragment {
    private AppCompatEditText edtRoutingNumber, edtAccountNumber, edtCity, edtPostCode,edtAddr;
    private String mRoutingNumber,mAccountNumber,mCity,mPostCode,mAddress;
    private AppCompatButton btnEditSave;
    private BankInfoProfileViewModel mViewModel;
    private ProgressBar progressBar;
    private String selectorState[]={"Edit","Save"};

    public BankInfoProfileFragment() {
        // Required empty public constructor
    }

    public static BankInfoProfileFragment newInstance() {
        return new BankInfoProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bank_info_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BankInfoProfileViewModel.class);
        initView(v);
        initListener();
        initObserver();

        mViewModel.fetchBankInfo();
    }

    private void initListener() {

        btnEditSave.setOnClickListener(v1 -> {
            String tag= (String) btnEditSave.getText();
            switch (tag){
                case "Edit":
                    mViewModel.setEditableView(true);
                    btnEditSave.setText(selectorState[1]);
                    break;
                case "Save":
//                  mViewModel.setEnableView(false);
                    btnEditSave.setText(selectorState[0]);

                     if(isFormValid()){
                         BankInfoRequest in=new BankInfoRequest.Builder()
                                 .setAccountNumber(Long.parseLong(mAccountNumber))
                                 .setCardNumber(Long.valueOf(0)) // no need ..
                                 .setAddress(mAddress)
                                 .setCity(mCity)
                                 .setRoutingNumber(mRoutingNumber)
                                 .setPostCode(mPostCode).build();
                         mViewModel.updateBankInfo(in);
                     }

                    break;
            }
        });
    }

    private void initObserver() {
        mViewModel.getFetchResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        BankInfoResponse.BankDetail infoObj = response.getData().getData();
                        setView(infoObj);
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        // tvAlertView.showTopAlert(response.getErrorMsg());
                        // handle later...
                    }
                    break;
            }

        });


        mViewModel.getUpdateResultant().observe(getViewLifecycleOwner(), response -> {
            mViewModel.setEditableView(false); //newly added ..
            mViewModel.setEnableView(true);

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        ProfileUpdateResponse infoObj = response.getData();
                        Toast.makeText(requireActivity(), infoObj.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        Toast.makeText(requireActivity(), response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        // tvAlertView.showTopAlert(response.getErrorMsg());
                        // handle later...
                    }
                    break;
            }

        });

        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getEditableView()
                .observe(getViewLifecycleOwner(), isEnable -> {
                    enableView(isEnable);
                });


        mViewModel.getEnableView().observe(getViewLifecycleOwner(), isEnable -> {
            btnEditSave.setEnabled(isEnable);
        });

    }



    private void initView(View v) {
        btnEditSave= v.findViewById(R.id.btn_edit_save);
        btnEditSave.setText(selectorState[0]); // by default

        edtRoutingNumber= v.findViewById(R.id.edt_routing_number);
        edtAccountNumber= v.findViewById(R.id.edt_account_number);
        edtAddr= v.findViewById(R.id.edt_addr);

        edtCity = v.findViewById(R.id.edt_city);
        edtPostCode = v.findViewById(R.id.edt_post_code);

        progressBar=v.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
    }


    private void setView(BankInfoResponse.BankDetail info) {
        edtRoutingNumber.setText(info.getRoutingNumber()!=null?info.getRoutingNumber():"");
        edtCity.setText(info.getCity()!=null?info.getCity():"");
        edtPostCode.setText(info.getPostCode()!=null?info.getPostCode():"");
        edtAccountNumber.setText(info.getAccountNumber()!=null?info.getAccountNumber():"");
        edtAddr.setText(info.getAddress()!=null?info.getAddress():"");
    }

    private void enableView(boolean isEnable) {
        edtRoutingNumber.setEnabled(isEnable);
        edtCity.setEnabled(isEnable);
        edtPostCode.setEnabled(isEnable);
        edtAccountNumber.setEnabled(isEnable);
        edtAddr.setEnabled(isEnable);


        if(isEnable){
            edtRoutingNumber.setSelection(edtRoutingNumber.getText()!=null?edtRoutingNumber.getText().length():0);
            edtRoutingNumber.requestFocus();
        }
    }


    private boolean isFormValid() {
        mRoutingNumber=edtRoutingNumber.getText().toString();
        mAccountNumber=edtAccountNumber.getText().toString();
        mCity=edtCity.getText().toString();
        mPostCode=edtPostCode.getText().toString();
        mAddress=edtAddr.getText().toString();



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

        if (TextUtils.isEmpty(mAddress)) {
            edtAddr.setError("Enter address ");
            return false;
        }


        if (TextUtils.isEmpty(mCity)) {
            edtCity.setError("Enter City");
            return false;
        }

//        if (mCity.contains(" ")) {
//            edtCity.setError("No Spaces Allowed");
//            return false;
//        }


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
