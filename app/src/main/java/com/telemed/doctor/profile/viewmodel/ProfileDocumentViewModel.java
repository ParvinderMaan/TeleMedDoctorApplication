package com.telemed.doctor.profile.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.model.FileDeleteResponse;
import com.telemed.doctor.signup.model.FileUploadResponse;
import com.telemed.doctor.signup.model.SignUpVResponse;
import com.telemed.doctor.signup.viewmodel.SignUpVViewModel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ProfileDocumentViewModel extends AndroidViewModel {
    private final String TAG= SignUpVViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpVResponse>> resultantSignUp;
    private MutableLiveData<ApiResponse<FileUploadResponse>> resultantFileUpload;
    private MutableLiveData<ApiResponse<FileDeleteResponse>> resultantFileDelete;

    private MutableLiveData<ApiResponse<AllDocumentResponse>> resultantAllDocument;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;




    public ProfileDocumentViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        resultantSignUp = new MutableLiveData<>();
        resultantFileUpload= new MutableLiveData<>();
        resultantFileDelete= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        resultantAllDocument=new MutableLiveData<>();



    }


    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }


    public void attemptFileUpload(Map<String, String> mHeaderMap, MultipartBody.Part docFile, int viewIndex) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptUploadFile(mHeaderMap,docFile).enqueue(new Callback<FileUploadResponse>() {
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

    public void attemptDeleteFile(Map<String, String> mHeaderMap, int fileId,int viewIndex) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptDeleteFile(mHeaderMap,fileId).enqueue(new Callback<FileDeleteResponse>() {
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


    public MutableLiveData<ApiResponse<FileDeleteResponse>> getResultantFileDelete() {
        return resultantFileDelete;
    }




}
