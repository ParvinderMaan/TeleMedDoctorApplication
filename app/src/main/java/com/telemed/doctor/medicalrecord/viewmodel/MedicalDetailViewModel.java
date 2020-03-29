package com.telemed.doctor.medicalrecord.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.medicalrecord.model.MedicalDetailResponse;
import com.telemed.doctor.medicalrecord.model.MedicalHistory;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class MedicalDetailViewModel extends AndroidViewModel {
    private final String TAG = MedicalDetailViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final MutableLiveData<List<MedicalDetailResponse.MedicalHistory>> lstOfMedicalHistory;

    public MutableLiveData<List<MedicalDetailResponse.MedicalHistory>> getMedicalHistory() {
        return lstOfMedicalHistory;
    }

    private MutableLiveData<Boolean> isLoading, isViewEnabled;
    private MutableLiveData<ApiResponse<MedicalDetailResponse>> resultMedicalDetail;

    public MutableLiveData<ApiResponse<MedicalDetailResponse>> getResultMedicalDetail() {
        return resultMedicalDetail;
    }

    public void setMedicalHistoryList(List<MedicalDetailResponse.MedicalHistory> appointmentList) {
        lstOfMedicalHistory.setValue(appointmentList);

    }


    public MedicalDetailViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        isLoading = new MutableLiveData<>();
        resultMedicalDetail = new MutableLiveData<>();
        lstOfMedicalHistory = new MutableLiveData<>();
        isViewEnabled = new MutableLiveData<>();

    }

    public void fetchMedicalDetails(HashMap<String, String> headerMap, String patientId,String type) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchMedicalDetails(headerMap, patientId, type).enqueue(new Callback<MedicalDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<MedicalDetailResponse> call, @NonNull Response<MedicalDetailResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body() != null) {
                    MedicalDetailResponse result = response.body();
                    if (result.getStatus()) {
                        resultMedicalDetail.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultMedicalDetail.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultMedicalDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MedicalDetailResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultMedicalDetail.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
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
