package com.telemed.doctor.medicalrecord.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.telemed.doctor.medicalrecord.MedicalDetailAdapter;
import com.telemed.doctor.medicalrecord.model.MedicalDetailResponse;
import com.telemed.doctor.medicalrecord.viewmodel.MedicalDetailViewModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;


public class MedicalDetailFragment extends Fragment {
    private ImageButton ibtnClose, ibtnBack;
    private HomeFragmentSelectedListener mFragmentListener;
    private String patientId;
    private MedicalDetailViewModel mViewModel;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private RecyclerView rvMedicalDetail;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;
    private TextView tvEmptyView,tvHeaderTitle;
    private MedicalDetailAdapter mAdapter;
    private String mPatientId;
    private String mType;

    public MedicalDetailFragment() {
        // Required empty public constructor
    }

    public static MedicalDetailFragment newInstance(Object payload) {
        MedicalDetailFragment fragment = new MedicalDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (UserInfoWrapper) payload);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //collect our intent
        //collect our intent
        if (getArguments() != null) {
            UserInfoWrapper out = getArguments().getParcelable("KEY_");
            if (out != null) mPatientId = out.getPatientId();
            if (out != null) mType = out.getType();

        }
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
        mAdapter = new MedicalDetailAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_medical_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MedicalDetailViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initRecyclerView(v);
        initObserver();

        mViewModel.fetchMedicalDetails(mHeaderMap,mPatientId,mType);
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

        tvEmptyView=v.findViewById(R.id.tv_empty_view);

        tvHeaderTitle=v.findViewById(R.id.tv_header);
    /*
    enum HistoryTypes
    {
        Past = 1,
        Surgical,
        Allergy,
        Family
    }
*/
       switch (mType){
           case "1":
               tvHeaderTitle.setText("Past History");
               break;

           case "2":
               tvHeaderTitle.setText("Surgical History");
               break;

           case "3":
               tvHeaderTitle.setText("Allergy");
               break;

           case "4":
               tvHeaderTitle.setText("Family History");
               break;
       }
    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getResultMedicalDetail().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MedicalDetailResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getMedicalHistory() != null) {
                            mViewModel.setMedicalHistoryList(infoObj.getMedicalHistory());
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


        mViewModel.getMedicalHistory().observe(getViewLifecycleOwner(), lstOfMedicalDetails -> {

            if(lstOfMedicalDetails.isEmpty()){
                rvMedicalDetail.setVisibility(View.GONE);
                tvEmptyView.setVisibility(View.VISIBLE);
            } else{
                rvMedicalDetail.setVisibility(View.VISIBLE);
                tvEmptyView.setVisibility(View.GONE);
                mAdapter.addAll(lstOfMedicalDetails);
            }
        });
    }

    private void initRecyclerView(View v) {
        rvMedicalDetail = v.findViewById(R.id.rv_medical_detail);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(requireActivity());
        rvMedicalDetail.setLayoutManager(mLinearLayoutManager);
        rvMedicalDetail.setAdapter(mAdapter);

    }
}
