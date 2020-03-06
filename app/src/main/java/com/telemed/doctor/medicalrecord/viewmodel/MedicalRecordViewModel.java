package com.telemed.doctor.medicalrecord.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.consult.model.AppointmentRequest;
import com.telemed.doctor.consult.model.UpcomingAppointment;
import com.telemed.doctor.consult.model.UpcomingAppointmentResponse;
import com.telemed.doctor.consult.viewmodel.AppointmentUpcomingViewModel;
import com.telemed.doctor.medicalrecord.model.MedicalHistory;
import com.telemed.doctor.medicalrecord.model.MedicalRecordResponse;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class MedicalRecordViewModel extends AndroidViewModel {
    private final String TAG= MedicalRecordViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final MutableLiveData<List<MedicalHistory>> lstOfMedicalHistory;

    public MutableLiveData<List<MedicalHistory>> getMedicalHistory() {
        return lstOfMedicalHistory;
    }

    private MutableLiveData<Boolean> isLoading,isViewEnabled;
    private MutableLiveData<ApiResponse<MedicalRecordResponse>> resultMedicalRecord;

    public MutableLiveData<ApiResponse<MedicalRecordResponse>> getResultMedicalRecord() {
        return resultMedicalRecord;
    }

    public void setMedicalHistoryList(List<MedicalHistory> appointmentList) {
        lstOfMedicalHistory.setValue(appointmentList);

    }

    public MedicalRecordViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        isLoading=new MutableLiveData<>();
        resultMedicalRecord=new MutableLiveData<>();
        lstOfMedicalHistory=new MutableLiveData<>();
        isViewEnabled=new MutableLiveData<>();
    }

    public void fetchMedicalRecord(HashMap<String, String> headerMap, String patientId) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchMedicalRecord(headerMap,patientId).enqueue(new Callback<MedicalRecordResponse>() {
            @Override
            public void onResponse(@NonNull Call<MedicalRecordResponse> call, @NonNull Response<MedicalRecordResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    MedicalRecordResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultMedicalRecord.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultMedicalRecord.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultMedicalRecord.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MedicalRecordResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultMedicalRecord.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }




    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }
    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }




}