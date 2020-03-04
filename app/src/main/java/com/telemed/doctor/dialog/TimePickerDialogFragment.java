package com.telemed.doctor.dialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.telemed.doctor.R;

import java.util.HashMap;

public class TimePickerDialogFragment extends BottomSheetDialogFragment {
    private TimePickerDialogFragment.TimePickerDialogFragmentListener mDialogListener;
    private TextView tvTitle,tvTime,tvDone;
    private TextView tvTitle_,tvTime_,tvDone_;
    private ImageButton ibtnDecr,ibtnIncr;
    private ImageButton ibtnDecr_,ibtnIncr_;
    private static String TAG="";
    private HashMap<Integer, String> map;
    private int keyIndex=0, keyIndex_,lowerBound;
    private ViewFlipper viewFlipper;

    @NonNull
    public static TimePickerDialogFragment newInstance() {
        return new TimePickerDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        map=getTimeIntervals();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_time_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        viewFlipper = v.findViewById(R.id.view_flipper);
        viewFlipper.setFlipInterval(3000);


        tvTitle = v.findViewById(R.id.tv_title);
        tvTime = v.findViewById(R.id.tv_time);
        ibtnDecr = v.findViewById(R.id.ibtn_decr);
        ibtnIncr = v.findViewById(R.id.ibtn_incr);

        tvTitle_ = v.findViewById(R.id.tv_title_);
        tvTime_ = v.findViewById(R.id.tv_time_);
        ibtnDecr_ = v.findViewById(R.id.ibtn_decr_);
        ibtnIncr_ = v.findViewById(R.id.ibtn_incr_);


        tvDone = v.findViewById(R.id.tv_ok);

        tvDone_ = v.findViewById(R.id.tv_ok_);


        tvTitle.setText("Starting Time");
        tvTitle_.setText("Ending Time");

        tvTime.setText(map.get(keyIndex));


        ibtnDecr.setOnClickListener(vv->{

            if(keyIndex>0)
                tvTime.setText(map.get(--keyIndex));

        });
        ibtnIncr.setOnClickListener(vv->{
            if(keyIndex<map.size()-1)
                tvTime.setText(map.get(++keyIndex));
        });


        ibtnDecr_.setOnClickListener(vv->{

            if(keyIndex_>lowerBound+1)
                tvTime_.setText(map.get(--keyIndex_));
        });
        ibtnIncr_.setOnClickListener(vv->{
            if(keyIndex_<map.size()-1)
                tvTime_.setText(map.get(++keyIndex_));
        });


        tvDone.setOnClickListener(vv->{
            tvTime_.setText(map.get(++keyIndex));
            keyIndex_=lowerBound=keyIndex;

            viewFlipper.setInAnimation(requireActivity(), android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(requireActivity(), android.R.anim.slide_out_right);
            viewFlipper.showNext();
           // viewFlipper.showPrevious();

        });

        tvDone_.setOnClickListener(vv->{
            TAG="DONE";
            dismiss();

        });





    }
    public void setOnTimePickerDialogFragmentListener(TimePickerDialogFragment.TimePickerDialogFragmentListener listener) {
        mDialogListener=  listener;
    }


    public interface TimePickerDialogFragmentListener {
        void onClick(String startTime, String endTime);

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

        if(mDialogListener!=null && TAG.equals("DONE")){
            TAG="";
            mDialogListener.onClick(map.get(keyIndex),map.get(keyIndex_));
        }
        super.onDismiss(dialog);

    }
    @SuppressLint("UseSparseArrays")
    public HashMap<Integer,String> getTimeIntervals() {
        HashMap<Integer,String> map=new HashMap<Integer, String>();
        map.put(0,"00:00");
        map.put(1,"00:30");
        map.put(2,"01:00");
        map.put(3,"01:30");
        map.put(4,"02:00");
        map.put(5,"02:30");
        map.put(6,"03:00");
        map.put(7,"03:30");
        map.put(8,"04:00");
        map.put(9,"04:30");
        map.put(10,"05:00");
        map.put(11,"05:30");
        map.put(12,"06:00");
        map.put(13,"06:30");
        map.put(14,"07:00");
        map.put(15,"07:30");
        map.put(16,"08:00");
        map.put(17,"08:30");
        map.put(18,"09:00");
        map.put(19,"09:30");
        map.put(20,"10:00");
        map.put(21,"10:30");
        map.put(22,"11:00");
        map.put(23,"11:30");
        map.put(24,"12:00");
        map.put(25,"12:30");
        map.put(26,"13:00");
        map.put(27,"13:30");
        map.put(28,"14:00");
        map.put(29,"14:30");
        map.put(30,"15:00");
        map.put(31,"15:30");
        map.put(32,"16:00");
        map.put(33,"16:30");
        map.put(34,"17:00");
        map.put(35,"17:30");
        map.put(36,"18:00");
        map.put(37,"18:30");
        map.put(38,"19:00");
        map.put(39,"19:30");
        map.put(40,"20:00");
        map.put(41,"20:30");
        map.put(42,"21:00");
        map.put(43,"21:30");
        map.put(44,"22:00");
        map.put(45,"22:30");
        map.put(46,"23:00");
        map.put(47,"23:30");
//      map.put(48,"24:00");

        return map;
    }

//    @Override
//    public int getTheme() {
//        return R.style.MyAlertDialogTheme;
//    }
}