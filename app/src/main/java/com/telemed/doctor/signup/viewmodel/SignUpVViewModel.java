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
import com.telemed.doctor.signup.model.SignUpVRequest;
import com.telemed.doctor.signup.model.SignUpVResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;

public class SignUpVViewModel extends AndroidViewModel {
    private final String TAG=SignUpVViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpVResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewClickable;

    public SignUpVViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewClickable=new MutableLiveData<>();
    }


    public void attemptSignUp(SignUpVRequest in, Map<String, String> map) {
        this.isLoading.setValue(true);
        this.isViewClickable.setValue(false);
        Log.e(TAG,in.toString());
        mWebService.attemptSignUpFive(map,in).enqueue(new Callback<SignUpVResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpVResponse> call, @NonNull Response<SignUpVResponse> response) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);

//                if (response.isSuccessful() && response.body()!=null) {
//                    SignUpIIIResponse result = response.body();
//                    Log.e(TAG,result.toString());
//                    if(result.getStatus()){
//                        resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
//                    }else {
//                        resultant.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
//                    }
//                }else {
//
//                }



            }

            @Override
            public void onFailure(@NonNull Call<SignUpVResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpVResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    void cancelRequest(){


    }

    public MutableLiveData<Boolean> getViewClickable() {
        return isViewClickable;
    }



}
