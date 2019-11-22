package com.telemed.doctor.signup;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;

import java.util.Calendar;


public class SignUpIIFragment extends BaseFragment {
    private AppCompatButton btnContinue;
    private AppCompatTextView tvCancel;
    private RouterFragmentSelectedListener mFragmentListener;
    private ProgressBar progressBar;
    private TextView edtDocName, edtDocSurname,edtDob,edtBirthCity, edtBirthCountry, edtNationality, edtSpeciality,
            edtLanguageOne, edtLanguageTwo, edtAddr, edtEmail;
    private String mDocName, mDocSurname,mDob,mBirthCity, mBirthCountry, mNationality, mSpeciality, mLanguageOne, mLanguageTwo, mAddr,
            mEmail;

    public SignUpIIFragment() {

    }

    public static SignUpIIFragment newInstance() {
        return new SignUpIIFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();


    }

    private void initListener() {
        btnContinue.setOnClickListener(mClickListener);
        tvCancel.setOnClickListener(mClickListener);
        edtDob.setOnClickListener(mClickListener);

        edtDocName.setOnEditorActionListener(mEditorActionListener);
        edtDocSurname.setOnEditorActionListener(mEditorActionListener);
        edtBirthCountry.setOnEditorActionListener(mEditorActionListener);
        edtNationality.setOnEditorActionListener(mEditorActionListener);
        edtSpeciality.setOnEditorActionListener(mEditorActionListener);
        edtLanguageOne.setOnEditorActionListener(mEditorActionListener);
        edtLanguageTwo.setOnEditorActionListener(mEditorActionListener);
        edtAddr.setOnEditorActionListener(mEditorActionListener);
        edtEmail.setOnEditorActionListener(mEditorActionListener);


    }

    private void initView(View v) {
        edtDocName = v.findViewById(R.id.edt_doc_name);
        edtDocSurname = v.findViewById(R.id.edt_doc_surname);
        edtDob= v.findViewById(R.id.edt_dob);
        edtBirthCity= v.findViewById(R.id.edt_birth_city);
        edtBirthCountry = v.findViewById(R.id.edt_birth_country);
        edtNationality = v.findViewById(R.id.edt_nationality);
        edtSpeciality = v.findViewById(R.id.edt_speciality);
        edtLanguageOne = v.findViewById(R.id.edt_language_one);
        edtLanguageTwo = v.findViewById(R.id.edt_language_two);
        edtAddr = v.findViewById(R.id.edt_addr);
        edtEmail = v.findViewById(R.id.edt_email);



        btnContinue = v.findViewById(R.id.btn_continue);
        tvCancel = v.findViewById(R.id.tv_cancel);
        progressBar = v.findViewById(R.id.progress_bar);


        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);


    }


    private View.OnClickListener mClickListener = v -> {
        switch (v.getId()) {

            case R.id.tv_cancel:
                if (mFragmentListener != null)
                    mFragmentListener.popTopMostFragment();
                break;

            case R.id.edt_dob:

               showDatePicker();
                break;

            case R.id.btn_continue:
                if (!isNetAvail()) {
                    makeToast("no internet");
                    return;
                }

                if (isFormValid()) {
                    attemptSignUp();
                }

                break;
        }


    };

    private void showDatePicker() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(getActivity(),R.style.PickerStyle ,(view1, year, monthOfYear, dayOfMonth) -> {
            String x=(monthOfYear + 1) + "-" + dayOfMonth + "-" + year;
            edtDob.setText(x);

        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis()- 568025136000L);  // -18yrs
        datePickerDialog.show();
    }

    private void attemptSignUp() {
        if (mFragmentListener != null)
            mFragmentListener.showFragment("SignUpIIIFragment");

    }
    /*
    mDocName, mDocSurname, mBirthCountry, mNationality, mSpeciality, mLanguageOne, mLanguageTwo, mAddr,
            mEmail;
     */

    private boolean isFormValid() {
        mDocName = edtDocName.getText().toString();
        mDocSurname = edtDocSurname.getText().toString();
        mDob= edtDob.getText().toString();
        mBirthCity= edtBirthCity.getText().toString();
        mBirthCountry = edtBirthCountry.getText().toString();
        mNationality = edtNationality.getText().toString();
        mSpeciality = edtSpeciality.getText().toString();
        mLanguageOne = edtLanguageOne.getText().toString();
        mLanguageTwo = edtLanguageTwo.getText().toString();
        mAddr = edtAddr.getText().toString();
        mEmail = edtEmail.getText().toString();




        if (TextUtils.isEmpty(mDocName)) {
            edtDocName.setError("Enter doctor name");
            return false;
        }

        if (mDocName.contains(" ")) {
            edtDocName.setError("No Spaces Allowed");
            return false;
        }

        if (TextUtils.isEmpty(mDocSurname)) {
            edtDocSurname.setError("Enter doctor surname");
            return false;
        }

        if (mDocSurname.contains(" ")) {
            edtDocSurname.setError("No Spaces Allowed");
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

        if (TextUtils.isEmpty(mSpeciality)) {
            edtSpeciality.setError("Enter speciality");
            return false;
        }


        if (TextUtils.isEmpty(mAddr)) {
            edtAddr.setError("Enter speciality");
            return false;
        }

        if (mAddr.contains(" ")) {
            edtAddr.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mEmail)) {
            edtEmail.setError("Enter Email address");
            return false;
        }

        if (mEmail.contains(" ")) {
            edtEmail.setError("No Spaces Allowed");
            return false;
        }

        if (!Validator.isEmailValid(mEmail)) {
            edtEmail.setError("Enter valid email address");
            return false;
        }

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
                        if (edtDocSurname.isFocused()) edtDocSurname.clearFocus();  hideSoftKeyboard(getActivity());
                        return true;
                    }

                    break;




                case R.id.edt_addr:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtAddr.isFocused()) edtEmail.requestFocus();
                        hideSoftKeyboard(getActivity());
                        return true;
                    }

                    break;

                case R.id.edt_email:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtEmail.isFocused()) edtEmail.clearFocus(); hideSoftKeyboard(getActivity());
                        hideSoftKeyboard(getActivity());
                        return true;
                    }

                    break;


            }


            return false;
        }
    };

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }



}
