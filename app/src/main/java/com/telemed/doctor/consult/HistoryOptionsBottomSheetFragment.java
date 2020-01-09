package com.telemed.doctor.consult;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.telemed.doctor.R;

/**
 * A simple {@link BottomSheetDialogFragment} subclass.
 */
public class HistoryOptionsBottomSheetFragment extends BottomSheetDialogFragment {
    private static String TAG= "";
    private LinearLayout llRateAppointment,llPrescribeAppointment;

    public HistoryOptionsBottomSheetFragment() {
        // Required empty public constructor
    }

    public static HistoryOptionsBottomSheetFragment newInstance() {
        return new HistoryOptionsBottomSheetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_bottom_sheet_history_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);

    }

    private void initView(View v) {
        llRateAppointment=v.findViewById(R.id.ll_rate_the_appointment);
        llPrescribeAppointment=v.findViewById(R.id.ll_precribe_the_appointment);

        llRateAppointment.setOnClickListener(v12 -> {
            TAG="TAG_RATE_APPOINTMENT";
            dismiss();

        });


        llPrescribeAppointment.setOnClickListener(v1 -> {
            TAG="TAG_PRESCRIBE_PATIENT";
            dismiss();

        });


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        switch (TAG){
            case "TAG_RATE_APPOINTMENT":

                if(getParentFragment()!=null){
                    ((MyConsultFragment)getParentFragment()).showFragment("TAG_RATE_APPOINTMENT");
                }
                break;

            case "TAG_PRESCRIBE_PATIENT":

                if(getParentFragment()!=null){
                    ((MyConsultFragment)getParentFragment()).showFragment("TAG_PRESCRIBE_PATIENT");
                }

                break;

        }
    }

    @Override
    public void onDestroyView() {
        releaseResource();
        super.onDestroyView();
    }

    private void releaseResource() {
        llRateAppointment.setOnClickListener(null);
        llPrescribeAppointment.setOnClickListener(null);
    }

}
