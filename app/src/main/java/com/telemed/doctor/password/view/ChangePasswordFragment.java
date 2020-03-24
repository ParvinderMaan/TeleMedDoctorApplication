package com.telemed.doctor.password.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.password.model.ChangePasswordRequest;
import com.telemed.doctor.password.viewmodel.ChangePasswordViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;


public class ChangePasswordFragment extends BaseFragment {
    private AppCompatEditText  edtOldPassword, edtNewPassword, edtConfirmNewPassword;
    private Button btnSave;
    private ImageButton ibtnClose,ibtnBack;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private HomeFragmentSelectedListener mFragmentListener;
    private ChangePasswordViewModel mViewModel;
    private String mOldPassword, mNewPassword, mConfirmNewPassword;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;

    public ChangePasswordFragment() {
        // Required empty public
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        mFragmentListener = (HomeFragmentSelectedListener) context;

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initObserver();
    }

    private void initObserver() {

        mViewModel.getResultantChangePassword().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){
                            makeToast(response.getData().getMessage());
                            mFragmentListener.popTopMostFragment();
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






        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getViewEnabled()
                .observe(getViewLifecycleOwner(), isView -> {
                    edtOldPassword.setEnabled(isView);
                    edtNewPassword.setEnabled(isView);
                    edtConfirmNewPassword.setEnabled(isView);
                });
    }

    private void initView(View v) {
        ibtnBack=v.findViewById(R.id.ibtn_back);
        ibtnClose=v.findViewById(R.id.ibtn_close);

        edtOldPassword=v.findViewById(R.id.edt_old_password);
        edtNewPassword=v.findViewById(R.id.edt_new_password);
        edtConfirmNewPassword=v.findViewById(R.id.edt_confirm_new_password);
        btnSave=v.findViewById(R.id.btn_save);
        ibtnClose=v.findViewById(R.id.ibtn_close);


        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        tvAlertView = v.findViewById(R.id.tv_alert_view);
    }

    private void initListener() {
        ibtnClose.setOnClickListener(v1 -> {
            if(mFragmentListener!=null)
                mFragmentListener.popTopMostFragment();
        });


        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment",0);
        });


        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        btnSave.setOnClickListener(v -> attemptChangePassword());

        edtOldPassword.setOnEditorActionListener(mEditorActionListener);
        edtNewPassword.setOnEditorActionListener(mEditorActionListener);
        edtConfirmNewPassword.setOnEditorActionListener(mEditorActionListener);
    }

    private void attemptChangePassword() {

        if (isFormValid()) {
            edtOldPassword.clearFocus();edtNewPassword.clearFocus();edtConfirmNewPassword.clearFocus();
            ChangePasswordRequest in=new ChangePasswordRequest();
            in.setOldPassword(mOldPassword);
            in.setNewPassword(mNewPassword);
            mViewModel.attemptChangePassword(mHeaderMap,in);

        }

    }

    private boolean isFormValid() {
        mOldPassword=edtOldPassword.getText().toString();
        mNewPassword=edtNewPassword.getText().toString();
        mConfirmNewPassword=edtConfirmNewPassword.getText().toString();


        if (TextUtils.isEmpty(mOldPassword)) {
            edtOldPassword.setError("Enter current password");
            return false;
        }

        if (mOldPassword.contains(" ")) {
            edtOldPassword.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mNewPassword)) {
            edtNewPassword.setError("Enter new password");
            return false;
        }

        if (mNewPassword.contains(" ")) {
            edtNewPassword.setError("No Spaces Allowed");
            return false;
        }


        if (!(mNewPassword.length() >= 6)) {
            edtNewPassword.setError("Password is short");
            return false;
        }

        if (!Validator.isAlphaNumeric(mNewPassword)) {
            edtNewPassword.setError("Password must be alphanumeric");
            return false;
        }


        if (TextUtils.isEmpty(mConfirmNewPassword)) {
            edtConfirmNewPassword.setError("Enter confirm password");
            return false;
        }

        if (mConfirmNewPassword.contains(" ")) {
            edtConfirmNewPassword.setError("No Spaces Allowed");
            return false;
        }

        if (!mConfirmNewPassword.contentEquals(mNewPassword)) {
            edtConfirmNewPassword.setError("Your password is not matched");
            return false;
        }

        return true;
    }


    private EditText.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_old_password:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtOldPassword.isFocused()) edtNewPassword.requestFocus();
                        return true;
                    }
                    break;


                case R.id.edt_new_password:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtNewPassword.isFocused()) edtConfirmNewPassword.requestFocus();
                        return true;
                    }

                    break;

                case R.id.edt_confirm_new_password:
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        mFragmentListener.hideSoftKeyboard();
                        attemptChangePassword();
                        return true;
                    }

                    break;
            }

            return false;
        }
    };

}
