package com.telemed.doctor.profile.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.profile.model.ChangeEmailRequest;
import com.telemed.doctor.profile.viewmodel.ChangeEmailViewModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;

public class ChangeEmailFragment extends Fragment {

    private final String TAG= ChangeEmailFragment.class.getSimpleName();
    private AppCompatEditText edtCurrentPassword, edtNewEmail;
    private TextView textCancel;
    private HomeFragmentSelectedListener mFragmentListener;
    private String mUserEmail, mUserPassword;
    private ChangeEmailViewModel mViewModel;
    private Button btnUpdateEmail;
    private String mAccessToken;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;

    public static ChangeEmailFragment newInstance(Object payload) {
          ChangeEmailFragment fragment=new ChangeEmailFragment();
          Bundle bundle=new Bundle();
          bundle.putParcelable("KEY_ACCESS_TOKEN",(UserInfoWrapper)payload);
          fragment.setArguments(bundle);
          return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    public ChangeEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_ACCESS_TOKEN");
            if (objInfo != null) mAccessToken =objInfo.getAccessToken();

//            Log.e(TAG,mAccessToken);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return inflater.inflate(R.layout.fragment_change_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChangeEmailViewModel.class);
        initView(v);
        initListener();
        initObserver();
    }

    private void initView(View v) {
        edtCurrentPassword = v.findViewById(R.id.edt_current_password);
        edtNewEmail = v.findViewById(R.id.edt_new_email);
        btnUpdateEmail = v.findViewById(R.id.btn_updateEmail);
        textCancel = v.findViewById(R.id.textCancel);
        tvAlertView = v.findViewById(R.id.tv_alert);

        progressBar = v.findViewById(R.id.progress_bar);
        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

    }

    private void initListener() {
        btnUpdateEmail.setOnClickListener(mOnClickListener);
        textCancel.setOnClickListener(mOnClickListener);

        edtCurrentPassword.setOnEditorActionListener(mEditorActionListener);
        edtNewEmail.setOnEditorActionListener(mEditorActionListener);
    }

    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()) {

            case R.id.btn_updateEmail:
                attemptChangeEmail();
                break;

            case R.id.textCancel:
                if (mFragmentListener != null)
                    mFragmentListener.popTopMostFragment();
                break;

        }
    };

    private EditText.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_usr_email:
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        // mFragmentListener.hideSoftKeyboard();
                        attemptChangeEmail();
                        return true;
                    }
                    break;

                case R.id.edt_usr_password:
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            if (edtCurrentPassword.isFocused()) edtNewEmail.requestFocus();
                            return true;
                        }
                    break;

            }


            return false;
        }
    };

    private void attemptChangeEmail() {

        if (isFormValid()) {
            ChangeEmailRequest in = new ChangeEmailRequest.Builder()
                    .setnewEmail(mUserEmail)
                    .setcurrentPassword(mUserPassword)
                    .build();
            mViewModel.setChangeaEmailInfo(in);
            edtNewEmail.clearFocus();edtCurrentPassword.clearFocus();
            mViewModel.attemptChangeEmail();
        }

    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


//        mViewModel.getEnableView()
//                .observe(getViewLifecycleOwner(), this::resetEnableView);


        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){
                            //data = response.getData().getData(); // adding Additional Info
                            //  data.setEmail(mUserEmail);
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            UserInfoWrapper infoWrapper = new UserInfoWrapper();
                            infoWrapper.setEmail(edtNewEmail.getText().toString());
                            infoWrapper.setOldPassword(edtCurrentPassword.getText().toString());
                            edtNewEmail.setText("");edtCurrentPassword.setText("");
                            if (mFragmentListener != null)
                                mFragmentListener.popTopMostFragment();
                                mFragmentListener.showFragment("OneTimeFragment", infoWrapper);

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



    private boolean isFormValid() {

        mUserEmail = edtNewEmail.getText().toString();
        mUserPassword = edtCurrentPassword.getText().toString();

        if (TextUtils.isEmpty(mUserEmail)) {
            edtNewEmail.setError("Enter New Email address");
            return false;
        }
        if (mUserEmail.contains(" ")) {
            edtNewEmail.setError("No Spaces Allowed");
            return false;

        }

        if (!Validator.isEmailValid(mUserEmail)) {
            edtNewEmail.setError("Enter valid email address");
            return false;

        }


        if (mUserPassword.isEmpty()) {
            edtCurrentPassword.setError("Enter Current Password");
            return false;

        }
        if (mUserPassword.contains(" ")) {
            edtCurrentPassword.setError("No Spaces Allowed");
            return false;

        }

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
