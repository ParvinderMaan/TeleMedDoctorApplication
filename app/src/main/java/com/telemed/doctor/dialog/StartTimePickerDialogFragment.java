package com.telemed.doctor.dialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.telemed.doctor.R;
import java.util.HashMap;



public class StartTimePickerDialogFragment extends BottomSheetDialogFragment {
    private StartTimePickerDialogFragmentListener mDialogListener;
    private TextView tvTitle,tvTime,tvDone;
    private ImageButton ibtnDecr,ibtnIncr;
    private static String TAG="";
    private HashMap<Integer, String> map;
    private int keyIndex=0;

    @NonNull
    public static StartTimePickerDialogFragment newInstance() {
        return new StartTimePickerDialogFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = view.findViewById(R.id.tv_title);
        tvTime = view.findViewById(R.id.tv_time);
        ibtnDecr = view.findViewById(R.id.ibtn_decr);
        ibtnIncr = view.findViewById(R.id.ibtn_incr);


        tvDone = view.findViewById(R.id.tv_ok);


        tvTitle.setText("Starting Time");

        tvTime.setText(map.get(keyIndex));

        ibtnDecr.setOnClickListener(v->{

            if(keyIndex>0)
            tvTime.setText(map.get(--keyIndex));

        });
        ibtnIncr.setOnClickListener(v->{
            if(keyIndex<map.size()-1)
            tvTime.setText(map.get(++keyIndex));
        });


        tvDone.setOnClickListener(v->{
            TAG="DONE";
            dismiss();

        });



    }
    public void setOnStartTimePickerDialogFragmentListener(StartTimePickerDialogFragmentListener listener) {
        mDialogListener=  listener;
    }


    public interface StartTimePickerDialogFragmentListener {
        void onClick(String value, int key);

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(mDialogListener!=null && TAG.equals("DONE")){
            mDialogListener.onClick(map.get(keyIndex),keyIndex);
        }

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
        map.put(48,"24:00");

       return map;
    }

//    @Override
//    public int getTheme() {
//        return R.style.MyAlertDialogTheme;
//    }
}