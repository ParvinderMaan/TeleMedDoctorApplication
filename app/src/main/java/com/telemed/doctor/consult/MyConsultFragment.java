package com.telemed.doctor.consult;


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

import com.telemed.doctor.R;
import com.telemed.doctor.consult.model.Appointment;
import com.telemed.doctor.consult.model.AppointmentListResponse;
import com.telemed.doctor.consult.viewmodel.MyConsultViewModel;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.profile.viewmodel.ProfileViewModel;
import com.telemed.doctor.util.CustomAlertTextView;
import com.telemed.doctor.util.DividerItemDecoration;


public class MyConsultFragment extends Fragment {
    private final String TAG=MyConsultFragment.class.getSimpleName();
    private RecyclerView rvAppointmentsUpcoming, rvAppointmentsHistory;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private HomeFragmentSelectedListener mFragmentListener;
    private MyConsultViewModel mViewModel;
    private AppointmentUpcomingAdapter mUpcomingAdapter;
    private ImageButton ibtnClose;

    public static MyConsultFragment newInstance() {
        return new MyConsultFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUpcomingAdapter = new AppointmentUpcomingAdapter();
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
        mViewModel.fetchUpcomingAppointments();

    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        ibtnClose=v.findViewById(R.id.ibtn_close);

        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });
    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getAppointmentList()
                .observe(getViewLifecycleOwner(), lstOfAppointments -> {
                    mUpcomingAdapter.addAll(lstOfAppointments);
                });


        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        AppointmentListResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        if(infoObj.getAppointmentList()!=null && (!infoObj.getAppointmentList().isEmpty())){
                            mViewModel.setAppointmentList(infoObj.getAppointmentList());
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


    private void initUpcomingRecyclerView(View v) {
        rvAppointmentsUpcoming = v.findViewById(R.id.rv_upcoming_appointment);
        rvAppointmentsUpcoming.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(requireActivity());
        rvAppointmentsUpcoming.setLayoutManager(mLinearLayoutManager);
        rvAppointmentsUpcoming.setAdapter(mUpcomingAdapter);
        mUpcomingAdapter.setOnItemClickListener(new AppointmentUpcomingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Appointment model) {

//                if (mFragmentListener != null) {
//                    mFragmentListener.showFragment("VideoCallTriggerFragment", model);
//                }
//
//            for now
              if (mFragmentListener != null) {
                    mFragmentListener.showFragment("VideoCallFragment", model);
                }
            }

            @Override
            public void onItemClickMore(String tag, int pos) {

//
////                UpcomingOptionsBottomSheetFragment mUpcomingOptionsBottomSheetFragment =
////                        UpcomingOptionsBottomSheetFragment.newInstance();
////                mUpcomingOptionsBottomSheetFragment.showNow(getChildFragmentManager(),
////                        mUpcomingOptionsBottomSheetFragment.getTag());  // latest changes

//               switch (tag){
//
//
//                   case "TAG_CHAT":
//                       if(getActivity() !=null) ((HomeActivity)getActivity()).showChatFragment();
//                       break;
//                   case "TAG_GALLERY":
//                       if(getActivity() !=null) ((HomeActivity)getActivity()).showPatientGalleryFragment();
//
//                       break;
//                   case "TAG_MEDICAL_RECORD":
//                       if(getActivity() !=null) ((HomeActivity)getActivity()).showMedicalRecordFragment();
//
//                       break;
//               }

            }
        });

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

        AppointmentHistoryAdapter mAdapter = new AppointmentHistoryAdapter();
        rvAppointmentsHistory.setAdapter(mAdapter);

            Drawable dividerDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.custom_divider_white);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
            rvAppointmentsHistory.addItemDecoration(dividerItemDecoration);


        ViewCompat.setNestedScrollingEnabled(rvAppointmentsHistory, false);

        mAdapter.setOnItemClickListener(new AppointmentHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }

            @Override
            public void onItemMoreClick(int position) {
              //  HistoryOptionsBottomSheetFragment mHistoryOptionsBottomSheetFragment = HistoryOptionsBottomSheetFragment.newInstance();
              //  mHistoryOptionsBottomSheetFragment.showNow(getChildFragmentManager(), mHistoryOptionsBottomSheetFragment.getTag());
            }
        });

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
}


