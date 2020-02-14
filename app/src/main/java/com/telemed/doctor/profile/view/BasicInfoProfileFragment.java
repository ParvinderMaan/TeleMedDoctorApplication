package com.telemed.doctor.profile.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.profile.model.BankInfoRequest;
import com.telemed.doctor.profile.model.BasicInfoRequest;
import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.profile.model.ProfileUpdateResponse;
import com.telemed.doctor.profile.viewmodel.BasicInfoProfileViewModel;

import java.util.HashMap;


public class BasicInfoProfileFragment extends Fragment {

    private static int REQUEST_CODE_SELECT = 121;
    private AppCompatEditText edtDocName, edtDocSurname, edtDob, edtBirthCity, edtBirthCountry, edtSpeciality,
            edtLanguageOne, edtLanguageTwo, edtAddr, edtEmail, edtNationality , edtCountry, edtState, edtCity
            ,edtPhoneNo;
    private BasicInfoProfileViewModel mViewModel;
    private ProgressBar progressBar;
    private Button btnEditSave;
    private String selectorState[]={"Edit","Save"};
    private HomeFragmentSelectedListener mFragmentListener;
    private String mLanguageOne , mLanguageTwo , mCountry , mState , mCity , mAddress , mEmail , mPhoneNo;

    public BasicInfoProfileFragment() {
        // Required empty public constructor
    }

    public static BasicInfoProfileFragment newInstance() {
        return new BasicInfoProfileFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_info_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BasicInfoProfileViewModel.class);
        initView(v);
        initListener();
        initObserver();

