package com.telemed.doctor.schedule.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ScheduleSecondMonthViewModel extends AndroidViewModel {
    private final String TAG= ScheduleSecondMonthViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<MonthlyScheduleResponse>> resultantAllSchedule;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<Map<String, String>> headerMap;
    private MutableLiveData<List<AllMonthSchedule>> lstOfSchedule;
    private Call<MonthlyScheduleResponse> liveData1,liveData2,liveData3;

    public ScheduleSecondMonthViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantAllSchedule= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        headerMap=new MutableLiveData<>();
        lstOfSchedule=new MutableLiveData<>();
        //---------------------------


    }



    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<ApiResponse<MonthlyScheduleResponse>> getResultantAllSchedule() {
        return resultantAllSchedule;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }




    public void fetchMonthlySchedules(Map<String, String> map,int monthNo) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.fetchMonthlySchedules(map,monthNo).enqueue(new Callback<MonthlyScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Response<MonthlyScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    MonthlyScheduleResponse result = response.body();
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
            public void onFailure(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAllSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
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