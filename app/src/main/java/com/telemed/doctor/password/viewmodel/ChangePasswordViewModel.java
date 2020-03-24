package com.telemed.doctor.password.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.password.model.ChangePasswordRequest;
import com.telemed.doctor.password.model.ChangePasswordResponse;
import com.telemed.doctor.password.model.ResetPasswordRequest;
import com.telemed.doctor.password.model.ResetPasswordResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ChangePasswordViewModel extends AndroidViewModel {

    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<ChangePasswordResponse>> resultantChangePassword;

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;


    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantChangePassword = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
    }

    public void attemptChangePassword(Map<String, String> token,ChangePasswordRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptChangePassword(token,in).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull Response<ChangePasswordResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    ChangePasswordResponse result = response.body();
                    Log.e("ResetPasswordViewModel",result.toString());
                    if(result.getStatus()){
                        resultantChangePassword.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantChangePassword.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantChangePassword.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }

            }
            @Override
            public void onFailure(@NonNull Call<ChangePasswordResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantChangePassword.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }

    public MutableLiveData<ApiResponse<ChangePasswordResponse>> getResultantChangePassword() {
        return resultantChangePassword;
    }

}
