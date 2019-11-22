package com.telemed.doctor.consult;

import android.content.DialogInterface;
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
    private static String TAG= "";

    public UpcomingOptionsBottomSheetFragment() {
        // Required empty public constructor
    }

    public static UpcomingOptionsBottomSheetFragment newInstance() {
        return new UpcomingOptionsBottomSheetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                TAG="TAG_IMAGE";
                dismiss();
            }
        });
        llVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG="TAG_VIDEO_CALL";
                dismiss();

            }
        });
        llMedicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG="TAG_MEDICAL_RECORD";
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        switch (TAG){
            case "TAG_IMAGE":
                if (getParentFragment() != null) {
                    ((MyConsultFragment) getParentFragment()).showFragment("TAG_IMAGE");
                }
                break;

            case "TAG_VIDEO_CALL":
                  if(getParentFragment()!=null){
                    ((MyConsultFragment)getParentFragment()).showFragment("TAG_VIDEO_CALL");
                }
                break;

            case "TAG_MEDICAL_RECORD":
                if(getParentFragment()!=null){
                    ((MyConsultFragment)getParentFragment()).showFragment("TAG_MEDICAL_RECORD");
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
        llImages.setOnClickListener(null);
        llVideoCall.setOnClickListener(null);
        llMedicalRecord.setOnClickListener(null);
    }
}
