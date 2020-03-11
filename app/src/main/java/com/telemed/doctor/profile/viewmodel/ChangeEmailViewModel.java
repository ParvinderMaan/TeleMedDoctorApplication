package com.telemed.doctor.profile.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.profile.model.ChangeEmailRequest;
import com.telemed.doctor.profile.model.ChangeEmailResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ChangeEmailViewModel extends AndroidViewModel {

    private final String TAG= ChangeEmailViewModel.class.getSimpleName();
    //@use Dagger instead
    private WebService mWebService;
    private MutableLiveData<ApiResponse<ChangeEmailResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;
    private MutableLiveData<ChangeEmailRequest> mChangeEmailRequest;
    private MutableLiveData<Map<String, String>> headerMap;
    private final HashMap<String, String> mHeaderMap;

    public ChangeEmailViewModel(@NonNull Application application) {
        super(application);
        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
        mChangeEmailRequest =new MutableLiveData<>();
       // headerMap=new MutableLiveData<>();
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("Authorization","Bearer "+accessToken);
    }



    public void attemptChangeEmail() {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
//        Log.e(TAG,in.toString());
        mWebService.attemptChangeEmail(mHeaderMap,mChangeEmailRequest.getValue()).enqueue(new Callback<ChangeEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChangeEmailResponse> call, @NonNull Response<ChangeEmailResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    ChangeEmailResponse result = response.body();
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
            public void onFailure(@NonNull Call<ChangeEmailResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<ChangeEmailResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }


    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap.setValue(headerMap);
    }


    public void setChangeaEmailInfo(ChangeEmailRequest changeEmailRequest) {
        this.mChangeEmailRequest.setValue(changeEmailRequest);
    }


}

