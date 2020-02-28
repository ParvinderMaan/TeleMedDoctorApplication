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
import com.telemed.doctor.schedule.model.DayScheduleAlterationResponse;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ScheduleSychronizeIViewModel extends AndroidViewModel {
    private final String TAG = ScheduleSychronizeIViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<DayScheduleAlterationResponse>> resultantDayScheduleCreation;
    private MutableLiveData<ApiResponse<DayScheduleAlterationResponse>> resultantDayScheduleDeletion;


    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<List<AllMonthSchedule>> lstOfSchedule;

    public MutableLiveData<Boolean> getDeleteDialogVisibility() {
        return isDeleteDialogVisibile;
    }

    private MutableLiveData<Boolean> isDeleteDialogVisibile;




    public ScheduleSychronizeIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantDayScheduleCreation=new MutableLiveData<>();
        resultantDayScheduleDeletion=new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        isViewEnabled = new MutableLiveData<>();
        lstOfSchedule = new MutableLiveData<>();
        isDeleteDialogVisibile= new MutableLiveData<>();

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




    public void createDaySchedule(Map<String, String> map, DayScheduleRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.createDaySchedule(map, in).enqueue(new Callback<DayScheduleAlterationResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleAlterationResponse> call, @NonNull Response<DayScheduleAlterationResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    DayScheduleAlterationResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultantDayScheduleCreation.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultantDayScheduleCreation.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantDayScheduleCreation.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<DayScheduleAlterationResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantDayScheduleCreation.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }


    public void deleteWeekSchedule(Map<String, String> map, DayScheduleRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.createDaySchedule(map, in).enqueue(new Callback<DayScheduleAlterationResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleAlterationResponse> call, @NonNull Response<DayScheduleAlterationResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    DayScheduleAlterationResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultantDayScheduleDeletion.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultantDayScheduleDeletion.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantDayScheduleDeletion.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<DayScheduleAlterationResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantDayScheduleDeletion.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public MutableLiveData<ApiResponse<DayScheduleAlterationResponse>> getResultantDayScheduleCreation() {
        return resultantDayScheduleCreation;
    }

    public MutableLiveData<ApiResponse<DayScheduleAlterationResponse>> getResultantDayScheduleDeletion() {
        return resultantDayScheduleDeletion;
    }


    public void setDeleteDialogVisibility(boolean state) {
        isDeleteDialogVisibile.setValue(state);

    }
}