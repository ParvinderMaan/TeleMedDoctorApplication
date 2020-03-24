package com.telemed.doctor.schedule.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.annotation.AppointmentStatus;
import com.telemed.doctor.appointment.model.AppointmentProcessRequest;
import com.telemed.doctor.appointment.model.AppointmentProcessResponse;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.network.WebUrl;
import com.telemed.doctor.schedule.model.PatientDetailResponse;
import com.telemed.doctor.schedule.model.PatientProfileInfo;
import com.telemed.doctor.schedule.model.TimeSlotModel;
import com.telemed.doctor.schedule.viewmodel.AppointmentConfirmIViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentConfirmIFragment extends Fragment {
    private final String TAG = DayWiseAvailabilityFragment.class.getSimpleName();
    private HomeFragmentSelectedListener mFragmentListener;
    private TextView tvPatientName, tvAddress, tvReviewCount, tvAge, tvGender, tvHeight,
            tvWeight, tvStartTime, tvConfirmAppointment, tvDenyAppointment, tvCancelAppointment;
    private ImageButton ibtnClose,ibtnBack;
    private Button btnMedicalRecord;
    private AppointmentConfirmIViewModel mViewModel;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private CircleImageView civProfilePic;
    private TimeSlotModel mTimeSlotModel;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;

    public static AppointmentConfirmIFragment newInstance(Object payload) {
        AppointmentConfirmIFragment fragment = new AppointmentConfirmIFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (TimeSlotModel) payload); // SignUpIResponse.Data
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // collect our intent
        if (getArguments() != null) {
            mTimeSlotModel = getArguments().getParcelable("KEY_");
             Log.e(TAG, mTimeSlotModel.toString());
        }

        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_appointment_confirm_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AppointmentConfirmIViewModel.class);

        initView(v);
        initListener();
        initObserver();

        tvStartTime.setText(mTimeSlotModel != null && mTimeSlotModel.getSlotFrom() != null ? mTimeSlotModel.getSlotFrom() : "");
        mViewModel.fetchPatientDetail(mHeaderMap, mTimeSlotModel.getPatientId());
        Log.e(TAG, mTimeSlotModel.toString());

        if(mTimeSlotModel.getAppointmentStatus()==3){
            tvConfirmAppointment.setVisibility(View.GONE);
            tvDenyAppointment.setVisibility(View.GONE);
            tvCancelAppointment.setVisibility(View.VISIBLE);

        }else if(mTimeSlotModel.getAppointmentStatus()==2){
            tvConfirmAppointment.setVisibility(View.VISIBLE);
            tvDenyAppointment.setVisibility(View.VISIBLE);
            tvCancelAppointment.setVisibility(View.GONE);
        }else {

        }
    }



    private void initListener() {
        tvConfirmAppointment.setOnClickListener(v1 -> {
            AppointmentProcessRequest in = new AppointmentProcessRequest();
            in.setAppointmentId(mTimeSlotModel.getAppointmentId());
            in.setAppointmentStatus(AppointmentStatus.CONFIRM);
            mViewModel.confirmAppointment(mHeaderMap,in);

        });

        tvDenyAppointment.setOnClickListener(v1 -> {
            AppointmentProcessRequest in = new AppointmentProcessRequest();
            in.setAppointmentId(mTimeSlotModel.getAppointmentId());
            in.setAppointmentStatus(AppointmentStatus.DENY);
            mViewModel.denyAppointment(mHeaderMap,in);
        });

        tvCancelAppointment.setOnClickListener(v1 -> {
            AppointmentProcessRequest in = new AppointmentProcessRequest();
            in.setAppointmentId(mTimeSlotModel.getAppointmentId());
            in.setAppointmentStatus(AppointmentStatus.CANCEL);
            mViewModel.cancelAppointment(mHeaderMap,in);

        });

        btnMedicalRecord.setOnClickListener(v1 -> {
            if (mFragmentListener != null) {
                mFragmentListener.showFragment("MedicalRecordFragment", mTimeSlotModel.getPatientId());
            }
        });



        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment",0);
        });

        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });
    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        ibtnClose = v.findViewById(R.id.ibtn_close);
        ibtnBack=v.findViewById(R.id.ibtn_back);

        tvConfirmAppointment = v.findViewById(R.id.tv_confirm);
        btnMedicalRecord = v.findViewById(R.id.btn_medical_record);
        tvPatientName = v.findViewById(R.id.tv_patient_name);
        tvAddress = v.findViewById(R.id.tv_address);
        tvReviewCount = v.findViewById(R.id.tv_review_count);
        tvAge = v.findViewById(R.id.tv_age);
        tvGender = v.findViewById(R.id.tv_gender);
        tvHeight = v.findViewById(R.id.tv_height);
        tvWeight = v.findViewById(R.id.tv_weight);
        tvStartTime = v.findViewById(R.id.tv_start_time);
        civProfilePic = v.findViewById(R.id.civ_profile_pic);
        tvConfirmAppointment = v.findViewById(R.id.tv_confirm_appointment);
        tvDenyAppointment = v.findViewById(R.id.tv_deny_appointment);
        tvCancelAppointment = v.findViewById(R.id.tv_cancel_appointment);


    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultantPatientDetail().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        PatientDetailResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getProfileInfo() != null) {
                            setView(infoObj.getProfileInfo());
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


        mViewModel.getResultantAppointmentConfirm().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        AppointmentProcessResponse.Data infoObj = response.getData().getData();
                        if (response.getData().getMessage() != null) {
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            // ohi... cancel appear
                            tvConfirmAppointment.setVisibility(View.GONE);
                            tvCancelAppointment.setVisibility(View.VISIBLE);
                            tvDenyAppointment.setVisibility(View.GONE);
                            if(mFragmentListener!=null){
                                mFragmentListener.refreshFragment("DayWiseAvailabilityFragment");
                            }
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

        mViewModel.getResultantAppointmentDeny().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        AppointmentProcessResponse.Data infoObj = response.getData().getData();
                        if (response.getData().getMessage() != null) {
                        //    tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        //    tvAlertView.showTopAlert(response.getData().getMessage());
                            if (mFragmentListener != null) {
                                mFragmentListener.refreshFragment("DayWiseAvailabilityFragment");
                                mFragmentListener.popTopMostFragment();
                            }
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


        mViewModel.getResultantAppointmentCancel().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        AppointmentProcessResponse.Data infoObj = response.getData().getData();
                        if (response.getData().getMessage() != null) {
                       //     tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                       //     tvAlertView.showTopAlert(response.getData().getMessage());
                            if (mFragmentListener != null) {
                                mFragmentListener.refreshFragment("DayWiseAvailabilityFragment");
                                mFragmentListener.popTopMostFragment();
                            }
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

    private void setView(PatientProfileInfo info) {
        tvPatientName.setText(info.getFirstName() != null && info.getLastName() != null ? info.getFirstName() + " " + info.getLastName() : "");
        tvAddress.setText(info.getStateName() != null && info.getCountryName() != null ? info.getStateName() + "," + info.getCountryName() : "");
        tvReviewCount.setText(info.getRating() != null ? "" + info.getRating() : "-");
        tvAge.setText(info.getAge() != null ? info.getAge() : "");
        tvGender.setText(info.getGender() != null ? info.getGender() : "");
        tvHeight.setText(info.getHeight() != null ? "" + info.getHeight() : "");
        tvWeight.setText(info.getWeight() != null ? "" + info.getWeight() : "");
//      tvStartTime.setText();
        Picasso.get().load(info.getProfilePic() != null && !info.getProfilePic().isEmpty() ? WebUrl.IMAGE_URL + info.getProfilePic() : "www.example.com")
                .placeholder(R.drawable.img_avatar)
                .error(R.drawable.img_avatar)
                .fit()
                .centerCrop()
                .into(civProfilePic);


    }

    private void resetEnableView(Boolean isView) {



    }


}
