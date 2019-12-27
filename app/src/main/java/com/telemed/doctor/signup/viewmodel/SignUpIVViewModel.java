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
import com.telemed.doctor.signup.model.SignUpIVRequest;
import com.telemed.doctor.signup.model.SignUpIVResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpIVViewModel extends AndroidViewModel {
    private final String TAG=SignUpIVViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpIVResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<Map<String, String>> headerMap;
    private MutableLiveData<SignUpIVRequest> signUpIVInfo;


    public SignUpIVViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        headerMap =new MutableLiveData<>();
        signUpIVInfo=new MutableLiveData<>();

    }


    public void attemptSignUp() {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.attemptSignUpFour(headerMap.getValue(),signUpIVInfo.getValue()).enqueue(new Callback<SignUpIVResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpIVResponse> call, @NonNull Response<SignUpIVResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    SignUpIVResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultant.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else {

                }



            }

            @Override
            public void onFailure(@NonNull Call<SignUpIVResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpIVResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap.setValue(headerMap);
    }

    public void setSignUpIVInfo(SignUpIVRequest signUpIVInfo) {
        this.signUpIVInfo.setValue(signUpIVInfo);
    }

    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }
}
