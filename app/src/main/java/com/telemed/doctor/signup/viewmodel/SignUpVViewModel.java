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
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.model.CvFileUploadResponse;
import com.telemed.doctor.signup.model.FileDeleteResponse;
import com.telemed.doctor.signup.model.FileUploadResponse;
import com.telemed.doctor.signup.model.SignUpVResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpVViewModel extends AndroidViewModel {
    private final String TAG=SignUpVViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpVResponse>> resultantSignUp;
    private MutableLiveData<ApiResponse<FileUploadResponse>> resultantFileUpload;
    private MutableLiveData<ApiResponse<FileDeleteResponse>> resultantFileDelete;
    private MutableLiveData<ApiResponse<CvFileUploadResponse>> resultantCvFileUpload,resultantGovtIdFileUpload;



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
        resultantCvFileUpload=new MutableLiveData<>();
        resultantGovtIdFileUpload=new MutableLiveData<>();

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


    public void attemptFileUpload(Map<String, String> token, MultipartBody.Part docFile,int viewIndex,RequestBody dateOfExpiry) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptUploadFile(token,docFile,dateOfExpiry).enqueue(new Callback<FileUploadResponse>() {
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

    public MutableLiveData<ApiResponse<CvFileUploadResponse>> getResultantCvFileUpload() {
        return resultantCvFileUpload;
    }

    public MutableLiveData<ApiResponse<CvFileUploadResponse>> getResultantGovtIdFileUpload() {
        return resultantGovtIdFileUpload;
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



    public void attemptCVFileUpload(Map<String, String> token, MultipartBody.Part docFile) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptCVUploadFile(token,docFile).enqueue(new Callback<CvFileUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<CvFileUploadResponse> call, @NonNull Response<CvFileUploadResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    CvFileUploadResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){

                        resultantCvFileUpload.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantCvFileUpload.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantCvFileUpload.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<CvFileUploadResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantCvFileUpload.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }



    public void attemptGovtRegisteredUploadFile(Map<String, String> token, MultipartBody.Part docFile,RequestBody info) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptGovtRegisteredUploadFile(token,docFile,info).enqueue(new Callback<CvFileUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<CvFileUploadResponse> call, @NonNull Response<CvFileUploadResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    CvFileUploadResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){

                        resultantGovtIdFileUpload.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantGovtIdFileUpload.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantGovtIdFileUpload.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }


            }

            @Override
            public void onFailure(@NonNull Call<CvFileUploadResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantGovtIdFileUpload.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }

}
