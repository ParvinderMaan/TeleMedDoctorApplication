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
import com.telemed.doctor.schedule.model.AllWeekSchedule;
import com.telemed.doctor.schedule.model.CreateWeekScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteWeekScheduleResponse;
import com.telemed.doctor.schedule.model.WeekScheduleRequest;
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
    private MutableLiveData<ApiResponse<CreateWeekScheduleResponse>> resultantCreateSchedule;
    private MutableLiveData<ApiResponse<WeekScheduleResponse>> resultantAllSchedule;
    private MutableLiveData<ApiResponse<DeleteWeekScheduleResponse>> resultantDeleteSchedule;


    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<Map<String, String>> headerMap;
    private MutableLiveData<List<AllWeekSchedule>> lstOfSchedule;

    public WeekDaysScheduleViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantCreateSchedule = new MutableLiveData<>();
        resultantAllSchedule= new MutableLiveData<>();
        resultantDeleteSchedule= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        headerMap=new MutableLiveData<>();
        lstOfSchedule=new MutableLiveData<>();


    }

    public void createWeekDaySchedule(Map<String, String> map, WeekScheduleRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.createWeekSchedule(map,in).enqueue(new Callback<CreateWeekScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateWeekScheduleResponse> call, @NonNull Response<CreateWeekScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    CreateWeekScheduleResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantCreateSchedule.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantCreateSchedule.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantCreateSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<CreateWeekScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantCreateSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }


    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public MutableLiveData<ApiResponse<DeleteWeekScheduleResponse>> getResultantDeleteSchedule() {
        return resultantDeleteSchedule;
    }

    public MutableLiveData<ApiResponse<WeekScheduleResponse>> getResultantAllSchedule() {
        return resultantAllSchedule;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }

    public MutableLiveData<ApiResponse<CreateWeekScheduleResponse>> getCreateResultant() {
        return resultantCreateSchedule;
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
                resultantCreateSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public MutableLiveData<List<AllWeekSchedule>> getAllSchedules() {
        return lstOfSchedule;
    }

    public void setScheduleList(List<AllWeekSchedule> scheduleList) {
        lstOfSchedule.setValue(scheduleList);

    }


    public void deleteWeekDaySchedule(Map<String, String> map,int itemIndex) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.deleteWeekSchedule(itemIndex,map).enqueue(new Callback<DeleteWeekScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeleteWeekScheduleResponse> call, @NonNull Response<DeleteWeekScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    DeleteWeekScheduleResponse result = response.body();
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
            public void onFailure(@NonNull Call<DeleteWeekScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }


}
