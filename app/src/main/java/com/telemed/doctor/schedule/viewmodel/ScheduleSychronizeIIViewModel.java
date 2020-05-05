package com.telemed.doctor.schedule.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.DayScheduleAlterationResponse;
import com.telemed.doctor.schedule.model.DayScheduleRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ScheduleSychronizeIIViewModel extends AndroidViewModel {
    private final String TAG = ScheduleSychronizeIIViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;


    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<List<AllMonthSchedule>> lstOfSchedule;


    public ScheduleSychronizeIIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        isLoading = new MutableLiveData<>();
        isViewEnabled = new MutableLiveData<>();
        lstOfSchedule = new MutableLiveData<>();


    }


    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }

    public MutableLiveData<List<AllMonthSchedule>> getAllSchedules() {
        return lstOfSchedule;
    }

    public void setScheduleList(List<AllMonthSchedule> scheduleList) {
        lstOfSchedule.setValue(scheduleList);

    }

}
