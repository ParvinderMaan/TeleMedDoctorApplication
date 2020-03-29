package com.telemed.doctor.signin.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.iid.FirebaseInstanceId;
import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.signin.model.SignInRequest;
import com.telemed.doctor.signin.model.SignInResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;


public class SignInViewModel extends AndroidViewModel {
    private final String TAG=SignInViewModel.class.getSimpleName();
    //@use Dagger instead
    private  WebService mWebService;
    private MutableLiveData<ApiResponse<SignInResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<SignInRequest> mSignInRequest;
    private MutableLiveData<String> mDeviceToken;

    public MutableLiveData<String> getDeviceToken() {
        return mDeviceToken;
    }

    public SignInViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        mSignInRequest =new MutableLiveData<>();
        mDeviceToken=new MutableLiveData<>();
        intializeDeviceToken();
    }

    private void intializeDeviceToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult()!=null) {
                            // Get new Instance ID token
                            mDeviceToken.setValue(task.getResult().getToken());
                        }else {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            mDeviceToken.setValue("12345");
                        }
                    });

    }


    public void attemptSignIn() {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
//        Log.e(TAG,in.toString());
        mWebService.attemptSignIn(mSignInRequest.getValue()).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignInResponse> call, @NonNull Response<SignInResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    SignInResponse result = response.body();
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
            public void onFailure(@NonNull Call<SignInResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignInResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }




    public void setSignInInfo(SignInRequest signInRequest) {
        this.mSignInRequest.setValue(signInRequest);
    }


}