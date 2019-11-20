package com.telemed.doctor.consult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.telemed.doctor.R;

public class UpcomingOptionsBottomSheetFragment extends BottomSheetDialogFragment {

    private LinearLayout llImages, llVideoCall, llMedicalRecord;


    public UpcomingOptionsBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_sheet_upcoming_option, container, false);

}

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);


        llImages=v.findViewById(R.id.ll_images);
        llVideoCall=v.findViewById(R.id.ll_video_call);
        llMedicalRecord=v.findViewById(R.id.ll_medical_record);


        llImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getParentFragment()!=null){
                    ((MyConsultFragment)getParentFragment()).showFragment("TAG_IMAGE");
                }

            }
        });
        llVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragment()!=null){
                    ((MyConsultFragment)getParentFragment()).showFragment("TAG_VIDEO_CALL");
                }


            }
        });
        llMedicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getParentFragment()!=null){

                    ((MyConsultFragment)getParentFragment()).showFragment("TAG_MEDICAL_RECORD");
                }

            }
        });

    }

}
