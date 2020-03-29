package com.telemed.doctor.password.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.password.model.ResendOtpResponse;
import com.telemed.doctor.password.model.VerficationRequest;
import com.telemed.doctor.password.model.VerificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class OneTimePasswordViewModel extends AndroidViewModel {
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<VerificationResponse>> resultantVerifyUser;
    private MutableLiveData<ApiResponse<ResendOtpResponse>> resultantResendOtp;

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;

    public OneTimePasswordViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantVerifyUser = new MutableLiveData<>();
        resultantResendOtp = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
    }

    public void attemptResendOtp(String email) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        JsonObject json=new JsonObject();
        json.addProperty("Email",email);
        mWebService.attemptResendOtp(json).enqueue(new Callback<ResendOtpResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResendOtpResponse> call, @NonNull Response<ResendOtpResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    ResendOtpResponse result = response.body();
                    Log.e("OtpViewModel",result.toString());
                    if(result.getStatus()){
                        resultantResendOtp.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantResendOtp.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantResendOtp.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }



            }

            @Override
            public void onFailure(@NonNull Call<ResendOtpResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantResendOtp.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public void attemptVerifyUser(VerficationRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptVerifyUser(in).enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<VerificationResponse> call, @NonNull Response<VerificationResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    VerificationResponse result = response.body();
                    Log.e("OtpViewModel",result.toString());
                    if(result.getStatus()){
                        resultantVerifyUser.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantVerifyUser.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantVerifyUser.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }



            }

            @Override
            public void onFailure(@NonNull Call<VerificationResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantVerifyUser.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }




    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }

    public MutableLiveData<ApiResponse<VerificationResponse>> getResultantVerifyUser() {
        return resultantVerifyUser;
    }

    public MutableLiveData<ApiResponse<ResendOtpResponse>> getResultantResendOtp() {
        return resultantResendOtp;
    }



}
