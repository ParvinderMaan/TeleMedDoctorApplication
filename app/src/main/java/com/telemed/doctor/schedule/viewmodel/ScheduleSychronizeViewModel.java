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
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ScheduleSychronizeViewModel extends AndroidViewModel {
    private final String TAG = ScheduleSychronizeViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<MonthlyScheduleResponse>> resultAllScheduleIMonth;
    private MutableLiveData<ApiResponse<MonthlyScheduleResponse>> resultAllScheduleIIMonth;
    private MutableLiveData<ApiResponse<MonthlyScheduleResponse>> resultAllScheduleIIIMonth;

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<List<AllMonthSchedule>> lstOfSchedule;


    public ScheduleSychronizeViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultAllScheduleIMonth = new MutableLiveData<>();
        resultAllScheduleIIMonth = new MutableLiveData<>();
        resultAllScheduleIIIMonth = new MutableLiveData<>();
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


    public void fetchFirstMonthSchedule(Map<String, String> map, int month, int year) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.fetchMonthlySchedules(map, month, year).enqueue(new Callback<MonthlyScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Response<MonthlyScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    MonthlyScheduleResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultAllScheduleIMonth.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultAllScheduleIMonth.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultAllScheduleIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultAllScheduleIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public void fetchSecondMonthSchedule(Map<String, String> map, int month, int year) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.fetchMonthlySchedules(map, month, year).enqueue(new Callback<MonthlyScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Response<MonthlyScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    MonthlyScheduleResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultAllScheduleIIMonth.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultAllScheduleIIMonth.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultAllScheduleIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultAllScheduleIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });
    }


    public void fetchThirdMonthSchedule(Map<String, String> map, int month, int year) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.fetchMonthlySchedules(map, month, year).enqueue(new Callback<MonthlyScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Response<MonthlyScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    MonthlyScheduleResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultAllScheduleIIIMonth.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultAllScheduleIIIMonth.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultAllScheduleIIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultAllScheduleIIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

}


    public MutableLiveData<List<AllMonthSchedule>> getAllSchedules() {
        return lstOfSchedule;
    }

    public void setScheduleList(List<AllMonthSchedule> scheduleList) {
        lstOfSchedule.setValue(scheduleList);

    }


    public MutableLiveData<ApiResponse<MonthlyScheduleResponse>> getResultAllScheduleIMonth() {
        return resultAllScheduleIMonth;
    }


    public MutableLiveData<ApiResponse<MonthlyScheduleResponse>> getResultAllScheduleIIMonth() {
        return resultAllScheduleIIMonth;
    }

    public MutableLiveData<ApiResponse<MonthlyScheduleResponse>> getResultAllScheduleIIIMonth() {
        return resultAllScheduleIIIMonth;
    }


}
