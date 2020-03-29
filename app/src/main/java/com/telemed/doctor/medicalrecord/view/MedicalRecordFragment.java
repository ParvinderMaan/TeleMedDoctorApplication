package com.telemed.doctor.medicalrecord.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.medicalrecord.model.MedicalHistory;
import com.telemed.doctor.medicalrecord.model.MedicalRecordResponse;
import com.telemed.doctor.medicalrecord.viewmodel.MedicalRecordViewModel;
import com.telemed.doctor.schedule.model.PatientDetailResponse;
import com.telemed.doctor.schedule.model.PatientProfileInfo;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;

public class MedicalRecordFragment extends Fragment {
    private ImageButton ibtnClose, ibtnBack;
    private HomeFragmentSelectedListener mFragmentListener;
    private TextView tvPatientName, tvAge, tvGender, tvHeight, tvWeight;
    private TextView tvPastMedicalHistory, tvSurgicalMedicalHistory, tvAllergyHistory, tvFamilyHistory;
    private MedicalRecordViewModel mViewModel;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;
    private String mPatientId;
    private AppCompatCheckBox cboxSmokingI, cboxSmokingII, cboxSmokingIII, cboxAlcoholI,
            cboxAlcoholII, cboxAlcoholIII, cboxDrugI, cboxDrugII;

    public static MedicalRecordFragment newInstance(Object payload) {
        MedicalRecordFragment fragment = new MedicalRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("KEY_", (String) payload); // SignUpIResponse.Data
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
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
        // collect our intent
        if (getArguments() != null) {
            mPatientId = getArguments().getString("KEY_");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_medical_record, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MedicalRecordViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initObserver();
        mViewModel.fetchPatientDetail(mHeaderMap, mPatientId);
        mViewModel.fetchMedicalRecord(mHeaderMap, mPatientId);
    }
 /*
    enum HistoryTypes
    {
        Past = 1,
        Surgical,
        Allergy,
        Family
    }
*/
    private void initListener() {
        tvPastMedicalHistory.setOnClickListener(v -> {
            UserInfoWrapper in=new UserInfoWrapper();
            in.setPatientId(mPatientId);
            in.setType("1");
            if(mFragmentListener!=null)
                mFragmentListener.showFragment("MedicalDetailFragment",in);
        });

        tvSurgicalMedicalHistory.setOnClickListener(v -> {
            UserInfoWrapper in=new UserInfoWrapper();
            in.setPatientId(mPatientId);
            in.setType("2");
            if(mFragmentListener!=null)
                mFragmentListener.showFragment("MedicalDetailFragment",in);
        });

        tvAllergyHistory.setOnClickListener(v -> {
            UserInfoWrapper in=new UserInfoWrapper();
            in.setPatientId(mPatientId);
            in.setType("3");
            if(mFragmentListener!=null)
                mFragmentListener.showFragment("MedicalDetailFragment",in);

        });

        tvFamilyHistory.setOnClickListener(v -> {
            UserInfoWrapper in=new UserInfoWrapper();
            in.setPatientId(mPatientId);
            in.setType("4");
            if(mFragmentListener!=null)
                mFragmentListener.showFragment("MedicalDetailFragment",in);
        });



    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


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



        mViewModel.getResultMedicalRecord().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MedicalRecordResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        if (infoObj.getHistory() != null) {
                            // mViewModel.setUpComingAppointmentList(infoObj.getDataList());
                            updateUi(infoObj.getHistory());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;

            }

        });


    }

    private void updateUi(MedicalHistory history) {

        switch (history.getSmoking()) {

            case "1":
                cboxSmokingI.setChecked(true);
                break;
            case "2":
                cboxSmokingII.setChecked(true);
                break;
            case "3":
                cboxSmokingIII.setChecked(true);
                break;

        }

        switch (history.getAlcohol()) {

            case "1":
                cboxAlcoholI.setChecked(true);
                break;
            case "2":
                cboxAlcoholII.setChecked(true);
                break;
            case "3":
                cboxAlcoholIII.setChecked(true);
                break;

        }


        switch (history.getDrugUse()) {
            case "1":
                cboxDrugI.setChecked(true);
                break;
            case "2":
                cboxDrugII.setChecked(true);
                break;
        }


    }


    private void initView(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);

        progressBar.setVisibility(View.INVISIBLE);


        ibtnClose = v.findViewById(R.id.ibtn_close);

        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment", 0);
        });

        ibtnBack = v.findViewById(R.id.ibtn_back);
        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });


        tvPatientName = v.findViewById(R.id.tv_patient_name);
        tvAge = v.findViewById(R.id.tv_age);
        tvGender = v.findViewById(R.id.tv_gender);
        tvHeight = v.findViewById(R.id.tv_height);
        tvWeight = v.findViewById(R.id.tv_weight);


        cboxSmokingI = v.findViewById(R.id.cbox_smoking_i);
        cboxSmokingII = v.findViewById(R.id.cbox_smoking_ii);
        cboxSmokingIII = v.findViewById(R.id.cbox_smoking_iii);
        cboxAlcoholI = v.findViewById(R.id.cbox__alcohol_i);
        cboxAlcoholII = v.findViewById(R.id.cbox_alcohol_ii);
        cboxAlcoholIII = v.findViewById(R.id.cbox_alcohol_iii);
        cboxDrugI = v.findViewById(R.id.cbox_drug_i);
        cboxDrugII = v.findViewById(R.id.cbox_drug_ii);


        tvPastMedicalHistory = v.findViewById(R.id.tv_past_medical_history);
        tvSurgicalMedicalHistory = v.findViewById(R.id.tv_surgical_medical_history);
        tvAllergyHistory = v.findViewById(R.id.tv_allergy_history);
        tvFamilyHistory = v.findViewById(R.id.tv_family_history);


    }



    private void setView(PatientProfileInfo info) {
        tvPatientName.setText(info.getFirstName() != null && info.getLastName() != null ? info.getFirstName() + " " + info.getLastName() : "");
        tvAge.setText(info.getAge() != null ? info.getAge() : "");
        tvGender.setText(info.getGender() != null ? info.getGender() : "");
        tvHeight.setText(info.getHeight() != null ? "" + info.getHeight() : "");
        tvWeight.setText(info.getWeight() != null ? "" + info.getWeight() : "");
    }
}
