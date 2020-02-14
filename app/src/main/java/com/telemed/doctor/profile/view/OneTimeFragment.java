package com.telemed.doctor.profile.view;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseTextWatcher;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.model.VerficationRequest;
import com.telemed.doctor.password.view.OneTimePasswordFragment;
import com.telemed.doctor.password.viewmodel.OneTimePasswordViewModel;
import com.telemed.doctor.profile.model.ChangeEmailRequest;
import com.telemed.doctor.profile.model.OTPRequest;
import com.telemed.doctor.profile.viewmodel.OneTimeViewModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;


public class OneTimeFragment extends Fragment {

    private final String TAG = OneTimeFragment.class.getSimpleName();
    private HomeFragmentSelectedListener mFragmentListener;

    private OneTimeViewModel mViewModel;

    private AppCompatEditText edtOtpOne, edtOtpTwo, edtOtpThree, edtOtpFour;
    private AppCompatTextView tvCancel,tvUserEmail,tvResendCode;
    private LinearLayout llRoot;
    private CustomAlertTextView tvAlertView;
    private AppCompatButton btnContinue;
    private ProgressBar progressBar;
    private Integer mOtpClient,mOtpServer;
    private String mEmail,mOldPassword;

    public OneTimeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OneTimeFragment newInstance(Object payload) {
        OneTimeFragment fragment = new OneTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (UserInfoWrapper) payload); // SignUpIResponse.Data
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_");
            if (objInfo != null) mEmail=objInfo.getEmail();
            if (objInfo != null) mOldPassword=objInfo.getOldPassword();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeTwo);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OneTimeViewModel.class);

        initView(v);
        initListener();
        initObserver();

        tvUserEmail.setText(mEmail!=null?mEmail:"");
        if(mOtpServer!=null) mHandler.sendEmptyMessageDelayed(1, 2000);  // fake call
        Log.e(TAG,""+ mOtpServer);

    }

    private void initView(View v) {
        llRoot= v.findViewById(R.id.ll_root);
        edtOtpOne = v.findViewById(R.id.edt_otp_one);
        edtOtpTwo = v.findViewById(R.id.edt_otp_two);
        edtOtpThree = v.findViewById(R.id.edt_otp_three);
        edtOtpFour = v.findViewById(R.id.edt_otp_four);
        btnContinue=v.findViewById(R.id.btn_continue);
        tvCancel=v.findViewById(R.id.tv_cancel);
        tvUserEmail=v.findViewById(R.id.tv_user_email);
        tvResendCode=v.findViewById(R.id.tv_resend_code);

        progressBar=v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorBlue), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView = v.findViewById(R.id.tv_alert_view);

    }


    private void initObserver() {

        mViewModel.getResultantVerifyUser().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//                            objInfo.setEmail(mEmail);
                            if (mFragmentListener != null)
                                mFragmentListener.popTopMostFragment();
                                mFragmentListener.showFragment("ProfileFragment", null);
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

        mViewModel.getResultantResendOtp().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        mOtpServer =response.getData().getData().getOtpCode();
                        createNotification(); // fake it
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
                    edtOtpOne.setEnabled(isView);
                    edtOtpTwo.setEnabled(isView);
                    edtOtpThree.setEnabled(isView);
                    edtOtpFour.setEnabled(isView);
                    btnContinue.setEnabled(isView);
                    tvUserEmail.setEnabled(isView);
                    tvResendCode.setEnabled(isView);
                });

    }

    private void initListener() {
        btnContinue.setOnClickListener(mClickListener);
        tvCancel.setOnClickListener(mClickListener);
        tvResendCode.setOnClickListener(mClickListener);

        edtOtpOne.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count == 1) edtOtpTwo.requestFocus();

            }
        });

        edtOtpTwo.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count == 1) edtOtpThree.requestFocus();
                if(count==0) edtOtpOne.requestFocus();
            }
        });

        edtOtpThree.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count == 1) edtOtpFour.requestFocus();
                if(count==0) edtOtpTwo.requestFocus();
            }
        });

        edtOtpFour.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(int start, int before, int count,CharSequence s) {
                if(count==0) edtOtpThree.requestFocus();
            }
        });

        edtOtpFour.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO){
              //  mFragmentListener.hideSoftKeyboard();
                attemptVerfication();

            }
            return false;
        });



    }

    private View.OnClickListener mClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_continue:
                    clearFocus();
                    attemptVerfication();
                    break;

                case R.id.tv_cancel:
                    if(mFragmentListener!=null) {
                        clearFocus();
                        if (mFragmentListener != null)
                            mFragmentListener.popTopMostFragment();
                    }
                    break;

                case R.id.tv_resend_code:
                    clearFocus();
                    edtOtpOne.setText("");edtOtpTwo.setText("");edtOtpThree.setText("");edtOtpFour.setText("");
                    ChangeEmailRequest in = new ChangeEmailRequest.Builder()
                            .setnewEmail(mEmail)
                            .setcurrentPassword(mOldPassword)
                            .build();
                    mViewModel.setChangeaEmailInfo(in);
                    mViewModel.attemptChangeEmail();
                    break;

            }


        }
    };

    public void clearFocus(){
        edtOtpOne.clearFocus();
        edtOtpTwo.clearFocus();
        edtOtpThree.clearFocus();
        edtOtpFour.clearFocus();
    }

    private boolean isFormValid() {

        String a=edtOtpOne.getText().toString();
        String b=edtOtpTwo.getText().toString();
        String c=edtOtpThree.getText().toString();
        String d=edtOtpFour.getText().toString();


        if(TextUtils.isEmpty(a) && TextUtils.isEmpty(b) && TextUtils.isEmpty(c) && TextUtils.isEmpty(d) ){
            tvAlertView.showTopAlert("Enter the OTP");
            return false;
        }
        // please dont do it above !!!
        mOtpClient =Integer.parseInt(a+b+c+d);
        return true;




    }

    private void attemptVerfication() {


        if(isFormValid()){
            OTPRequest in=new OTPRequest.Builder()
                    .setOtp(mOtpClient)
                    .build();
            edtOtpOne.clearFocus();  edtOtpTwo.clearFocus();  edtOtpThree.clearFocus();   edtOtpFour.clearFocus();
            mViewModel.attemptResetEmail(in);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            createNotification();
        }
    };

    private void createNotification() {

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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
