package com.telemed.doctor.schedule;


import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.telemed.doctor.R;
import com.telemed.doctor.helper.DateFormatter;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyScheduleDemoFragment extends Fragment {


    private LinearLayout llMySchedule;
    private TextView tvMoreInfo;
    private GridLayout glWeekDay;
//    private TextView tvSelectWeekDay,tvSelectTiming;

    public static MyScheduleDemoFragment newInstance() {
      return new MyScheduleDemoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_schedule_demo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        llMySchedule=v.findViewById(R.id.ll_my_schedule);
        llMySchedule.setVisibility(View.GONE);



//        tvSelectWeekDay=v.findViewById(R.id.tv_select_week_day);
//        tvSelectTiming=v.findViewById(R.id.tv_select_timing);

//        tvSelectWeekDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        tvSelectTiming.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });



    }


    void getFromTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(),R.style.PickerStyle, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String fromTime = DateFormatter.changeTimeFormat12Hour(selectedHour + ":" + selectedMinute);
                Log.e("From: ", fromTime);
//                tvStartTime.setText(fromTime);


            }
        }, hour, minute, false);//Yes 24 hour time
//      mTimePicker.setTitle("From");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        mTimePicker.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setText("Start Timing :");
        mTimePicker.show();
    }


    void getToTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(),R.style.PickerStyle, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String toTime = DateFormatter.changeTimeFormat12Hour(selectedHour + ":" + selectedMinute);
                Log.e("To: ", "" +toTime);
//               setUpRecyclerView(); ------------------------------------------------------------------>>>>
//                tvEndTime.setText(toTime);

            }
        }, hour, minute, false);//Yes 24 hour time
//      mTimePicker.setTitle("To");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        mTimePicker.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setText("Off Timing :");
        mTimePicker.show();
    }


}
