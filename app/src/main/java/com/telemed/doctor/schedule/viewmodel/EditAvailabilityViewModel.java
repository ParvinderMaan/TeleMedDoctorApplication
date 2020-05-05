package com.telemed.doctor.schedule.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.schedule.model.DayAvailabilityModel;
import com.telemed.doctor.schedule.model.DayScheduleAlterationResponse;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.model.DayScheduleResponse;
import com.telemed.doctor.schedule.model.EditDayScheduleRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class EditAvailabilityViewModel extends AndroidViewModel {
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<ApiResponse<DayScheduleResponse>> resultAllDaySchedule;
    private MutableLiveData<ApiResponse<DayScheduleResponse>> resultDeleteSchedule;
    private MutableLiveData<ApiResponse<DayScheduleResponse>> resultEditSchedule;
    private MutableLiveData<List<DayAvailabilityModel>> lstOfAllDaySchedule;
    private MutableLiveData<ApiResponse<DayScheduleAlterationResponse>> resultantDayScheduleCreation;


    public MutableLiveData<ApiResponse<DayScheduleResponse>> getResultEditSchedule() {
        return resultEditSchedule;
    }

    public EditAvailabilityViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantDayScheduleCreation=new MutableLiveData<>();
        resultAllDaySchedule = new MutableLiveData<>();
        resultDeleteSchedule = new MutableLiveData<>();
        resultEditSchedule = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        isViewEnabled = new MutableLiveData<>();
        lstOfAllDaySchedule = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }


    public void fetchDaySchedule(Map<String, String> map, String selectedDate) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.fetchDaySchedule(map,selectedDate).enqueue(new Callback<DayScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleResponse> call, @NonNull Response<DayScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    DayScheduleResponse result = response.body();
                    if (result.getStatus()) {
                        resultAllDaySchedule.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultAllDaySchedule.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultAllDaySchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<DayScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultAllDaySchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });
    }

    public MutableLiveData<ApiResponse<DayScheduleResponse>> getResultDeleteSchedule() {
        return resultDeleteSchedule;
    }

    public void deleteDaySchedule(Map<String, String> map, Integer timeSlotId) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.deleteDaySchedule(map,timeSlotId).enqueue(new Callback<DayScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleResponse> call, @NonNull Response<DayScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

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
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultDeleteSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });
    }


    public void editDaySchedule(Map<String, String> map, EditDayScheduleRequest in,Integer timeSlotId) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.editDaySchedule(map,in,timeSlotId).enqueue(new Callback<DayScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleResponse> call, @NonNull Response<DayScheduleResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    DayScheduleResponse result = response.body();
                    if (result.getStatus()) {
                        resultEditSchedule.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultEditSchedule.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultEditSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<DayScheduleResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultEditSchedule.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });
    }

    public MutableLiveData<ApiResponse<DayScheduleResponse>> getResultAllDaySchedule() {
        return resultAllDaySchedule;
    }

    public MutableLiveData<List<DayAvailabilityModel>> getAllDaySchedule() {
        return lstOfAllDaySchedule;
    }

    public void setAllDayScheduleList(List<DayAvailabilityModel> scheduleList) {
        lstOfAllDaySchedule.setValue(scheduleList);
    }



    public void createDaySchedule(Map<String, String> map, DayScheduleRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.createDaySchedule(map, in,true).enqueue(new Callback<DayScheduleAlterationResponse>() {
            @Override
            public void onResponse(@NonNull Call<DayScheduleAlterationResponse> call, @NonNull Response<DayScheduleAlterationResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body() != null) {
                    DayScheduleAlterationResponse result = response.body();
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



    public MutableLiveData<ApiResponse<DayScheduleAlterationResponse>> getResultantDayScheduleCreation() {
        return resultantDayScheduleCreation;
    }

}
