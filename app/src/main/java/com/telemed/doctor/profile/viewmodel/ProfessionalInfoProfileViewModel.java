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
import com.telemed.doctor.profile.model.ProfessionalInfoRequest;
import com.telemed.doctor.profile.model.ProfessionalInfoResponse;
import com.telemed.doctor.profile.model.ProfileUpdateResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ProfessionalInfoProfileViewModel extends AndroidViewModel {
    private final String TAG=ProfessionalInfoProfileViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<Boolean> isLoading;
    private final HashMap<String, String> mHeaderMap;
    private MutableLiveData<ApiResponse<ProfessionalInfoResponse>> resultant;
    private MutableLiveData<Boolean> mEnableView;
    private MutableLiveData<ApiResponse<ProfileUpdateResponse>> resultantUpdateProfInfo;



    public ProfessionalInfoProfileViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        isLoading=new MutableLiveData<>();
        resultant=new MutableLiveData<>();
        mEnableView=new MutableLiveData<>();
        resultantUpdateProfInfo=new MutableLiveData<>();
        mEditableView=new MutableLiveData<>();

        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization","Bearer "+accessToken);
    }

    public void fetchProfessionalInfo() {
        this.isLoading.setValue(true);
        mWebService.fetchProfessionalProfileInfo(mHeaderMap).enqueue(new Callback<ProfessionalInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfessionalInfoResponse> call, @NonNull Response<ProfessionalInfoResponse> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body()!=null) {
                    ProfessionalInfoResponse result = response.body();
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
            public void onFailure(@NonNull Call<ProfessionalInfoResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<ProfessionalInfoResponse>> getFetchResultant() {
        return resultant;
    }



    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public void updateProfessionalInfo(ProfessionalInfoRequest in) {
        this.isLoading.setValue(true);
        this.mEnableView.setValue(false);
        mWebService.updateProfessionalProfileInfo(mHeaderMap,in).enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileUpdateResponse> call, @NonNull Response<ProfileUpdateResponse> response) {
                isLoading.setValue(false);
                mEnableView.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    ProfileUpdateResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantUpdateProfInfo.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantUpdateProfInfo.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantUpdateProfInfo.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<ProfileUpdateResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                mEnableView.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantUpdateProfInfo.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });



    }

    public MutableLiveData<ApiResponse<ProfileUpdateResponse>> getResultantUpdateProfInfo() {
        return resultantUpdateProfInfo;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return mEnableView;
    }

    public void setEnableView(Boolean enableView) {
        this.mEnableView.setValue(enableView);
    }

    private MutableLiveData<Boolean> mEditableView;


    public MutableLiveData<Boolean> getEditableView() {
        return mEditableView;
    }


    public void setEditableView(boolean enableView) {
        this.mEditableView.setValue(enableView);
    }
}
