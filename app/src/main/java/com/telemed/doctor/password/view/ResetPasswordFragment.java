package com.telemed.doctor.password.view;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.model.ResetPasswordRequest;
import com.telemed.doctor.password.viewmodel.ResetPasswordViewModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    private final String TAG=ResetPasswordFragment.class.getSimpleName();
    private ProgressBar progressBar;
    private AppCompatEditText edtOtp,edtPassword,edtConfirmPassword;
    private CustomAlertTextView tvAlertView;
    private TextView tvCancel;
    private Button btnResetPassword;
    private RouterFragmentSelectedListener mFragmentListener;
    private Integer mOtpServer;
    private String mOtpClient,mUsrPassword,mUsrConfirmPassword,mUsrEmail;
    private ResetPasswordViewModel mViewModel;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    public static ResetPasswordFragment newInstance(Object payload) {
        ResetPasswordFragment fragment=new ResetPasswordFragment();
        Bundle bundle=new Bundle();
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
        if(getArguments()!=null){
            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_");
            if (objInfo != null) mOtpServer =objInfo.getOtpCode();
            if (objInfo != null) mUsrEmail =objInfo.getEmail();
            Log.e(TAG, String.valueOf(mOtpServer));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_reset_password, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel.class);

        initView(v);
        initListener();
        initObserver();

        if(mOtpServer!=null) mHandler.sendEmptyMessageDelayed(1,1500);
    }

    private void initObserver() {

        mViewModel.getResultantResetPassword().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//                          mFragmentListener.popTillFragment("SignInFragment", 0);
                            mHandler.sendEmptyMessageDelayed(2, 1500);

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
                    edtOtp.setEnabled(isView);
                    edtPassword.setEnabled(isView);
                    edtConfirmPassword.setEnabled(isView);
                    btnResetPassword.setEnabled(isView);

                });
    }

    private void initListener() {
        tvCancel.setOnClickListener(v -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });
        btnResetPassword.setOnClickListener(v -> attemptResetPassword());


        edtOtp.setOnEditorActionListener(mEditorActionListener);
        edtPassword.setOnEditorActionListener(mEditorActionListener);
        edtConfirmPassword.setOnEditorActionListener(mEditorActionListener);


    }


    private void initView(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        edtOtp = v.findViewById(R.id.edt_otp);
        edtPassword = v.findViewById(R.id.edt_password);
        edtConfirmPassword = v.findViewById(R.id.edt_confirm_password);

        tvAlertView = v.findViewById(R.id.tv_alert_view);

        tvCancel = v.findViewById(R.id.tv_cancel);
        btnResetPassword = v.findViewById(R.id.btn_reset_password);



    }


    private boolean isFormValid() {
        mOtpClient= edtOtp.getText().toString();
        mUsrPassword=edtPassword.getText().toString();
        mUsrConfirmPassword=edtConfirmPassword.getText().toString();




        if (TextUtils.isEmpty(mOtpClient)) {
            edtOtp.setError("Enter OTP");
            return false;
        }

        if (mOtpClient.contains(" ")) {
            edtPassword.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mUsrPassword)) {
            edtPassword.setError("Enter password");
            return false;
        }

        if (mUsrPassword.contains(" ")) {
            edtPassword.setError("No Spaces Allowed");
            return false;
        }


        if (!(mUsrPassword.length() >= 6)) {
            edtPassword.setError("Password is short");
            return false;
        }

        if (!Validator.isAlphaNumeric(mUsrPassword)) {
            edtPassword.setError("Password must be alphanumeric");
            return false;
        }


        if (TextUtils.isEmpty(mUsrConfirmPassword)) {
            edtConfirmPassword.setError("Enter confirm password");
            return false;
        }

        if (mUsrConfirmPassword.contains(" ")) {
            edtConfirmPassword.setError("No Spaces Allowed");
            return false;
        }

        if (!mUsrConfirmPassword.contentEquals(mUsrPassword)) {
            edtConfirmPassword.setError("Your password is not matched");
            return false;
        }

        return true;
    }

    private void attemptResetPassword() {

        if (isFormValid()) {

            edtOtp.clearFocus();edtPassword.clearFocus();edtConfirmPassword.clearFocus();
            ResetPasswordRequest in=new ResetPasswordRequest();
            in.setOtp(Integer.valueOf(mOtpClient));in.setEmail(mUsrEmail);in.setNewPassword(mUsrPassword);
            mViewModel.attemptResetPassword(in);
        }

    }


    void createNotification() {

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description:"+" OTP is "+ mOtpServer);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);

            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_notification)
                .setTicker("TeleMedDoctor")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Verification notification")
                .setContentText("Your OTP is "+ mOtpServer)
                .setContentInfo("Information");

        notificationManager.notify(1, notificationBuilder.build());
    }

    private EditText.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_otp:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtOtp.isFocused()) edtPassword.requestFocus();
                        return true;
                    }
                    break;


                case R.id.edt_password:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtPassword.isFocused()) edtConfirmPassword.requestFocus();
                        return true;
                    }

                    break;

                case R.id.edt_confirm_password:
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        mFragmentListener.hideSoftKeyboard();
                        attemptResetPassword();
                        return true;
                    }

                    break;
            }

            return false;
        }
    };


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

               switch (msg.what){
                   case 1:
                       createNotification();
                       break;
                   case 2:
                       if (mFragmentListener != null) {
                           mFragmentListener.popTillFragment("SignInFragment", 0);
                       }
                       break;
               }


        }
    };

    @Override
    public void onDestroyView() {
        mHandler.removeMessages(1);
        mHandler.removeMessages(2);
        edtOtp.setOnEditorActionListener(null);
        edtPassword.setOnEditorActionListener(null);
        edtConfirmPassword.setOnEditorActionListener(null);
        super.onDestroyView();
    }
}
