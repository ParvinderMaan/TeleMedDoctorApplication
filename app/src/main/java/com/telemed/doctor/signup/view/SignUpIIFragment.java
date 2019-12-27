package com.telemed.doctor.signup.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.profile.model.Country;
import com.telemed.doctor.profile.model.Gender;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.profile.model.Speciliaty;
import com.telemed.doctor.profile.model.State;
import com.telemed.doctor.profile.view.ChooseOptionActivity;
import com.telemed.doctor.profile.view.ChooseOptionFragment;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.signup.model.SignUpIIRequest;
import com.telemed.doctor.signup.model.SignUpIIResponse;
import com.telemed.doctor.signup.viewmodel.SignUpIIViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SignUpIIFragment extends BaseFragment {
    private final String TAG = SignUpIIFragment.class.getSimpleName();
    private static int REQUEST_CODE_SELECT = 121;
    private AppCompatButton btnContinue;
    private AppCompatTextView tvCancel;
    private CustomAlertTextView tvAlertView;

    private RouterFragmentSelectedListener mFragmentListener;
    private ProgressBar progressBar;
    private AppCompatEditText edtDocName, edtDocSurname, edtDob, edtBirthCity, edtBirthCountry, edtNationality, edtSpeciality,
            edtLanguageOne, edtLanguageTwo, edtAddr, edtEmail, edtGender, edtCountry, edtState, edtCity;
    private String mDocName, mDocSurname, mDob, mBirthCity, mBirthCountry, mNationality, mSpeciality, mLanguageOne, mLanguageTwo, mAddr,
            mEmail, mGender, mCountry, mState, mCity;
    private ChooseOptionFragment mChooseOptionFragmnet;
    private SignUpIIViewModel mViewModel;
    private String mAccessToken;
    private FrameLayout flContainer;

    public SignUpIIFragment() {

    }

    public static SignUpIIFragment newInstance(Object payload) {
        SignUpIIFragment fragment = new SignUpIIFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (UserInfoWrapper) payload);
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
        if (getArguments() != null) {
            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_");
            if (objInfo != null) mAccessToken = objInfo.getAccessToken();
            if (objInfo != null) mEmail = objInfo.getEmail();

            Log.e(TAG, mAccessToken);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpIIViewModel.class);
        initView(v);
        initListener();
        initObserver();

        edtEmail.setText(mEmail);

    }

    private void initObserver() {

        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getViewEnabled()
                .observe(getViewLifecycleOwner(), this::resetView);

        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null) {
//                          SignUpIIResponse.Data data = response.getData().getData(); // adding Additional Info
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            UserInfoWrapper in = new UserInfoWrapper();
                            in.setAccessToken(mAccessToken);
//                            mFragmentListener.showFragment("SignUpIIIFragment", in);
                            Message msg = new Message();
                            msg.obj = in;
                            msg.what = 1;
                            mHandler.sendMessageDelayed(msg, 1500);

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

    }

    private void resetView(boolean isView) {

        edtDocName.setEnabled(isView);
        edtDocSurname.setEnabled(isView);
        edtDob.setEnabled(isView);
        edtBirthCity.setEnabled(isView);
        edtBirthCountry.setEnabled(isView);
        edtNationality.setEnabled(isView);
        edtSpeciality.setEnabled(isView);
        edtLanguageOne.setEnabled(isView);
        edtLanguageTwo.setEnabled(isView);
        edtAddr.setEnabled(isView);
        edtGender.setEnabled(isView);
        edtCountry.setEnabled(isView);
        edtState.setEnabled(isView);
        edtCity.setEnabled(isView);
        btnContinue.setEnabled(isView);

    }

    private void initListener() {
        btnContinue.setOnClickListener(mClickListener);
        tvCancel.setOnClickListener(mClickListener);
        edtDob.setOnClickListener(mClickListener);
        edtBirthCity.setOnClickListener(mClickListener);
        edtBirthCountry.setOnClickListener(mClickListener);
        edtNationality.setOnClickListener(mClickListener);
        edtSpeciality.setOnClickListener(mClickListener);
        edtLanguageOne.setOnClickListener(mClickListener);
        edtLanguageTwo.setOnClickListener(mClickListener);
        edtGender.setOnClickListener(mClickListener);
        edtCountry.setOnClickListener(mClickListener);
        edtState.setOnClickListener(mClickListener);

        edtDocName.setOnEditorActionListener(mEditorActionListener);
        edtDocSurname.setOnEditorActionListener(mEditorActionListener);
        edtAddr.setOnEditorActionListener(mEditorActionListener);


    }

    private void initView(View v) {
        flContainer = v.findViewById(R.id.fl_container);
        edtDocName = v.findViewById(R.id.edt_doc_name);
        edtDocSurname = v.findViewById(R.id.edt_doc_surname);
        edtDob = v.findViewById(R.id.edt_dob);
        edtBirthCity = v.findViewById(R.id.edt_birth_city);
        edtBirthCountry = v.findViewById(R.id.edt_birth_country);
        edtNationality = v.findViewById(R.id.edt_nationality);
        edtSpeciality = v.findViewById(R.id.edt_speciality);
        edtLanguageOne = v.findViewById(R.id.edt_language_one);
        edtLanguageTwo = v.findViewById(R.id.edt_language_two);
        edtAddr = v.findViewById(R.id.edt_addr);
        edtEmail = v.findViewById(R.id.edt_email);

        edtGender = v.findViewById(R.id.edt_gender);
        edtCountry = v.findViewById(R.id.edt_country);
        edtState = v.findViewById(R.id.edt_state);
        edtCity = v.findViewById(R.id.edt_city);


        btnContinue = v.findViewById(R.id.btn_continue);
        tvCancel = v.findViewById(R.id.tv_cancel);
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);


        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);


    }


    private View.OnClickListener mClickListener = v -> {
        switch (v.getId()) {

            case R.id.tv_cancel:
                if (mFragmentListener != null)
                    mFragmentListener.abortSignUpDialog();
                break;

            case R.id.edt_dob:
                showDatePicker();
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

            case R.id.btn_continue:

                if (isFormValid()) {
                    SignUpIIRequest inn = new SignUpIIRequest.Builder()
                            .setFirstName(mDocName)
                            .setLastName(mDocSurname)
                            .setDateOfBirth(mDob)
                            .setBirthCity(mBirthCity)
                            .setBirthCountry(mBirthCountry)
                            .setNationalityId((Integer) edtNationality.getTag())
                            .setGenderId((Integer) edtGender.getTag())
                            .setSpecialityId((Integer) edtSpeciality.getTag())
                            .setPrimaryLanguageId((Integer) edtLanguageOne.getTag())
                            .setSecondaryLanguageId((Integer) edtLanguageTwo.getTag())
                            .setCountryId((Integer) edtCountry.getTag())
                            .setStateId((Integer) edtState.getTag())
                            .setCity(mCity)
                            .setAddress1(mAddr)
                            .build();

                    Log.e(TAG,inn.toString());
                    Map<String, String> map = new HashMap<>();
                    map.put("content-type", "application/json");
                    map.put("Authorization", "Bearer " + mAccessToken);
                    mViewModel.setHeaderMap(map);
                    mViewModel.setSignUpIIInfo(inn);
                    mViewModel.attemptSignUp();
                }

                break;




            case R.id.edt_nationality:
                Intent inz = new Intent(getActivity(), ChooseOptionActivity.class);
                inz.putExtra("KEY_", "TAG_NATIONALITY");
                startActivityForResult(inz, REQUEST_CODE_SELECT);
                if(getActivity()!=null)
                getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                break;

            case R.id.edt_speciality:
                Intent iz = new Intent(getActivity(), ChooseOptionActivity.class);
                iz.putExtra("KEY_", (String) "TAG_SPECIALITY");
                startActivityForResult(iz, REQUEST_CODE_SELECT);
                if(getActivity()!=null)
                getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                break;

            case R.id.edt_language_one:
                Intent ii = new Intent(getActivity(), ChooseOptionActivity.class);
                ii.putExtra("KEY_", "TAG_LANGUAGE_ONE");
                startActivityForResult(ii, REQUEST_CODE_SELECT);
                if(getActivity()!=null)
                getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                break;

            case R.id.edt_language_two:
                Intent iii = new Intent(getActivity(), ChooseOptionActivity.class);
                iii.putExtra("KEY_", "TAG_LANGUAGE_TWO");
                startActivityForResult(iii, REQUEST_CODE_SELECT);
                if(getActivity()!=null)
                getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                break;

            case R.id.edt_gender:
                Intent iv = new Intent(getActivity(), ChooseOptionActivity.class);
                iv.putExtra("KEY_", "TAG_GENDER");
                startActivityForResult(iv, REQUEST_CODE_SELECT);
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

        }


    };



    /*     MM/dd/yyyy     */
    private void showDatePicker() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(getActivity(), R.style.PickerStyle, (view1, year, monthOfYear, dayOfMonth) -> {
            String x = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
            edtDob.setText(x);

        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis() - 568025136000L);  // -18yrs
        datePickerDialog.show();
    }




    private boolean isFormValid() {
        mDocName = edtDocName.getText().toString();
        mDocSurname = edtDocSurname.getText().toString();
        mDob = edtDob.getText().toString();
        mBirthCity = edtBirthCity.getText().toString();
        mBirthCountry = edtBirthCountry.getText().toString();
        mNationality = edtNationality.getText().toString();
        mSpeciality = edtSpeciality.getText().toString();
        mLanguageOne = edtLanguageOne.getText().toString();
        mLanguageTwo = edtLanguageTwo.getText().toString();
        mAddr = edtAddr.getText().toString();
//        mEmail = edtEmail.getText().toString();

        mGender = edtGender.getText().toString();
        mCountry = edtCountry.getText().toString();
        mState = edtState.getText().toString();
        mCity = edtCity.getText().toString();


        if (TextUtils.isEmpty(mDocName)) {
            edtDocName.setError("Enter doctor name");
            return false;
        }

//        if (mDocName.contains(" ")) {
//            edtDocName.setError("No Spaces Allowed");
//            return false;
//        }

        if (!Validator.isOnlyString(mDocName)) {
            edtDocName.setError("Only Alphabets Allowed");
            return false;
        }



        if (TextUtils.isEmpty(mDocSurname)) {
            edtDocSurname.setError("Enter doctor surname");
            return false;
        }

//        if (mDocSurname.contains(" ")) {
//            edtDocSurname.setError("No Spaces Allowed");
//            return false;
//        }


        if (!Validator.isOnlyString(mDocSurname)) {
            edtDocSurname.setError("Only Alphabets Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mDob)) {
            edtDob.setError("Enter date of birth");
            return false;
        }

        if (TextUtils.isEmpty(mBirthCity)) {
            edtBirthCity.setError("Enter city of birth");
            return false;
        }

        if (TextUtils.isEmpty(mBirthCountry)) {
            edtBirthCountry.setError("Enter birth country");
            return false;
        }

        if (TextUtils.isEmpty(mNationality)) {
            edtNationality.setError("Enter nationality");
            return false;
        }

        if (TextUtils.isEmpty(mGender)) {
            edtGender.setError("Enter gender");
            return false;
        }

        if (TextUtils.isEmpty(mSpeciality)) {
            edtSpeciality.setError("Enter speciality");
            return false;
        }

        if (TextUtils.isEmpty(mLanguageOne)) {
            edtLanguageOne.setError("Enter first language");
            return false;
        }

        if (TextUtils.isEmpty(mLanguageTwo)) {
            edtLanguageTwo.setError("Enter second language");
            return false;
        }

        if (TextUtils.isEmpty(mCountry)) {
            edtCountry.setError("Enter country");
            return false;
        }

        if (TextUtils.isEmpty(mState)) {
            edtState.setError("Enter state");
            return false;
        }

        if (TextUtils.isEmpty(mCity)) {
            edtCity.setError("Enter city");
            return false;
        }


        if (TextUtils.isEmpty(mAddr)) {
            edtAddr.setError("Enter speciality");
            return false;
        }

//        if (mAddr.contains(" ")) {
//            edtAddr.setError("No Spaces Allowed");
//            return false;
//        }


        return true;
    }


    private EditText.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_doc_name:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtDocName.isFocused()) edtDocSurname.requestFocus();
                        return true;
                    }
                    break;


                case R.id.edt_doc_surname:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtDocSurname.isFocused()) edtDocSurname.clearFocus();
                        mFragmentListener.hideSoftKeyboard();
                        return true;
                    }

                    break;


                case R.id.edt_addr:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtAddr.isFocused()) edtAddr.clearFocus();
                        if(mFragmentListener!=null) mFragmentListener.hideSoftKeyboard();
                        return true;
                    }

                    break;

            }

            return false;
        }
    };




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SELECT && resultCode == Activity.RESULT_OK && data != null) {
            String type = data.getStringExtra("KEY_");
            String item;Integer itemId;

            if(type==null) return;

            switch (type) {
                case "TAG_NATIONALITY":
                     item = data.getStringExtra( "KEY_NAME");
                    itemId= data.getIntExtra("KEY_ID",0);
                    edtNationality.setText(item);
                    edtNationality.setTag(itemId);
                    break;
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

                case "TAG_SPECIALITY":
                    item = data.getStringExtra( "KEY_NAME");
                    itemId= data.getIntExtra("KEY_ID",0);
                    edtSpeciality.setText(item);
                    edtSpeciality.setTag(itemId);
                    break;

                case "TAG_GENDER":
                    item = data.getStringExtra( "KEY_NAME");
                    itemId= data.getIntExtra("KEY_ID",0);
                    edtGender.setText(item);
                    edtGender.setTag(itemId);
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

    public void popChooseOptionFragment() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStackImmediate();

        }

    }

    @Override
    public void onDestroyView() {
        releaseResourse();
        super.onDestroyView();
    }


