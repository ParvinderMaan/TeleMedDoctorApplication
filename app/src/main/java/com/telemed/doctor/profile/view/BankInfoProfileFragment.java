package com.telemed.doctor.profile.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.telemed.doctor.R;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.profile.model.BankInfoResponse;
import com.telemed.doctor.profile.viewmodel.BankInfoProfileViewModel;
import com.telemed.doctor.profile.viewmodel.ProfessionalInfoProfileViewModel;


public class BankInfoProfileFragment extends Fragment {
    private AppCompatEditText edtRoutingNumber, edtAccountNumber, edtCity, edtPostCode,edtAddr;
    private String mRoutingNumber,mAccountNumber,mCity,mPostCode;
    private BankInfoProfileViewModel mViewModel;
    private ProgressBar progressBar;
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
        initObserver();

        mViewModel.fetchBankInfo();
    }

    private void initObserver() {
        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {
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
                    }
                    break;
            }

        });

        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

    }

    private void initView(View v) {
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
        String accNo = Integer.toString(info.getAccountNumber());
        edtAccountNumber.setText(accNo);
        edtAddr.setText(info.getAddress()!=null?info.getAddress():"");
    }
}
