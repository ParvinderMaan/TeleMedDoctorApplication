package com.telemed.doctor.signin;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;


public class SignInViewModel extends AndroidViewModel {
    private final String TAG=SignInViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignInResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewClickable;



    public SignInViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewClickable=new MutableLiveData<>();
    }



    public void attemptSignIn(SignInRequest in) {
        this.isLoading.setValue(true);
        this.isViewClickable.setValue(false);
        Log.e(TAG,in.toString());
        mWebService.attemptSignIn(in).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignInResponse> call, @NonNull Response<SignInResponse> response) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    SignInResponse result = response.body();
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
            public void onFailure(@NonNull Call<SignInResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);
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

    void cancelRequest(){


    }

    public MutableLiveData<Boolean> getViewClickable() {
        return isViewClickable;
    }







}