        mViewModel.fetchBasicInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SELECT && resultCode == Activity.RESULT_OK && data != null) {
            String type = data.getStringExtra("KEY_");
            String item;Integer itemId;

            if(type==null) return;

            switch (type) {

                case "TAG_LANGUAGE_ONE":
                    item = data.getStringExtra( "KEY_NAME");
                    itemId= data.getIntExtra("KEY_ID",0);
                    edtLanguageOne.setText(item);
                    edtLanguageOne.setTag(itemId);
                    break;

                case "TAG_LANGUAGE_TWO":
                    item = data.getStringExtra( "KEY_NAME");
                    itemId= data.getIntExtra("KEY_ID",0);
                    edtLanguageTwo.setText(item);
                    edtLanguageTwo.setTag(itemId);
                    break;

                case "TAG_COUNTRY":
                    item = data.getStringExtra( "KEY_NAME");
                    itemId= data.getIntExtra("KEY_ID",0);
                    edtCountry.setText(item);
                    edtCountry.setTag(itemId); // for fetching country id
                    break;

                case "TAG_STATE":
                    item = data.getStringExtra( "KEY_NAME");
                    itemId= data.getIntExtra("KEY_ID",0);
                    edtState.setText(item);
                    edtState.setTag(itemId);
                    break;

            }


        }
    }

    private void initListener() {

        edtLanguageOne.setOnClickListener(mClickListener);
        edtLanguageTwo.setOnClickListener(mClickListener);
        edtCountry.setOnClickListener(mClickListener);
        edtState.setOnClickListener(mClickListener);
        edtEmail.setOnClickListener(mClickListener);

        btnEditSave.setOnClickListener(v1 -> {
            String tag= (String) btnEditSave.getText();
            switch (tag){
                case "Edit":
                    mViewModel.setEditableView(true);
                    btnEditSave.setText(selectorState[1]);
                    break;
                case "Save":
                   // mViewModel.setEnableView(false);
                    btnEditSave.setText(selectorState[0]);

                    if(isFormValid()){
                        BasicInfoRequest in=new BasicInfoRequest.Builder()
                                .setPhoneNumber(mPhoneNo)
                                .setPrimaryLanguageId((Integer) edtLanguageOne.getTag())
                                .setsecondaryLanguageId((Integer) edtLanguageTwo.getTag())
                                .setAddress(mAddress)
                                .setCity(mCity)
                                .setCountryId((Integer) edtCountry.getTag())
                                .setStateId((Integer) edtState.getTag()).build();
                        mViewModel.updateBasicInfo(in);
                    }

                    break;
            }
        });
    }


    private boolean isFormValid() {

        mLanguageOne = edtLanguageOne.getText().toString();
        mLanguageTwo = edtLanguageTwo.getText().toString();
        mCountry = edtCountry.getText().toString();
        mState = edtState.getText().toString();
        mCity=edtCity.getText().toString();
        mAddress=edtAddr.getText().toString();
        mEmail=edtEmail.getText().toString();
        mPhoneNo = edtPhoneNo.getText().toString();

        if (TextUtils.isEmpty(mLanguageOne)) {
            edtLanguageOne.setError("Select Language");
            return false;
        }

        if (TextUtils.isEmpty(mLanguageTwo)) {
            edtLanguageTwo.setError("Select Language");
            return false;
        }

        if (TextUtils.isEmpty(mCountry)) {
            edtCountry.setError("Select Country");
            return false;
        }

        if (TextUtils.isEmpty(mState)) {
            edtState.setError("Select State");
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

        if (TextUtils.isEmpty(mPhoneNo)) {
            edtPhoneNo.setError("Enter Phone No");
            return false;
        }

        return true;
    }

    private View.OnClickListener mClickListener = v -> {
        switch (v.getId()) {

            case R.id.edt_language_one:
                Intent ii = new Intent(getActivity(), ChooseOptionActivity.class);
                ii.putExtra("KEY_", "TAG_LANGUAGE_ONE");
                startActivityForResult(ii, REQUEST_CODE_SELECT);
                if (getActivity() != null)
                    getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;

            case R.id.edt_language_two:
                Intent iii = new Intent(getActivity(), ChooseOptionActivity.class);
                iii.putExtra("KEY_", "TAG_LANGUAGE_TWO");
                startActivityForResult(iii, REQUEST_CODE_SELECT);
                if(getActivity()!=null)
                    getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                break;

            case R.id.edt_country:
                Intent v1 = new Intent(getActivity(), ChooseOptionActivity.class);
                v1.putExtra("KEY_", "TAG_COUNTRY");
                startActivityForResult(v1, REQUEST_CODE_SELECT);
                if(getActivity()!=null)
                    getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                break;


            case R.id.edt_state:
                String countryId= String.valueOf((Integer) edtCountry.getTag());
                Intent in = new Intent(getActivity(), ChooseOptionActivity.class);
                in.putExtra("KEY_", "TAG_STATE");
                in.putExtra("KEY_COUNTRY_ID",countryId);
                startActivityForResult(in, REQUEST_CODE_SELECT);
                if(getActivity()!=null)
                    getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                break;

            case R.id.edt_email:

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("UpdateEmailFragment",null);

                break;

        }
    };


    private void initObserver() {
        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        BasicInfoResponse.BasicDetail infoObj = response.getData().getData();
                        mViewModel.setBasicDetail(infoObj);
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
          //  mViewModel.setEnableView(true);

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

        mViewModel.getEditableView()
                .observe(getViewLifecycleOwner(), isEnable -> {
                    enableView(isEnable);
                });


        mViewModel.getBasicDetail().observe(getViewLifecycleOwner(), this::setView);

        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


    }

    private void setView(BasicInfoResponse.BasicDetail info) {
        if(info ==null) return;

        // setting up
        edtDocName.setText(info.getFirstName() != null ? info.getFirstName() : "");
        edtDocSurname.setText(info.getLastName() != null ? info.getLastName() : "");
        edtDob.setText(info.getDateOfBirth() != null ? info.getDateOfBirth() : "");
        edtBirthCity.setText(info.getBirthCity() != null ? info.getBirthCity() : "");
        edtBirthCountry.setText(info.getBirthCountry() != null ? info.getBirthCountry() : "");
        edtSpeciality.setText(info.getSpeciality() != null ? info.getSpeciality() : "");
        edtLanguageOne.setText(info.getPrimaryLanguage() != null ? info.getPrimaryLanguage() : "");
        edtLanguageTwo.setText(info.getSecondaryLanguage() != null ? info.getSecondaryLanguage() : "");
        edtNationality.setText(info.getNationality() != null ? info.getNationality() : "");
        edtCountry.setText(info.getCountryName() != null ? info.getCountryName() : "");
        edtState.setText(info.getStateName() != null ? info.getStateName() : "");
        edtCity.setText(info.getCity() != null ? info.getCity() : "");
        edtAddr.setText(info.getAddress1() != null ? info.getCity() : "");
        edtPhoneNo.setText(info.getPhoneNumber() != null ? info.getPhoneNumber() : "");
        edtEmail.setText(info.getEmail()!=null?info.getEmail():"");


        //----------
//        if (getParentFragment() != null) {
//            ((ProfileFragment)getParentFragment()).updateUi(info);
//        }

    }

    private void enableView(boolean isEnable) {
        edtLanguageOne.setEnabled(isEnable);
        edtLanguageTwo.setEnabled(isEnable);
        edtCountry.setEnabled(isEnable);
        edtState.setEnabled(isEnable);
        edtCity.setEnabled(isEnable);
        edtAddr.setEnabled(isEnable);
        edtEmail.setEnabled(isEnable);
        edtPhoneNo.setEnabled(isEnable);

    }

    private void initView(View v) {
        edtDocName = v.findViewById(R.id.edt_doc_name);
        edtDocSurname = v.findViewById(R.id.edt_doc_surname);
        edtDob = v.findViewById(R.id.edt_dob);
        edtBirthCity = v.findViewById(R.id.edt_birth_city);
        edtBirthCountry = v.findViewById(R.id.edt_birth_country);
        edtSpeciality = v.findViewById(R.id.edt_speciality);
        edtLanguageOne = v.findViewById(R.id.edt_language_one);
        edtLanguageTwo = v.findViewById(R.id.edt_language_two);
        edtNationality = v.findViewById(R.id.edt_nationality);
        edtCountry = v.findViewById(R.id.edt_country);
        edtState = v.findViewById(R.id.edt_state);
        edtCity = v.findViewById(R.id.edt_city);
        edtPhoneNo = v.findViewById(R.id.edt_phoneNo);


        edtAddr = v.findViewById(R.id.edt_addr);
        edtEmail = v.findViewById(R.id.edt_email);
        btnEditSave = v.findViewById(R.id.btn_edit_save);

        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        btnEditSave.setOnClickListener(vv->{

        });

    }
}
