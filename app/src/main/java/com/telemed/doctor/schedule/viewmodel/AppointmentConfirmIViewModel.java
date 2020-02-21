package com.telemed.doctor.schedule.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.appointment.model.AppointmentProcessRequest;
import com.telemed.doctor.appointment.model.AppointmentProcessResponse;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.schedule.model.PatientDetailResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class AppointmentConfirmIViewModel extends AndroidViewModel {
    private final String TAG= AppointmentConfirmIViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<PatientDetailResponse>> resultantPatientDetail;
    private MutableLiveData<ApiResponse<AppointmentProcessResponse>> resultantAppointmentConfirm;
    private MutableLiveData<ApiResponse<AppointmentProcessResponse>> resultantAppointmentDeny;
    private MutableLiveData<ApiResponse<AppointmentProcessResponse>> resultantAppointmentCancel;

    private MutableLiveData<Boolean> isLoading;

    public MutableLiveData<ApiResponse<AppointmentProcessResponse>> getResultantAppointmentConfirm() {
        return resultantAppointmentConfirm;
    }

    public MutableLiveData<ApiResponse<AppointmentProcessResponse>> getResultantAppointmentDeny() {
        return resultantAppointmentDeny;
    }

    public MutableLiveData<ApiResponse<AppointmentProcessResponse>> getResultantAppointmentCancel() {
        return resultantAppointmentCancel;
    }

    private MutableLiveData<Boolean> isViewEnabled;

    public AppointmentConfirmIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantPatientDetail= new MutableLiveData<>();
        resultantAppointmentConfirm=new MutableLiveData<>();
        resultantAppointmentDeny=new MutableLiveData<>();
        resultantAppointmentCancel=new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();


    }



    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<ApiResponse<PatientDetailResponse>> getResultantPatientDetail() {
        return resultantPatientDetail;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }




    public void fetchPatientDetail(Map<String, String> map, String  patientId) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.fetchPatientDetail(map,patientId).enqueue(new Callback<PatientDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PatientDetailResponse> call, @NonNull Response<PatientDetailResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    PatientDetailResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantPatientDetail.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantPatientDetail.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantPatientDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<PatientDetailResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantPatientDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }


    public void confirmAppointment(HashMap<String, String> mHeaderMap, AppointmentProcessRequest in) {

        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.processAppointmentRequest(mHeaderMap,in).enqueue(new Callback<AppointmentProcessResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentProcessResponse> call, @NonNull Response<AppointmentProcessResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    AppointmentProcessResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantAppointmentConfirm.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantAppointmentConfirm.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantAppointmentConfirm.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<AppointmentProcessResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAppointmentConfirm.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public void denyAppointment(HashMap<String, String> mHeaderMap, AppointmentProcessRequest in) {

        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.processAppointmentRequest(mHeaderMap,in).enqueue(new Callback<AppointmentProcessResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentProcessResponse> call, @NonNull Response<AppointmentProcessResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    AppointmentProcessResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantAppointmentDeny.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantAppointmentDeny.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantAppointmentDeny.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<AppointmentProcessResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAppointmentDeny.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }
    public void cancelAppointment(HashMap<String, String> mHeaderMap, AppointmentProcessRequest in) {

        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.processAppointmentRequest(mHeaderMap,in).enqueue(new Callback<AppointmentProcessResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentProcessResponse> call, @NonNull Response<AppointmentProcessResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    AppointmentProcessResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantAppointmentCancel.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantAppointmentCancel.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantAppointmentCancel.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<AppointmentProcessResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAppointmentCancel.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

}
