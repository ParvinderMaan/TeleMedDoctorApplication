package com.telemed.doctor.document;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.document.model.MedicalHistoryRequest;
import com.telemed.doctor.document.model.MedicalHistoryResponse;
import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.schedule.model.PatientDetailResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class DoctorDocumentViewModel extends AndroidViewModel {
    private final String TAG= DoctorDocumentViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<PatientDetailResponse>> resultantPatientDetail;
    private MutableLiveData<ApiResponse<MedicalHistoryResponse>> resultantMedicalHistoryDetail;
    private MutableLiveData<ApiResponse<MedicalHistoryResponse>> resultantAlterMedicalHistoryDetail;
    private MutableLiveData<Boolean> isLoading,isViewEnabled;

    public MutableLiveData<ApiResponse<MedicalHistoryResponse>> getResultantMedicalHistoryDetail() {
        return resultantMedicalHistoryDetail;
    }

    public MutableLiveData<ApiResponse<MedicalHistoryResponse>> getResultantAlterMedicalHistoryDetail() {
        return resultantAlterMedicalHistoryDetail;
    }

    public DoctorDocumentViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantPatientDetail= new MutableLiveData<>();
        resultantMedicalHistoryDetail= new MutableLiveData<>();
        resultantAlterMedicalHistoryDetail= new MutableLiveData<>();
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

    public void fetchPastMedicalHistory(Map<String, String> map, Integer  appointmentId) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.fetchPastMedicalHistory(map,appointmentId).enqueue(new Callback<MedicalHistoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<MedicalHistoryResponse> call, @NonNull Response<MedicalHistoryResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    MedicalHistoryResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantMedicalHistoryDetail.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<MedicalHistoryResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public void addPastMedicalHistory(Map<String, String> map, MedicalHistoryRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.addPastMedicalHistory(map,in).enqueue(new Callback<MedicalHistoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<MedicalHistoryResponse> call, @NonNull Response<MedicalHistoryResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    MedicalHistoryResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<MedicalHistoryResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }


    public void updatePastMedicalHistory(Map<String, String> map, MedicalHistoryRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.updatePastMedicalHistory(map,in).enqueue(new Callback<MedicalHistoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<MedicalHistoryResponse> call, @NonNull Response<MedicalHistoryResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    MedicalHistoryResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }

            }

            @Override
            public void onFailure(@NonNull Call<MedicalHistoryResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAlterMedicalHistoryDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

}
