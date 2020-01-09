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
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.model.DocumentInfo;
import com.telemed.doctor.signup.model.FileDeleteResponse;
import com.telemed.doctor.signup.model.FileUploadResponse;
import com.telemed.doctor.signup.model.SignUpVResponse;

import java.util.List;
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
    private MutableLiveData<ApiResponse<SignUpVResponse>> resultantSignUp;
    private MutableLiveData<ApiResponse<FileUploadResponse>> resultantFileUpload;
    private MutableLiveData<ApiResponse<FileDeleteResponse>> resultantFileDelete;



    private MutableLiveData<ApiResponse<AllDocumentResponse>> resultantAllDocument;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;




    public SignUpVViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultantSignUp = new MutableLiveData<>();
        resultantFileUpload= new MutableLiveData<>();
        resultantFileDelete= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        resultantAllDocument=new MutableLiveData<>();

    }


    public void attemptSignUp(Map<String, String> map) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptSignUpFive(map).enqueue(new Callback<SignUpVResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpVResponse> call, @NonNull Response<SignUpVResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    SignUpVResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantSignUp.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantSignUp.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantSignUp.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<SignUpVResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantSignUp.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpVResponse>> getResultantSignUp() {
        return resultantSignUp;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    void cancelRequest(){

    }

    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }


    public void attemptFileUpload(Map<String, String> token, MultipartBody.Part docFile,int viewIndex) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptUploadFile(token,docFile).enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call, @NonNull Response<FileUploadResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    FileUploadResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        result.getData().setViewIndex(viewIndex);
                        resultantFileUpload.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantFileUpload.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantFileUpload.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantFileUpload.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public void attemptDeleteFile(Map<String, String> token, int fileId,int viewIndex) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptDeleteFile(token,fileId).enqueue(new Callback<FileDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<FileDeleteResponse> call, @NonNull Response<FileDeleteResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

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
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantFileDelete.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }

            }

            @Override
            public void onFailure(@NonNull Call<FileDeleteResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantFileDelete.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

    public MutableLiveData<ApiResponse<FileUploadResponse>> getResultantFileUpload() {
        return resultantFileUpload;
    }

    public void fetchAllDocuments(Map<String, String> map){
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.fetchAllDocument(map).enqueue(new Callback<AllDocumentResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllDocumentResponse> call, @NonNull Response<AllDocumentResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    AllDocumentResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantAllDocument.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantAllDocument.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantAllDocument.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }



            }

            @Override
            public void onFailure(@NonNull Call<AllDocumentResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantAllDocument.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<FileDeleteResponse>> getResultantFileDelete() {
        return resultantFileDelete;
    }

    public MutableLiveData<ApiResponse<AllDocumentResponse>> getResultantAllDocument() {
        return resultantAllDocument;
    }



}
