package com.telemed.doctor.consult.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
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
import com.telemed.doctor.consult.model.AppointmentRequest;
import com.telemed.doctor.consult.model.PastAppointment;
import com.telemed.doctor.consult.model.PastAppointmentResponse;
import com.telemed.doctor.consult.model.UpcomingAppointment;
import com.telemed.doctor.consult.model.UpcomingAppointmentResponse;
import com.telemed.doctor.consult.viewmodel.MyConsultViewModel;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.util.CustomAlertTextView;
import com.telemed.doctor.util.DividerItemDecoration;
import com.telemed.doctor.videocall.model.VideoCallDetail;
import com.telemed.doctor.videocall.model.VideoCallDetailResponse;

import java.util.HashMap;


public class MyConsultFragment extends Fragment {
    private final String TAG=MyConsultFragment.class.getSimpleName();
    private RecyclerView rvAppointmentsUpcoming, rvAppointmentsHistory;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private HomeFragmentSelectedListener mFragmentListener;
    private MyConsultViewModel mViewModel;
    private AppointmentUpcomingAdapter mUpComingAppointmentAdapter;
    private ImageButton ibtnClose;
    private TextView tvLastAppointmentTitle,tvUpcomingAppointmentTitle;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private AppointmentHistoryAdapter mPastAppointmentAdapter;
    private View viewUpcomingAppointmentTitle,viewLastAppointmentTitle;
    private TextView tvEmptyView;

    public static MyConsultFragment newInstance() {
        return new MyConsultFragment();
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
        mHeaderMap.put("Authorization","Bearer "+mAccessToken);
        mUpComingAppointmentAdapter = new AppointmentUpcomingAdapter();
        mPastAppointmentAdapter = new AppointmentHistoryAdapter();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_consult, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyConsultViewModel.class);
        initView(v);
        initUpcomingRecyclerView(v);
        initHistoryAppointmentRecyclerView(v);
        initObserver();

        AppointmentRequest in=new AppointmentRequest();
        in.setPageNumber(1);
        in.setPageSize(3);
        in.setSearchQuery("");  // no need there
        in.setFilterBy(""); // no need there
        mViewModel.fetchUpcomingAppointments(mHeaderMap,in);
        mViewModel.fetchPastAppointments(mHeaderMap,in);
        
    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        ibtnClose=v.findViewById(R.id.ibtn_close);

        tvEmptyView=v.findViewById(R.id.tv_empty_view);


        tvLastAppointmentTitle = v.findViewById(R.id.tv_last_appointment_title);
        tvUpcomingAppointmentTitle = v.findViewById(R.id.tv_upcoming_appointment_title);
        tvLastAppointmentTitle.setVisibility(View.GONE);
        tvUpcomingAppointmentTitle.setVisibility(View.GONE);

