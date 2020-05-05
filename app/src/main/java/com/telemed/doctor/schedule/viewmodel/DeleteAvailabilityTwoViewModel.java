package com.telemed.doctor.schedule.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.DayScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteScheduleRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class DeleteAvailabilityTwoViewModel extends AndroidViewModel {
    private MutableLiveData<ApiResponse<DayScheduleResponse>> resultDeleteSchedule;
    private final WebService mWebService;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<AllMonthSchedule>> lstOfSchedule;

    public DeleteAvailabilityTwoViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultDeleteSchedule = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        lstOfSchedule= new MutableLiveData<>();
    }

    public MutableLiveData<ApiResponse<DayScheduleResponse>> getResultDeleteSchedule() {
        return resultDeleteSchedule;
    }

    public void deleteDatesSchedule(Map<String, String> map, DeleteScheduleRequest in) {
        this.isLoading.setValue(true);
        mWebService.deleteDatesSchedule(map,in).enqueue(new Callback<DayScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleResponse> call, @NonNull Response<DayScheduleResponse> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    DayScheduleResponse result = response.body();
                    if (result.getStatus()) {
                        resultDeleteSchedule.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<DayScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });
    }

    public MutableLiveData<List<AllMonthSchedule>> getAllSchedules() {
        return lstOfSchedule;
    }

    public void setScheduleList(List<AllMonthSchedule> scheduleList) {
        lstOfSchedule.setValue(scheduleList);
    }
}

