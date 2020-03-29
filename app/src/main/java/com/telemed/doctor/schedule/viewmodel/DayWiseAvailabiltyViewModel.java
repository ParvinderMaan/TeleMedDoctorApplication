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
import com.telemed.doctor.schedule.model.ScheduleTimeSlotResponse;
import com.telemed.doctor.schedule.model.TimeSlotModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class DayWiseAvailabiltyViewModel extends AndroidViewModel {
    private final String TAG= DayWiseAvailabiltyViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<ScheduleTimeSlotResponse>> resultantTimeSlot;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<List<TimeSlotModel>> lstOfTimeSlot;

    public DayWiseAvailabiltyViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantTimeSlot= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        lstOfTimeSlot =new MutableLiveData<>();

    }



    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<ApiResponse<ScheduleTimeSlotResponse>> getResultantTimeSlot() {
        return resultantTimeSlot;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }




    public void fetchScheduleTimeSlots(Map<String, String> map, String  date) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.fetchScheduleTimeSlots(map,date).enqueue(new Callback<ScheduleTimeSlotResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleTimeSlotResponse> call, @NonNull Response<ScheduleTimeSlotResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    ScheduleTimeSlotResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantTimeSlot.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantTimeSlot.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantTimeSlot.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<ScheduleTimeSlotResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantTimeSlot.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public MutableLiveData<List<TimeSlotModel>> getAllTimeSlot() {
        return lstOfTimeSlot;
    }

    public void setTimeSlotList(List<TimeSlotModel> scheduleList) {
        lstOfTimeSlot.setValue(scheduleList);

    }


}
