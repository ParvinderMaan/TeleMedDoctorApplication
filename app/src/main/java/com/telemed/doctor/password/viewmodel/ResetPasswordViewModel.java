package com.telemed.doctor.password.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.password.model.ResetPasswordRequest;
import com.telemed.doctor.password.model.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ResetPasswordViewModel extends AndroidViewModel {
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<ResetPasswordResponse>> resultantResetPassword;

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;


    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantResetPassword = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
    }

    public void attemptResetPassword(ResetPasswordRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptResetPassword(in).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResetPasswordResponse> call, @NonNull Response<ResetPasswordResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    ResetPasswordResponse result = response.body();
                    Log.e("ResetPasswordViewModel",result.toString());
                    if(result.getStatus()){
                        resultantResetPassword.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantResetPassword.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantResetPassword.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }

            }
            @Override
            public void onFailure(@NonNull Call<ResetPasswordResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantResetPassword.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }

    public MutableLiveData<ApiResponse<ResetPasswordResponse>> getResultantResetPassword() {
        return resultantResetPassword;
    }
}
