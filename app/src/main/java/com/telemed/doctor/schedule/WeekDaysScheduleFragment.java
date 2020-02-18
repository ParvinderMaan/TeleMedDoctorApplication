package com.telemed.doctor.schedule;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telemed.doctor.R;
import com.telemed.doctor.helper.DateFormatter;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.signup.model.UserInfoWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekDaysScheduleFragment extends Fragment {
    private WeekDaysScheduleViewModel mViewModel;
    private RecyclerView rvSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private FloatingActionButton fbtnAddSchedule;

    public static WeekDaysScheduleFragment newInstance(Object payload) {
        WeekDaysScheduleFragment fragment = new WeekDaysScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (UserInfoWrapper) payload); // SignUpIResponse.Data
        fragment.setArguments(bundle);
        return fragment;

    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_week_days_schedule, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WeekDaysScheduleViewModel.class);
        initRecyclerView(v);

        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {
            if(mFragmentListener!=null) mFragmentListener.popTopMostFragment();
        });


        fbtnAddSchedule=v.findViewById(R.id.fbtn_add_schedule);
        fbtnAddSchedule.setOnClickListener(v1 -> {

           addScheduleAlert();

        });



    }

    private void initRecyclerView(View v) {
        rvSchedule=v.findViewById(R.id.rv_schedule);
        WeekDayScheduleAdapter mAdapter = new WeekDayScheduleAdapter();
        mAdapter.setOnItemClickListener(position -> {
          //  if(mFragmentListener!=null) mFragmentListener.showFragment("AppointmentConfirmIFragment",null);

        });
        mAdapter.setItems(fetchWeekDaysSchedule());
        rvSchedule.setHasFixedSize(true);
        rvSchedule.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvSchedule.setAdapter(mAdapter);




    }

    private List<RecurringScheduleModel> fetchWeekDaysSchedule() {
        List<RecurringScheduleModel> lstOfAvailableSlot=new ArrayList<>();
        // String id, int status, String firmName, String timeSlot, String patientName
//        lstOfAvailableSlot.add(new RecurringScheduleModel("1",0,"Infinity Doc","8:00 / 8:30","John Ali"));
//        lstOfAvailableSlot.add(new RecurringScheduleModel("2",0,"Infinity Doc","8:30 / 9:00","Mic Laki"));
//        lstOfAvailableSlot.add(new RecurringScheduleModel("3",1,"Infinity Doc","9:00 / 9:30","Ahil Ali"));
//        lstOfAvailableSlot.add(new RecurringScheduleModel("4",1,"Infinity Doc","10:00 / 10:30","John Ali"));
//        lstOfAvailableSlot.add(new RecurringScheduleModel("5",2,"Infinity Doc","10:30 / 11:00","John Ali"));
//        lstOfAvailableSlot.add(new RecurringScheduleModel("5",2,"Infinity Doc","10:30 / 11:00","John Ali"));
        return lstOfAvailableSlot;

    }


    private void addScheduleAlert() {

        final Dialog dialog = new Dialog(requireActivity(), R.style.MyAlertDialogTheme);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setContentView(R.layout.dialog_add_schedule);

        TextView tvDone = (TextView) dialog.findViewById(R.id.tv_done);
        TextView tvSelectAll = (TextView) dialog.findViewById(R.id.tv_select_all);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        tvDone.setOnClickListener(view -> {
            dialog.dismiss();
            getFromTime();
        });
        tvSelectAll.setOnClickListener(view -> {
        });

        dialog.show();
    }

    private void getFromTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(),R.style.PickerStyle, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String fromTime = selectedHour + ":" + selectedMinute;
                Log.e("From: ", fromTime);
                getToTime();

            }
        }, hour, minute, true);//Yes 24 hour time
//      mTimePicker.setTitle("From");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        mTimePicker.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setText("Starting Time :");
        mTimePicker.show();
    }

    private void getToTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(requireActivity(),R.style.PickerStyle, (timePicker, selectedHour, selectedMinute) -> {
            String to = selectedHour + ":" + selectedMinute;
            Log.e("To: ", to);
           // setUpRecyclerView();


        }, hour, minute, true); // Yes 24 hour time
        //      mTimePicker.setTitle("To");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        mTimePicker.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setText("Ending Time :");
        mTimePicker.show();


    }

}
