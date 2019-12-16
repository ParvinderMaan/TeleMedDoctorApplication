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
import com.telemed.doctor.signup.model.SignUpIIRequest;
import com.telemed.doctor.signup.model.SignUpIIResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpIIViewModel extends AndroidViewModel {
    private final String TAG=SignUpIIViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpIIResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;



    public SignUpIIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
    }
    public void attemptSignUp(SignUpIIRequest in,  Map<String, String> map) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        Log.e(TAG,in.toString());
        mWebService.attemptSignUpTwo(map,in).enqueue(new Callback<SignUpIIResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpIIResponse> call, @NonNull Response<SignUpIIResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    SignUpIIResponse result = response.body();
                    Log.e("SignUpIIViewModel",result.toString());
                    if(result.getStatus()){
                        resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultant.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else {

                }



            }

            @Override
            public void onFailure(@NonNull Call<SignUpIIResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpIIResponse>> getResultant() {
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
}
