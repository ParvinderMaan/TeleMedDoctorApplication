package com.telemed.doctor.signup.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.signup.model.SignUpIIIRequest;
import com.telemed.doctor.signup.model.SignUpIIIResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpIIIViewModel extends AndroidViewModel {
    private final String TAG=SignUpIIIViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpIIIResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<SignUpIIIRequest> signUpIIIInfo;
    private MutableLiveData<Map<String, String>> headerMap;

    public SignUpIIIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        signUpIIIInfo=new MutableLiveData<>();
        headerMap=new MutableLiveData<>();
    }

    public void attemptSignUp() {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.attemptSignUpThree(headerMap.getValue(),signUpIIIInfo.getValue()).enqueue(new Callback<SignUpIIIResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpIIIResponse> call, @NonNull Response<SignUpIIIResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    SignUpIIIResponse result = response.body();
                    Log.e(TAG,result.toString());
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
            public void onFailure(@NonNull Call<SignUpIIIResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpIIIResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public void setSignUpIIIInfo(SignUpIIIRequest signUpIIIInfo) {
        this.signUpIIIInfo.setValue(signUpIIIInfo);
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap.setValue(headerMap);
    }


    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }
}
