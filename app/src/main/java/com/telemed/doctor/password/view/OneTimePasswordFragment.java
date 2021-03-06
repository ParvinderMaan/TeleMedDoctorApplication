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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
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
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.base.BaseTextWatcher;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.model.VerficationRequest;
import com.telemed.doctor.password.viewmodel.OneTimePasswordViewModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;


public class OneTimePasswordFragment extends BaseFragment {
    private final String TAG = OneTimePasswordFragment.class.getSimpleName();
    private AppCompatEditText edtOtpOne, edtOtpTwo, edtOtpThree, edtOtpFour;
    private AppCompatTextView tvCancel,tvUserEmail,tvResendCode;
    private LinearLayout llRoot;
    private CustomAlertTextView tvAlertView;

    private RouterFragmentSelectedListener mFragmentListener;
    private AppCompatButton btnContinue;
    private OneTimePasswordViewModel mViewModel;
    private ProgressBar progressBar;
    private Integer mOtpClient,mOtpServer;
    private String mEmail,mAccessToken;


    public OneTimePasswordFragment() {
        // Required empty public constructor
    }

    public static OneTimePasswordFragment newInstance(Object payload) {
        OneTimePasswordFragment fragment=new OneTimePasswordFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("KEY_", (UserInfoWrapper) payload); // SignUpIResponse.Data
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
            if (objInfo != null) mEmail=objInfo.getEmail();
            if (objInfo != null) mAccessToken=objInfo.getAccessToken();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeTwo);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_one_time_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OneTimePasswordViewModel.class);

        initView(v);
        initListener();
        initObserver();

        tvUserEmail.setText(mEmail!=null?mEmail:"");
        if(mOtpServer!=null) mHandler.sendEmptyMessageDelayed(1, 2000);  // fake call
        Log.e(TAG,""+ mOtpServer);


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
                            UserInfoWrapper in=new UserInfoWrapper();
                            in.setEmail(mEmail);in.setAccessToken(mAccessToken);
                            mFragmentListener.showFragment("SignUpIIFragment",in);
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
                mFragmentListener.hideSoftKeyboard();
                attemptVerfication();

            }
            return false;
        });



    }

    private void attemptVerfication() {


        if(isFormValid()){
            VerficationRequest in=new VerficationRequest.Builder()
                    .setEmail(mEmail)
                    .setOtp(mOtpClient)
                    .build();

            edtOtpOne.clearFocus();  edtOtpTwo.clearFocus();  edtOtpThree.clearFocus();   edtOtpFour.clearFocus();
            mViewModel.attemptVerifyUser(in);
        }
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
                       mFragmentListener.abortSignUpDialog();
                   }
                       break;

               case R.id.tv_resend_code:
                   clearFocus();
                   edtOtpOne.setText("");edtOtpTwo.setText("");edtOtpThree.setText("");edtOtpFour.setText("");
                   mViewModel.attemptResendOtp(mEmail);
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

    @Override
    public void onDestroyView() {
        releaseResource();
        super.onDestroyView();

    }

    private void releaseResource() {
        btnContinue.setOnClickListener(null);
        tvCancel.setOnClickListener(null);
        edtOtpOne.addTextChangedListener(null);
        edtOtpTwo.addTextChangedListener(null);
        edtOtpThree.addTextChangedListener(null);
        edtOtpFour.addTextChangedListener(null);
        edtOtpFour.setOnEditorActionListener(null);
        mHandler.removeMessages(1);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            createNotification();
        }
    };

     private void createNotification() {

        NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
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
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(requireActivity(), NOTIFICATION_CHANNEL_ID);
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



    /*
    {
  "status": true,
  "message": "You are verified successfully!"
    }
     */


    /*
    {
  "status": false,
  "message": "OTP entered is not correct!"
}
     */
    //---------------------- 2 api ....
    /*

    {"status":true,"message":"You are verified successfully!"}
     */
/*

{"status":false,"message":"OTP entered is not correct!"}
 */
}
