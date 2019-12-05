package com.telemed.doctor.signup.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonSyntaxException;
import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.network.WrapperError;
import com.telemed.doctor.network.WrapperResponse;
import com.telemed.doctor.signup.model.SignUpIRequest;
import com.telemed.doctor.signup.model.SignUpIResponse;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.ERROR;
import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpIViewModel extends AndroidViewModel {
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpIResponse>> resultant;
    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();



    public SignUpIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
    }

    public void attemptSignUp(SignUpIRequest in) {
        this.isLoading.setValue(true);
        mWebService.attemptSignUpOne(in).enqueue(new Callback<SignUpIResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpIResponse> call, @NonNull Response<SignUpIResponse> response) {
               isLoading.setValue(false);
                if (response.isSuccessful() && response.body()!=null) {
                    SignUpIResponse result = response.body();

                    if(result.getStatus()){
                        resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        result.getMessage();
                        resultant.setValue(new ApiResponse<>(FAILURE, result, null));
                    }

                    return;
                }

            }

            @Override
            public void onFailure(@NonNull Call<SignUpIResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(ERROR, null, errorMsg));
            }
        });

    }

    public MutableLiveData<ApiResponse<SignUpIResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

}
