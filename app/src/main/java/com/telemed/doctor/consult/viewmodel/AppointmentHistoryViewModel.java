package com.telemed.doctor.consult.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.consult.model.AppointmentRequest;
import com.telemed.doctor.consult.model.PastAppointment;
import com.telemed.doctor.consult.model.PastAppointmentResponse;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class AppointmentHistoryViewModel extends AndroidViewModel {
    private final String TAG= AppointmentHistoryViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final MutableLiveData<List<PastAppointment>> lstOfPastAppointment;



    public MutableLiveData<List<PastAppointment>> getPastAppointments() {
        return lstOfPastAppointment;
    }

    private MutableLiveData<Boolean> isLoading,isViewEnabled,isListLoading,isLastPage;;
    private MutableLiveData<ApiResponse<PastAppointmentResponse>> resultPastAppointment,resultPastNextAppointment;





    public MutableLiveData<ApiResponse<PastAppointmentResponse>> getResultPastAppointment() {
        return resultPastAppointment;
    }

    public MutableLiveData<ApiResponse<PastAppointmentResponse>> getResultPastNextAppointment() {
        return resultPastNextAppointment;
    }

    public MutableLiveData<Boolean> getListLoadingStatus() {
        return isListLoading;
    }

    public AppointmentHistoryViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        isListLoading=new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        resultPastAppointment=new MutableLiveData<>();
        resultPastNextAppointment=new MutableLiveData<>();
        isViewEnabled=new MutableLiveData<>();
        lstOfPastAppointment=new MutableLiveData<>();
        isLastPage=new MutableLiveData<>();

    }

    public MutableLiveData<Boolean> isLastPage() {
        return isLastPage;
    }

    public void isLastPage(Boolean isLastPage) {
        this.isLastPage.setValue(isLastPage);
    }


    public void fetchPastAppointments(HashMap<String, String> headerMap, AppointmentRequest in) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchPastAppointment(headerMap,in).enqueue(new Callback<PastAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<PastAppointmentResponse> call, @NonNull Response<PastAppointmentResponse> response) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    PastAppointmentResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultPastAppointment.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultPastAppointment.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultPastAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PastAppointmentResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultPastAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public void fetchPastNextAppointments(HashMap<String, String> headerMap, AppointmentRequest in) {
        mWebService.fetchPastAppointment(headerMap,in).enqueue(new Callback<PastAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<PastAppointmentResponse> call, @NonNull Response<PastAppointmentResponse> response) {
                isListLoading.setValue(false);
                if (response.isSuccessful() && response.body()!=null) {
                    PastAppointmentResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultPastNextAppointment.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultPastNextAppointment.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultPastNextAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PastAppointmentResponse> call, @NonNull Throwable error) {
                isListLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultPastNextAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }




    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }
    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }


    public void setPastAppointmentList(List<PastAppointment> appointmentList) {
        lstOfPastAppointment.setValue(appointmentList);
    }

    public void setListLoading(Boolean isListLoading) {
        this.isListLoading.setValue(isListLoading);
    }
}