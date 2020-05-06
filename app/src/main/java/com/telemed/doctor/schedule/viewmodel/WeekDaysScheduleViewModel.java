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
import com.telemed.doctor.schedule.model.CreateWeekScheduleResponse;
import com.telemed.doctor.schedule.model.DayScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteWeekDayScheduleRequest;
import com.telemed.doctor.schedule.model.WeekScheduleResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class WeekDaysScheduleViewModel extends AndroidViewModel {
    private final String TAG= WeekDaysScheduleViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<WeekScheduleResponse>> resultantAllSchedule;
    private MutableLiveData<ApiResponse<DayScheduleResponse>> resultantDeleteSchedule;


    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<List<WeekScheduleResponse.ReturnTimeSlot>> lstOfSchedule;

    public WeekDaysScheduleViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();

        resultantAllSchedule= new MutableLiveData<>();
        resultantDeleteSchedule= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        lstOfSchedule=new MutableLiveData<>();


    }



    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public MutableLiveData<ApiResponse<DayScheduleResponse>> getResultantDeleteSchedule() {
        return resultantDeleteSchedule;
    }

    public MutableLiveData<ApiResponse<WeekScheduleResponse>> getResultantAllSchedule() {
        return resultantAllSchedule;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }


    public void fetchWeekSchedules(Map<String, String> map) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.fetchWeekSchedules(map).enqueue(new Callback<WeekScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeekScheduleResponse> call, @NonNull Response<WeekScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    WeekScheduleResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantAllSchedule.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantAllSchedule.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantAllSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<WeekScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAllSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public MutableLiveData<List<WeekScheduleResponse.ReturnTimeSlot>> getAllSchedules() {
        return lstOfSchedule;
    }

    public void setScheduleList(List<WeekScheduleResponse.ReturnTimeSlot> scheduleList) {
        lstOfSchedule.setValue(scheduleList);

    }


    public void deleteWeekDaySchedule(Map<String, String> map, DeleteWeekDayScheduleRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.deleteWeekSchedule(map,in).enqueue(new Callback<DayScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleResponse> call, @NonNull Response<DayScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    DayScheduleResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantDeleteSchedule.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<DayScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }


}
