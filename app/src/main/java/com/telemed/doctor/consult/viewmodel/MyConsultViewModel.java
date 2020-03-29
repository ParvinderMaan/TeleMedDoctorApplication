package com.telemed.doctor.consult.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.consult.model.AppointmentRequest;
import com.telemed.doctor.consult.model.PastAppointment;
import com.telemed.doctor.consult.model.PastAppointmentResponse;
import com.telemed.doctor.consult.model.UpcomingAppointment;
import com.telemed.doctor.consult.model.UpcomingAppointmentResponse;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.videocall.model.VideoCallDetailResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class MyConsultViewModel extends AndroidViewModel {
    private final String TAG=MyConsultViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final MutableLiveData<List<UpcomingAppointment>> lstOfUpComingAppointment;
    private final MutableLiveData<List<PastAppointment>> lstOfPastAppointment;

    public MutableLiveData<List<UpcomingAppointment>> getUpComingAppointments() {
        return lstOfUpComingAppointment;
    }

    public MutableLiveData<List<PastAppointment>> getPastAppointments() {
        return lstOfPastAppointment;
    }

    private MutableLiveData<Boolean> isLoading,isViewEnabled;
    private MutableLiveData<ApiResponse<UpcomingAppointmentResponse>> resultUpComingAppointment;
    private MutableLiveData<ApiResponse<PastAppointmentResponse>> resultPastAppointment;
    private MutableLiveData<ApiResponse<VideoCallDetailResponse>> resultVideoCallDetail;
    private MediatorLiveData<Boolean> resultEmptyView;


    public MutableLiveData<ApiResponse<VideoCallDetailResponse>> getResultVideoCallDetail() {
        return resultVideoCallDetail;
    }

    public MutableLiveData<ApiResponse<UpcomingAppointmentResponse>> getResultUpComingAppointment() {
        return resultUpComingAppointment;
    }

    public MutableLiveData<ApiResponse<PastAppointmentResponse>> getResultPastAppointment() {
        return resultPastAppointment;
    }



    public MyConsultViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();

        isLoading=new MutableLiveData<>();
        resultUpComingAppointment=new MutableLiveData<>();
        resultPastAppointment=new MutableLiveData<>();
        isViewEnabled=new MutableLiveData<>();
        lstOfUpComingAppointment=new MutableLiveData<>();
        lstOfPastAppointment=new MutableLiveData<>();
        resultVideoCallDetail=new MutableLiveData<>();

    }


    public void fetchUpcomingAppointments(HashMap<String, String> headerMap, AppointmentRequest in) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchUpcomingAppointment(headerMap,in).enqueue(new Callback<UpcomingAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingAppointmentResponse> call, @NonNull Response<UpcomingAppointmentResponse> response) {
                isLoading.setValue(false);
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
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultUpComingAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public void fetchPastAppointments(HashMap<String, String> headerMap, AppointmentRequest in) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchPastAppointment(headerMap,in).enqueue(new Callback<PastAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<PastAppointmentResponse> call, @NonNull Response<PastAppointmentResponse> response) {
                isLoading.setValue(false);
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
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultPastAppointment.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
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

    public void setPastAppointmentList(List<PastAppointment> appointmentList) {
        lstOfPastAppointment.setValue(appointmentList);
    }



    public void fetchVideoCallDetail(HashMap<String, String> headerMap, Integer appointmentId) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchVideoCallDetail(headerMap,appointmentId).enqueue(new Callback<VideoCallDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoCallDetailResponse> call, @NonNull Response<VideoCallDetailResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    VideoCallDetailResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultVideoCallDetail.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultVideoCallDetail.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultVideoCallDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoCallDetailResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultVideoCallDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }






}
