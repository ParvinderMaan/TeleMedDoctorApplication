package com.telemed.doctor.signup.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.signup.model.SignUpIRequest;
import com.telemed.doctor.signup.model.SignUpIResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpIViewModel extends AndroidViewModel {
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpIResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<SignUpIRequest> mSignUpIInfo;



    public SignUpIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        mSignUpIInfo =new MutableLiveData<>();
    }

    public void attemptSignUp() {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptSignUpOne(mSignUpIInfo.getValue()).enqueue(new Callback<SignUpIResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpIResponse> call, @NonNull Response<SignUpIResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                        SignUpIResponse result = response.body();
                        Log.e("SignUpIVewModel",result.toString());
                        if(result.getStatus()){
                            resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
                        }else {
                            resultant.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                        }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<SignUpIResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpIResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    void cancelRequest(){


    }

    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }

    public void setSignUpIInfo(SignUpIRequest signUpIInfo) {
        this.mSignUpIInfo.setValue(signUpIInfo);
    }
}
