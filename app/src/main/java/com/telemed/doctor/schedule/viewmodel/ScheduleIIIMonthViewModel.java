package com.telemed.doctor.schedule.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.schedule.model.AllMonthSchedule;

import java.util.List;

public class ScheduleIIIMonthViewModel extends AndroidViewModel {
    private MutableLiveData<List<AllMonthSchedule>> lstOfSchedule;

    public ScheduleIIIMonthViewModel(@NonNull Application application) {
        super(application);
        lstOfSchedule=new MutableLiveData<>();
    }

    public MutableLiveData<List<AllMonthSchedule>> getAllSchedules() {
        return lstOfSchedule;
    }

    public void setScheduleList(List<AllMonthSchedule> scheduleList) {
        lstOfSchedule.setValue(scheduleList);

    }
}