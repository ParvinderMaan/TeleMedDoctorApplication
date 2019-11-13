package com.telemed.doctor.profile;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView tvBasicInfo,tvProfessionalInfo,tvBankInfo;
    private ImageButton ibtnClose;
    private BankInfoProfileFragment mBankInfoFragment;
    private ProfessionalInfoProfileFragment mProfessionalInfoFragment;
    private BasicInfoProfileFragment mBasicInfoFragment;


    public static ProfileFragment newInstance() {
      return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasicInfoFragment = BasicInfoProfileFragment.newInstance();
        mProfessionalInfoFragment = ProfessionalInfoProfileFragment.newInstance();
        mBankInfoFragment = BankInfoProfileFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);

        //@ initialization
        tvBasicInfo.setSelected(true);
        tvProfessionalInfo.setSelected(false);
        tvBankInfo.setSelected(false);
        showFragment("BASIC_TAG");
    }

    private void initView(View v) {
        ibtnClose= v.findViewById(R.id.ibtn_close);
        tvBasicInfo= v.findViewById(R.id.tv_basic_info);
        tvProfessionalInfo= v.findViewById(R.id.tv_professional_info);
        tvBankInfo= v.findViewById(R.id.tv_bank_info);

        tvBasicInfo.setOnClickListener(mClickListener);
        tvProfessionalInfo.setOnClickListener(mClickListener);
        tvBankInfo.setOnClickListener(mClickListener);
        ibtnClose.setOnClickListener(mClickListener);
    }

    private void showFragment(String tag) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        switch (tag) {

            case "BASIC_TAG":
                if (mBasicInfoFragment.isAdded()) ft.show(mBasicInfoFragment);
                 else ft.add(R.id.fl_container, mBasicInfoFragment);
                if (mProfessionalInfoFragment.isAdded())  ft.hide(mProfessionalInfoFragment);
                if (mBankInfoFragment.isAdded())  ft.hide(mBankInfoFragment);
                ft.commit();

                break;
            case "PROFESSIONAL_TAG":
                if (mProfessionalInfoFragment.isAdded()) ft.show(mProfessionalInfoFragment);
                else ft.add(R.id.fl_container, mProfessionalInfoFragment);
                if (mBasicInfoFragment.isAdded())  ft.hide(mBasicInfoFragment);
                if (mBankInfoFragment.isAdded())  ft.hide(mBankInfoFragment);
                ft.commit();

                break;
            case "BANK_TAG":
                if (mBankInfoFragment.isAdded()) ft.show(mBankInfoFragment);
                else ft.add(R.id.fl_container, mBankInfoFragment);
                if (mBasicInfoFragment.isAdded())  ft.hide(mBasicInfoFragment);
                if (mProfessionalInfoFragment.isAdded())  ft.hide(mProfessionalInfoFragment);
                ft.commit();
                break;


        }

    }





    private View.OnClickListener mClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          switch (v.getId()){

              case R.id.ibtn_back:
                   if(getActivity()!=null)
                       ((HomeActivity)getActivity()).popTopMostFragment();
                  break;
              case R.id.tv_basic_info:
                  tvBasicInfo.setSelected(true);
                  tvProfessionalInfo.setSelected(false);
                  tvBankInfo.setSelected(false);
                  showFragment("BASIC_TAG");

                  break;
              case R.id.tv_professional_info:

                  tvBasicInfo.setSelected(false);
                  tvProfessionalInfo.setSelected(true);
                  tvBankInfo.setSelected(false);
                  showFragment("PROFESSIONAL_TAG");
                  break;
              case R.id.tv_bank_info:
                  tvBasicInfo.setSelected(false);
                  tvProfessionalInfo.setSelected(false);
                  tvBankInfo.setSelected(true);
                  showFragment("BANK_TAG");
                  break;
          }


        }
    };
}