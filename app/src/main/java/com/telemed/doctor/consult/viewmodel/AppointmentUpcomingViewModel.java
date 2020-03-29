package com.telemed.doctor.consult.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.consult.model.AppointmentRequest;
import com.telemed.doctor.consult.model.UpcomingAppointment;
import com.telemed.doctor.consult.model.UpcomingAppointmentResponse;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class AppointmentUpcomingViewModel extends AndroidViewModel {
    private final String TAG= AppointmentUpcomingViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;

    public void setListLoading(Boolean isListLoading) {
        this.isListLoading.setValue(isListLoading);
    }

    private final MutableLiveData<List<UpcomingAppointment>> lstOfUpComingAppointment;

    public MutableLiveData<List<UpcomingAppointment>> getUpComingAppointments() {
        return lstOfUpComingAppointment;
    }

    public MutableLiveData<ApiResponse<UpcomingAppointmentResponse>> getResultUpComingNextAppointment() {
        return resultUpComingNextAppointment;
    }

    public MutableLiveData<Boolean> isLastPage() {
        return isLastPage;
    }

    public void isLastPage(Boolean isLastPage) {
        this.isLastPage.setValue(isLastPage);
    }

    private MutableLiveData<Boolean> isLoading,isViewEnabled,isListLoading,isLastPage;
    private MutableLiveData<ApiResponse<UpcomingAppointmentResponse>> resultUpComingAppointment;
    private MutableLiveData<ApiResponse<UpcomingAppointmentResponse>> resultUpComingNextAppointment;



    public MutableLiveData<ApiResponse<UpcomingAppointmentResponse>> getResultUpComingAppointment() {
        return resultUpComingAppointment;
    }

    public MutableLiveData<Boolean> getListLoadingStatus() {
        return isListLoading;
    }



    public AppointmentUpcomingViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        isLoading=new MutableLiveData<>();
        isListLoading=new MutableLiveData<>();
        resultUpComingAppointment=new MutableLiveData<>();
        resultUpComingNextAppointment=new MutableLiveData<>();
        isViewEnabled=new MutableLiveData<>();
        lstOfUpComingAppointment=new MutableLiveData<>();
        isLastPage=new MutableLiveData<>();
    }


    public void fetchUpcomingAppointments(HashMap<String, String> headerMap, AppointmentRequest in) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchUpcomingAppointment(headerMap,in).enqueue(new Callback<UpcomingAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingAppointmentResponse> call, @NonNull Response<UpcomingAppointmentResponse> response) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    UpcomingAppointmentResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultUpComingAppointment.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultUpComingAppointment.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultUpComingAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingAppointmentResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultUpComingAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public void fetchUpcomingNextAppointments(HashMap<String, String> headerMap, AppointmentRequest in) {
        mWebService.fetchUpcomingAppointment(headerMap,in).enqueue(new Callback<UpcomingAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingAppointmentResponse> call, @NonNull Response<UpcomingAppointmentResponse> response) {
                isListLoading.setValue(false);
                if (response.isSuccessful() && response.body()!=null) {
                    UpcomingAppointmentResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultUpComingNextAppointment.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultUpComingNextAppointment.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultUpComingNextAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingAppointmentResponse> call, @NonNull Throwable error) {
                isListLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultUpComingNextAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }
    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }


    public void setUpComingAppointmentList(List<UpcomingAppointment> appointmentList) {
        lstOfUpComingAppointment.setValue(appointmentList);

    }




}