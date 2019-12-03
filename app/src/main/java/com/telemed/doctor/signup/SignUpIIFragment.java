package com.telemed.doctor.signup;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class SignUpIIFragment extends BaseFragment {
    private AppCompatButton btnContinue;
    private AppCompatTextView tvCancel;
    private RouterFragmentSelectedListener mFragmentListener;
    private ProgressBar progressBar;
    private AppCompatEditText edtDocName, edtDocSurname,edtDob,edtBirthCity, edtBirthCountry, edtNationality, edtSpeciality,
            edtLanguageOne, edtLanguageTwo, edtAddr, edtEmail,edtGender,edtCountry,edtState,edtCity;
    private String mDocName, mDocSurname,mDob,mBirthCity, mBirthCountry, mNationality, mSpeciality, mLanguageOne, mLanguageTwo, mAddr,
            mEmail,mGender,mCountry,mState, mCity;

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
        edtBirthCity.setOnClickListener(mClickListener);
        edtBirthCountry.setOnClickListener(mClickListener);
        edtNationality.setOnClickListener(mClickListener);
        edtSpeciality.setOnClickListener(mClickListener);
        edtLanguageOne.setOnClickListener(mClickListener);
        edtLanguageTwo.setOnClickListener(mClickListener);


        edtDocName.setOnEditorActionListener(mEditorActionListener);
        edtDocSurname.setOnEditorActionListener(mEditorActionListener);
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

        edtGender= v.findViewById(R.id.edt_gender);
        edtCountry= v.findViewById(R.id.edt_country);
        edtState= v.findViewById(R.id.edt_state);
        edtCity= v.findViewById(R.id.edt_city);


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

//                if (isFormValid()) {
                    attemptSignUp();
//                }

                break;

            case R.id.edt_birth_city:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ChooseTeritoryFragment","TAG_BIRTH_CITY" );

                break;

            case R.id.edt_birth_country:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ChooseTeritoryFragment","TAG_BIRTH_COUNTRY" );

                break;


            case  R.id.edt_nationality:

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ChooseTeritoryFragment","TAG_NATIONALITY" );
                break;

            case  R.id.edt_speciality:
              //  showSpecialityDialog();
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ChooseTeritoryFragment","TAG_SPECIALITY" );
                break;

            case  R.id.edt_language_one:
                   showLanguageDialog(edtLanguageOne);

                break;

            case  R.id.edt_language_two:
                  showLanguageDialog(edtLanguageTwo);

                break;
        }


    };

    private void showSpecialityDialog() {

        final ArrayList itemsSelected = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AppCompatAlertDialogStyle);
        //   builder.setTitle("Select Language ");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        builder.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setText("Select Speciality");
        String []mLanguageItems=getResources().getStringArray(R.array.array_speciality);

        builder.setMultiChoiceItems(mLanguageItems, null,
                (dialog, selectedItemId, isSelected) -> {

                    if (isSelected) {
                        itemsSelected.add(selectedItemId);
                    } else if (itemsSelected.contains(selectedItemId)) {
                        itemsSelected.remove(Integer.valueOf(selectedItemId));
                    }
                });

        builder.setSingleChoiceItems(mLanguageItems,-1,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtSpeciality.setText(""+which);

            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(getResources().getString(R.string.title_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });



        Dialog dialog = builder.create();
        dialog.show();

    }

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
            mFragmentListener.showFragment("SignUpIIIFragment", null);

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

        mGender = edtGender.getText().toString();
        mCountry = edtCountry.getText().toString();
        mState = edtState.getText().toString();
        mCity = edtCity.getText().toString();



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

        if (mAddr.contains(" ")) {
            edtAddr.setError("No Spaces Allowed");
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
                        if (edtDocSurname.isFocused()) edtDocSurname.clearFocus();
                        mFragmentListener.hideSoftKeyboard();
                        return true;
                    }

                    break;




                case R.id.edt_addr:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtAddr.isFocused()) edtEmail.requestFocus();
                        mFragmentListener.hideSoftKeyboard();
                        return true;
                    }

                    break;

                case R.id.edt_email:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtEmail.isFocused()) edtEmail.clearFocus();
                        mFragmentListener.hideSoftKeyboard();
                        return true;
                    }

                    break;


            }


            return false;
        }
    };


    public void updateUi(String item, String tag) {
        switch (tag) {

            case "TAG_NATIONALITY":
                edtNationality.setText(item);

                break;
            case "TAG_BIRTH_COUNTRY":
                edtBirthCountry.setText(item);
                break;

            case "TAG_BIRTH_CITY":
                edtBirthCity.setText(item);
                break;

            case "TAG_SPECIALITY":
                edtSpeciality.setText(item);
                break;


        }

    }
    private void showLanguageDialog(AppCompatEditText edtLanguage) {
        final String[] mProvinceItems = getResources().getStringArray(R.array.array_language);
        final ArrayList itemsSelected = new ArrayList();
        Dialog dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        // builder.setTitle("Select Your Region");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        builder.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setText("Select your Language");

        builder.setSingleChoiceItems(mProvinceItems, 0, null)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        try {
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            edtLanguage.setText(mProvinceItems[selectedPosition]);
                        } catch (Exception io) {
                            io.printStackTrace();
                        }

                        // Do something useful withe the position of the selected radio button
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }
}