//    @Override
//    public void onDestroy() {
//        mHandler.removeMessages(1);
//        super.onDestroy();
//    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                UserInfoWrapper in = (UserInfoWrapper) msg.obj;
                if (mFragmentListener != null) {
                    mFragmentListener.showFragment("SignUpIIIFragment", in);
                }
            }


        }
    };



    private void releaseResourse() {

        btnContinue.setOnClickListener(null);
        tvCancel.setOnClickListener(null);
        edtDob.setOnClickListener(null);
        edtBirthCity.setOnClickListener(null);
        edtBirthCountry.setOnClickListener(null);
        edtNationality.setOnClickListener(null);
        edtSpeciality.setOnClickListener(null);
        edtLanguageOne.setOnClickListener(null);
        edtLanguageTwo.setOnClickListener(null);
        edtGender.setOnClickListener(null);
        edtCountry.setOnClickListener(null);
        edtState.setOnClickListener(null);

        edtDocName.setOnEditorActionListener(null);
        edtDocSurname.setOnEditorActionListener(null);
        edtAddr.setOnEditorActionListener(null);

        mHandler.removeMessages(1);

    }

}


/*

{firstName='fuvi', lastName='civib9', dateOfBirth='12/4/2001', birthCity='fhui', birthCountry='ubin9', nationalityId=3, genderId=2, specialityId=8, primaryLanguageId=5, secondaryLanguageId=2, address1='vbibono', city=' yih9j0j0', countryId=5, stateId=128}
 */