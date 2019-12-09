package com.telemed.doctor.signup.view;


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
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
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
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.profile.model.Gender;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.profile.model.Speciliaty;
import com.telemed.doctor.profile.view.ChooseOptionFragment;
import com.telemed.doctor.signup.model.SignUpIIRequest;
import com.telemed.doctor.signup.viewmodel.SignUpIIViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.ArrayList;
import java.util.Calendar;


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
        mViewModel = ViewModelProviders.of(this).get(SignUpIIViewModel.class);
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
        edtGender.setOnClickListener(mClickListener);

        edtDocName.setOnEditorActionListener(mEditorActionListener);
        edtDocSurname.setOnEditorActionListener(mEditorActionListener);
        edtAddr.setOnEditorActionListener(mEditorActionListener);
        edtEmail.setOnEditorActionListener(mEditorActionListener);


    }

    private void initView(View v) {
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
                    mFragmentListener.abortSignUp();
                break;

            case R.id.edt_dob:

                showDatePicker();
                break;

            case R.id.btn_continue:
                if (!isNetAvail()) {
                    tvAlertView.showTopAlert("No Internet");
                    return;
                }

//                if (isFormValid()) {
                SignUpIIRequest in = new SignUpIIRequest.Builder()
                        .setFirstName("Pappu")
                        .setLastName("Maan")
                        .setDateOfBirth("2019-12-09")
                        .setBirthCity("Abi")
                        .setBirthCountry("asdasd")
                        .setNationalityId(2)
                        .setGenderId(2)
                        .setSpecialityId(1)
                        .setPrimaryLanguageId(1)
                        .setSecondaryLanguageId(2)
                        .setAddress1("")
                        .setCity("sdas")
                        .setCountryId(1)
                        .setStateId(1)
                        .setPostalCode("12")
                        .setPrefAddress("sdasd")
                        .build();
                mViewModel.attemptSignUp(in);
//                }

                break;

            case R.id.edt_birth_city:
//                    Intent in=new Intent(getActivity(), ChooseOptionActivity.class);
//                    in.putExtra("KEY_",(String)"TAG_BIRTH_CITY");
//                    startActivityForResult(in, REQUEST_CODE_SELECT);

                mChooseOptionFragmnet = ChooseOptionFragment.newInstance("TAG_BIRTH_CITY");
                mChooseOptionFragmnet.setOnChooseOptionSelectedListener(new ChooseOptionFragment.ChooseOptionFragmentSelectedListener() {
                    @Override
                    public void onItemSelected(Object item, String type) {
                        //  if(type.equals("TAG_BIRTH_CITY")) edtBirthCity.setText(item);
                        //  popChooseOptionFragment();
                    }
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_container, mChooseOptionFragmnet)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.edt_birth_country:
//                Intent inn=new Intent(getActivity(), ChooseOptionActivity.class);
//                inn.putExtra("KEY_",(String)"TAG_BIRTH_COUNTRY");
//                startActivityForResult(inn, REQUEST_CODE_SELECT);
                mChooseOptionFragmnet = ChooseOptionFragment.newInstance("TAG_BIRTH_COUNTRY");
                mChooseOptionFragmnet.setOnChooseOptionSelectedListener(new ChooseOptionFragment.ChooseOptionFragmentSelectedListener() {
                    @Override
                    public void onItemSelected(Object item, String type) {
//                        if(type.equals("TAG_BIRTH_COUNTRY")) edtBirthCountry.setText(item);
//                        popChooseOptionFragment();
                    }
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_container, mChooseOptionFragmnet)
                        .addToBackStack(null)
                        .commit();
                break;


            case R.id.edt_nationality:
//                Intent innn=new Intent(getActivity(), ChooseOptionActivity.class);
//                innn.putExtra("KEY_",(String)"TAG_NATIONALITY");
//                startActivityForResult(innn, REQUEST_CODE_SELECT);
                mChooseOptionFragmnet = ChooseOptionFragment.newInstance("TAG_NATIONALITY");
                mChooseOptionFragmnet.setOnChooseOptionSelectedListener(new ChooseOptionFragment.ChooseOptionFragmentSelectedListener() {
                    @Override
                    public void onItemSelected(Object item, String type) {
//                        if(type.equals("TAG_NATIONALITY")) edtNationality.setText(item);
//                        popChooseOptionFragment();
                    }
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_container, mChooseOptionFragmnet)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.edt_speciality:
//                Intent iz=new Intent(getActivity(), ChooseOptionActivity.class);
//                iz.putExtra("KEY_",(String)"TAG_SPECIALITY");
//                startActivityForResult(iz, REQUEST_CODE_SELECT);
                mChooseOptionFragmnet = ChooseOptionFragment.newInstance("TAG_SPECIALITY");
                mChooseOptionFragmnet.setOnChooseOptionSelectedListener(new ChooseOptionFragment.ChooseOptionFragmentSelectedListener() {
                    @Override
                    public void onItemSelected(Object item, String type) {
                        if (type.equals("TAG_SPECIALITY")) {
                            Speciliaty speciliaty = (Speciliaty) item;
                            edtSpeciality.setText(speciliaty.getName());
                            popChooseOptionFragment();
                        }
                    }
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_container, mChooseOptionFragmnet)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.edt_language_one:
                //  showLanguageDialog(edtLanguageOne);
                mChooseOptionFragmnet = ChooseOptionFragment.newInstance("TAG_LANGUAGE_ONE");
                mChooseOptionFragmnet.setOnChooseOptionSelectedListener(new ChooseOptionFragment.ChooseOptionFragmentSelectedListener() {
                    @Override
                    public void onItemSelected(Object item, String type) {
                        if (type.equals("TAG_LANGUAGE_ONE")) {
                            Language language = (Language) item;
                            edtLanguageOne.setText(language.getName());
                            popChooseOptionFragment();
                        }


                    }
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_container, mChooseOptionFragmnet)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.edt_language_two:
                //   showLanguageDialog(edtLanguageTwo);
                mChooseOptionFragmnet = ChooseOptionFragment.newInstance("TAG_LANGUAGE_TWO");
                mChooseOptionFragmnet.setOnChooseOptionSelectedListener(new ChooseOptionFragment.ChooseOptionFragmentSelectedListener() {
                    @Override
                    public void onItemSelected(Object item, String type) {
                        if (type.equals("TAG_LANGUAGE_TWO")) {
                            Language language = (Language) item;
                            edtLanguageTwo.setText(language.getName());
                            popChooseOptionFragment();
                        }
                    }
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_container, mChooseOptionFragmnet)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.edt_gender:
                mChooseOptionFragmnet = ChooseOptionFragment.newInstance("TAG_GENDER");
                mChooseOptionFragmnet.setOnChooseOptionSelectedListener(new ChooseOptionFragment.ChooseOptionFragmentSelectedListener() {
                    @Override
                    public void onItemSelected(Object item, String type) {
                        if (type.equals("TAG_GENDER")) {
                            Gender gender = (Gender) item;
                            edtGender.setText(gender.getName());
                        }
                        popChooseOptionFragment();
                    }
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_container, mChooseOptionFragmnet)
                        .addToBackStack(null)
                        .commit();
                break;
        }


    };

    private void showSpecialityDialog() {

        final ArrayList itemsSelected = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
        //   builder.setTitle("Select Language ");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        builder.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setText("Select Speciality");
        String[] mLanguageItems = getResources().getStringArray(R.array.array_speciality);

        builder.setMultiChoiceItems(mLanguageItems, null,
                (dialog, selectedItemId, isSelected) -> {

                    if (isSelected) {
                        itemsSelected.add(selectedItemId);
                    } else if (itemsSelected.contains(selectedItemId)) {
                        itemsSelected.remove(Integer.valueOf(selectedItemId));
                    }
                });

        builder.setSingleChoiceItems(mLanguageItems, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtSpeciality.setText("" + which);

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
        datePickerDialog = new DatePickerDialog(getActivity(), R.style.PickerStyle, (view1, year, monthOfYear, dayOfMonth) -> {
            String x = (monthOfYear + 1) + "-" + dayOfMonth + "-" + year;
            edtDob.setText(x);

        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis() - 568025136000L);  // -18yrs
        datePickerDialog.show();
    }

    private void attemptSignUp() {
//        if (mFragmentListener != null)
//            mFragmentListener.showFragment("SignUpIIIFragment", null);

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


    private void showLanguageDialog(AppCompatEditText edtLanguage) {
        final String[] mProvinceItems = getResources().getStringArray(R.array.array_language);
        final ArrayList itemsSelected = new ArrayList();
        Dialog dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_CODE_SELECT && resultCode == Activity.RESULT_OK && data != null) {
//            String type = data.getStringExtra("KEY_");
//            String item;
//            switch (type) {
//                case "TAG_NATIONALITY":
//                     item = data.getStringExtra( "TAG_NATIONALITY");
//                    edtNationality.setText(item);
//
//                    break;
//                case "TAG_BIRTH_COUNTRY":
//                     item = data.getStringExtra( "TAG_BIRTH_COUNTRY");
//                    edtBirthCountry.setText(item);
//                    break;
//
//                case "TAG_BIRTH_CITY":
//                    item = data.getStringExtra( "TAG_BIRTH_CITY");
//                    edtBirthCity.setText(item);
//                    break;
//
//                case "TAG_SPECIALITY":
//                    item = data.getStringExtra( "TAG_SPECIALITY");
//                    edtSpeciality.setText(item);
//                    break;
//
//
//            }
//
//
//        }
//    }

    public void popChooseOptionFragment() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStackImmediate();

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
