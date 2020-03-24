package com.telemed.doctor.miscellaneous.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.home.WelcomeInfoResponse;
import com.telemed.doctor.miscellaneous.model.SignOutResponse;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class HomeViewModel extends AndroidViewModel {
    //@use Dagger instead
    private final WebService mWebService;
    private final SharedPrefHelper mHelper;
    private MutableLiveData<ApiResponse<SignOutResponse>> resultant;
    private MutableLiveData<ApiResponse<WelcomeInfoResponse>> resultantWelcomeInfoFirst,resultantWelcomeInfoNext;

    private MutableLiveData<Boolean> isLoading,isRefreshing;
    private MutableLiveData<Boolean> isViewEnabled;
    public HomeViewModel(@NonNull Application application) {
        super(application);
         mWebService = ((TeleMedApplication) application).getRetrofitInstance();
         mHelper = ((TeleMedApplication)application).getSharedPrefInstance();

        resultant = new MutableLiveData<>();
        resultantWelcomeInfoFirst = new MutableLiveData<>();
        resultantWelcomeInfoNext=new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isRefreshing=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
    }

    public MutableLiveData<ApiResponse<WelcomeInfoResponse>> getResultantWelcomeInfoFirst() {
        return resultantWelcomeInfoFirst;
    }

    public MutableLiveData<ApiResponse<WelcomeInfoResponse>> getResultantWelcomeInfoNext() {
        return resultantWelcomeInfoNext;
    }


    public void attemptSignOut(Map<String, String> in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptSignOut(in).enqueue(new Callback<SignOutResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignOutResponse> call, @NonNull Response<SignOutResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    SignOutResponse result = response.body();
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
            public void onFailure(@NonNull Call<SignOutResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignOutResponse>> getSignOutResultant() {
        return resultant;
    }
    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getRefreshing() {
        return isRefreshing;
    }


    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }



    public void fetchWelcomeInfoFirst(Map<String, String> in) {
        this.isLoading.setValue(true);
        mWebService.fetchWelcomeInfo(in).enqueue(new Callback<WelcomeInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<WelcomeInfoResponse> call, @NonNull Response<WelcomeInfoResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body()!=null) {
                    WelcomeInfoResponse result = response.body();
                    if(result.getStatus()){
                        resultantWelcomeInfoFirst.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantWelcomeInfoFirst.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantWelcomeInfoFirst.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }

            }

            @Override
            public void onFailure(@NonNull Call<WelcomeInfoResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultantWelcomeInfoFirst.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public void fetchWelcomeInfoNext(Map<String, String> in) {
        this.isRefreshing.setValue(true);
        mWebService.fetchWelcomeInfo(in).enqueue(new Callback<WelcomeInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<WelcomeInfoResponse> call, @NonNull Response<WelcomeInfoResponse> response) {
                isRefreshing.setValue(false);
                if (response.isSuccessful() && response.body()!=null) {
                    WelcomeInfoResponse result = response.body();
                    if(result.getStatus()){
                        resultantWelcomeInfoNext.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantWelcomeInfoNext.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantWelcomeInfoNext.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }

            }

            @Override
            public void onFailure(@NonNull Call<WelcomeInfoResponse> call, @NonNull Throwable error) {
                isRefreshing.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultantWelcomeInfoNext.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }




}