        viewUpcomingAppointmentTitle=v.findViewById(R.id.view_upcoming_appointment_title);
        viewLastAppointmentTitle=v.findViewById(R.id.view_last_appointment_title);
        viewUpcomingAppointmentTitle.setVisibility(View.GONE);
        viewLastAppointmentTitle.setVisibility(View.GONE);

        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        tvLastAppointmentTitle.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.showFragment("AppointmentHistoryFragment",null);

        });


        tvUpcomingAppointmentTitle.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.showFragment("AppointmentUpcomingFragment",null);

        });



    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getUpComingAppointments()
                .observe(getViewLifecycleOwner(), lstOfAppointments -> {
                    if(lstOfAppointments.isEmpty()) {
                        viewUpcomingAppointmentTitle.setVisibility(View.GONE);
                        tvUpcomingAppointmentTitle.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    }else {
                        viewUpcomingAppointmentTitle.setVisibility(View.VISIBLE);
                        tvUpcomingAppointmentTitle.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }
                    mUpComingAppointmentAdapter.clearAll();
                    mUpComingAppointmentAdapter.addAll(lstOfAppointments);

                });


        mViewModel.getPastAppointments()
                .observe(getViewLifecycleOwner(), lstOfAppointments -> {
                    if(lstOfAppointments.isEmpty()){
                        tvLastAppointmentTitle.setVisibility(View.GONE);
                        viewLastAppointmentTitle.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);
                    } else{
                        tvLastAppointmentTitle.setVisibility(View.VISIBLE);
                        viewLastAppointmentTitle.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }
                    mPastAppointmentAdapter.clearAll();
                    mPastAppointmentAdapter.addAll(lstOfAppointments);
                });


        mViewModel.getResultUpComingAppointment().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        UpcomingAppointmentResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        if(infoObj.getDataList()!=null ){
                            mViewModel.setUpComingAppointmentList(infoObj.getDataList());
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


        mViewModel.getResultPastAppointment().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        PastAppointmentResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        if(infoObj.getDataList()!=null){
                            mViewModel.setPastAppointmentList(infoObj.getDataList());
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



        mViewModel.getResultVideoCallDetail()
                .observe(getViewLifecycleOwner(), response -> {
                    switch (response.getStatus()) {
                        case SUCCESS:
                            if (response.getData() != null) {
                                VideoCallDetailResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                                if(infoObj!=null && infoObj.getMediaValue()!=null ){

                                    if(mFragmentListener!=null){
                                        VideoCallDetail value =infoObj.getMediaValue();
                                        value.setOpenTokApiKey(infoObj.getOpenTokApiKey());
                                        mFragmentListener.showFragment("VideoCallFragment",value);
                                    }
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


    private void initUpcomingRecyclerView(View v) {
        rvAppointmentsUpcoming = v.findViewById(R.id.rv_upcoming_appointment);
        rvAppointmentsUpcoming.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(requireActivity());
        rvAppointmentsUpcoming.setLayoutManager(mLinearLayoutManager);
        rvAppointmentsUpcoming.setAdapter(mUpComingAppointmentAdapter);
        mUpComingAppointmentAdapter.setOnItemClickListener(mUpcomOnItemClickListener);

        Drawable dividerDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.custom_divider_white);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvAppointmentsUpcoming.addItemDecoration(dividerItemDecoration);
        ViewCompat.setNestedScrollingEnabled(rvAppointmentsUpcoming, false);
    }

    private void initHistoryAppointmentRecyclerView(View v) {
        rvAppointmentsHistory = v.findViewById(R.id.rv_last_appointment);
        rvAppointmentsHistory.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(requireActivity());
        rvAppointmentsHistory.setLayoutManager(mLinearLayoutManager);
        rvAppointmentsHistory.setAdapter(mPastAppointmentAdapter);
        mPastAppointmentAdapter.setOnItemClickListener(mHisOnItemClickListener);

        Drawable dividerDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.custom_divider_white);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvAppointmentsHistory.addItemDecoration(dividerItemDecoration);
        ViewCompat.setNestedScrollingEnabled(rvAppointmentsHistory, false);
    }


    public void showFragment(String tag) {
 //------------------------------------------------------------------------------------
        switch (tag) {
            case "TAG_IMAGE":
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("PatientGalleryFragment", null);
                break;

            case "TAG_VIDEO_CALL":
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("VideoCallTriggerFragment", null);
                break;

            case "TAG_MEDICAL_RECORD":
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("MedicalRecordFragment", null);
                break;

            case "TAG_RATE_APPOINTMENT":
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("PatientRatingFragment", null);
                break;

            case "TAG_PRESCRIBE_PATIENT":
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("AppointmentSummaryFragment", null);
                break;

        }

//------------------------------------------------------------------------------------

    }


private AppointmentUpcomingAdapter.OnItemClickListener mUpcomOnItemClickListener=new AppointmentUpcomingAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, UpcomingAppointment model) {
            mViewModel.fetchVideoCallDetail(mHeaderMap,model.getAppointmentId());
/*
                if (mFragmentListener != null) {
                    mFragmentListener.showFragment("VideoCallTriggerFragment", model);
                }

            for now
              if (mFragmentListener != null) {
                    mFragmentListener.showFragment("VideoCallFragment", model);
                }
*/

        }

        @Override
        public void onItemClickMedicalRecord(UpcomingAppointment model, int pos) {
            if (mFragmentListener != null) {
                mFragmentListener.showFragment("MedicalRecordFragment", model);
            }
        }
    };

private AppointmentHistoryAdapter.OnItemClickListener mHisOnItemClickListener=new AppointmentHistoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, PastAppointment model) {

        }

        @Override
        public void onItemClickMore(String tag, int pos) {

        }
    };
}


