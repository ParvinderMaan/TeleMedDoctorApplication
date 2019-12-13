package com.telemed.doctor.signup.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.base.BaseCallback;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.signup.model.FileDeleteResponse;
import com.telemed.doctor.signup.model.FileUploadResponse;
import com.telemed.doctor.signup.model.SignUpVRequest;
import com.telemed.doctor.signup.model.SignUpVResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpVViewModel extends AndroidViewModel {
    private final String TAG=SignUpVViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;

    public MutableLiveData<ApiResponse<SignUpVResponse>> getResultantSignUp() {
        return resultantSignUp;
    }

    private MutableLiveData<ApiResponse<SignUpVResponse>> resultantSignUp;
    private MutableLiveData<ApiResponse<FileUploadResponse>> resultantFileUpload;

    public MutableLiveData<ApiResponse<FileDeleteResponse>> getResultantFileDelete() {
        return resultantFileDelete;
    }

    private MutableLiveData<ApiResponse<FileDeleteResponse>> resultantFileDelete;

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewClickable;



    public SignUpVViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantSignUp = new MutableLiveData<>();
        resultantFileUpload= new MutableLiveData<>();
        resultantFileDelete= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewClickable=new MutableLiveData<>();
    }


    public void attemptSignUp(Map<String, String> map) {
        this.isLoading.setValue(true);
        this.isViewClickable.setValue(false);
        mWebService.attemptSignUpFive(map).enqueue(new Callback<SignUpVResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpVResponse> call, @NonNull Response<SignUpVResponse> response) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    SignUpVResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantSignUp.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantSignUp.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else {

                }



            }

            @Override
            public void onFailure(@NonNull Call<SignUpVResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantSignUp.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpVResponse>> getResultant() {
        return resultantSignUp;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    void cancelRequest(){

    }

    public MutableLiveData<Boolean> getViewClickable() {
        return isViewClickable;
    }


    public void attemptFileUpload(Map<String, String> token, MultipartBody.Part docFile,int viewIndex) {
        this.isLoading.setValue(true);
        this.isViewClickable.setValue(false);
        mWebService.attemptUploadFile(token,docFile).enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call, @NonNull Response<FileUploadResponse> response) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    FileUploadResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        result.getData().setViewIndex(viewIndex);
                        resultantFileUpload.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantFileUpload.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantFileUpload.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public void attemptDeleteFile(Map<String, String> token, int fileId,int viewIndex) {
        this.isLoading.setValue(true);
        this.isViewClickable.setValue(false);
        mWebService.attemptDeleteFile(token,fileId).enqueue(new Callback<FileDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<FileDeleteResponse> call, @NonNull Response<FileDeleteResponse> response) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    FileDeleteResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        FileDeleteResponse.Data x = result.getData();
                        x.setViewIndex(viewIndex);
                        resultantFileDelete.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantFileDelete.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FileDeleteResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewClickable.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantFileDelete.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public MutableLiveData<ApiResponse<FileUploadResponse>> getResultantFileUpload() {
        return resultantFileUpload;
    }


}
