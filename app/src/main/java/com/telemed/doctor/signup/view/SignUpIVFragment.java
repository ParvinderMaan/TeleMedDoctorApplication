package com.telemed.doctor.signup.view;


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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.signup.model.SignUpIVRequest;
import com.telemed.doctor.signup.model.SignUpIVResponse;
import com.telemed.doctor.signup.viewmodel.SignUpIVViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpIVFragment extends BaseFragment {
    private final String TAG=SignUpIVFragment.class.getSimpleName();
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private RouterFragmentSelectedListener mFragmentListener;
    private Button btnContinue;
    private TextView tvCancel;
    private AppCompatEditText edtRoutingNumber, edtAccountNumber, edtCity, edtPostCode,edtAddr;
    private String mRoutingNumber,mAccountNumber,mCity,mPostCode,mAddress;
    private SignUpIVViewModel mViewModel;
    private LinearLayout llRoot;
    private String mAccessToken;

    public SignUpIVFragment() {
        // Required empty public constructor
    }

    public static SignUpIVFragment newInstance(Object payload) {
        SignUpIVFragment fragment=new SignUpIVFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("KEY_ACCESS_TOKEN", (UserInfoWrapper) payload);
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
        // collect our intent
        if(getArguments()!=null){
            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_ACCESS_TOKEN");
            if (objInfo != null) mAccessToken =objInfo.getAccessToken();

            Log.e(TAG,mAccessToken);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_iv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpIVViewModel.class);
        initView(v);
        initListener(v);

    mViewModel.getProgress()
            .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


    mViewModel.getViewEnabled()
            .observe(getViewLifecycleOwner(), this::resetEnableView);

    mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {
        switch (response.getStatus()) {
            case SUCCESS:
                if (response.getData() != null) {
                    if (mFragmentListener != null) {
//                                SignUpIVResponse.Data data = response.getData().getData(); // adding Additional Info
                        tvAlertView.showTopAlert(response.getData().getMessage());
                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        UserInfoWrapper in = new UserInfoWrapper();
                        in.setAccessToken(mAccessToken);
//                            mFragmentListener.showFragment("SignUpVFragment", in);
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

    private void resetEnableView(Boolean isView) {
        btnContinue.setEnabled(isView);
        tvCancel.setEnabled(isView);
        edtRoutingNumber.setEnabled(isView);
        edtAccountNumber.setEnabled(isView);
        edtCity.setEnabled(isView);
        edtPostCode.setEnabled(isView);

    }

    private void initListener(View v) {

        btnContinue.setOnClickListener(v1 -> {


//                if(!isNetAvail()){
//                    tvAlertView.showTopAlert("No Internet");
//                    return;
//                }

            if(isFormValid()){

                SignUpIVRequest in=new SignUpIVRequest.Builder()
                        .setRoutingNumber(mRoutingNumber)
                        .setAccountNumber(Integer.parseInt(mAccountNumber))
                        .setCity(mCity)
                        .setAddress(mAddress)  // replace it !!!
                        .setPostCode(mPostCode)
                        .build();

                Map<String, String> map = new HashMap<>();
                map.put("content-type", "application/json");
                map.put("Authorization","Bearer "+mAccessToken);

                mViewModel.attemptSignUp(in,map);

            }

//                if (mFragmentListener != null)
//                    mFragmentListener.showFragment("SignUpVFragment", null);

        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFragmentListener != null)
                    mFragmentListener.abortSignUpDialog();

            }
        });


        edtPostCode.setOnEditorActionListener((v12, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (edtPostCode.isFocused()) edtPostCode.clearFocus();
                mFragmentListener.hideSoftKeyboard();
                return true;
            }
            return false;
        });
    }

    private void initView(View v) {
        llRoot = v.findViewById(R.id.ll_root);
        btnContinue = v.findViewById(R.id.btn_continue);
        tvCancel = v.findViewById(R.id.tv_cancel);

        edtRoutingNumber= v.findViewById(R.id.edt_routing_number);
        edtAccountNumber= v.findViewById(R.id.edt_account_number);
        edtCity = v.findViewById(R.id.edt_city);
        edtPostCode = v.findViewById(R.id.edt_post_code);
        edtAddr = v.findViewById(R.id.edt_addr);



        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);


        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private boolean isFormValid() {
        mRoutingNumber=edtRoutingNumber.getText().toString();
        mAccountNumber=edtAccountNumber.getText().toString();
        mCity=edtCity.getText().toString();
        mPostCode=edtPostCode.getText().toString();
        mAddress=edtAddr.getText().toString();



        if (TextUtils.isEmpty(mRoutingNumber)) {
            edtRoutingNumber.setError("Enter Routing number");
            return false;
        }

        if (mRoutingNumber.contains(" ")) {
            edtRoutingNumber.setError("No Spaces Allowed");
            return false;
        }

        if (TextUtils.isEmpty(mAccountNumber)) {
            edtAccountNumber.setError("Enter account number");
            return false;
        }

        if (mAccountNumber.contains(" ")) {
            edtAccountNumber.setError("No Spaces Allowed");
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

//        if (mCity.contains(" ")) {
//            edtCity.setError("No Spaces Allowed");
//            return false;
//        }


        if (TextUtils.isEmpty(mPostCode)) {
            edtPostCode.setError("Enter post code");
            return false;
        }

        if (mPostCode.contains(" ")) {
            edtPostCode.setError("No Spaces Allowed");
            return false;
        }



        return true;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                UserInfoWrapper in = (UserInfoWrapper) msg.obj;
                if(mFragmentListener!=null){
                    mFragmentListener.showFragment("SignUpVFragment", in);
                }
            }



        }
    };

    @Override
    public void onDestroy() {
        mHandler.removeMessages(1);
        super.onDestroy();
    }

    void createNotification(String message) {

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description:"+"  "+ message);
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
                .setContentText("Your error "+ message)
                .setContentInfo("Information");

        notificationManager.notify(1, notificationBuilder.build());
    }


}
