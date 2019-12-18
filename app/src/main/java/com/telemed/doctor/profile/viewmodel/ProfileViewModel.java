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
import com.telemed.doctor.profile.model.AlterProfilePicResponse;
import com.telemed.doctor.profile.model.BasicInfoResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;


public class ProfileViewModel extends AndroidViewModel {
    private final String TAG=ProfileViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final HashMap<String, String> mHeaderMap;
    private MutableLiveData<Boolean> isLoading,isViewEnabled;
    private MutableLiveData<ApiResponse<AlterProfilePicResponse>> resultant;



    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        isLoading=new MutableLiveData<>();
        resultant=new MutableLiveData<>();
        isViewEnabled=new MutableLiveData<>();

        mHeaderMap = new HashMap<>();
        mHeaderMap.put("Authorization","Bearer "+accessToken);
    }

    public void alterProfilePic(MultipartBody.Part imgPart) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.alterProfilePic(mHeaderMap,imgPart).enqueue(new Callback<AlterProfilePicResponse>() {
            @Override
            public void onResponse(@NonNull Call<AlterProfilePicResponse> call, @NonNull Response<AlterProfilePicResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    AlterProfilePicResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultant.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<AlterProfilePicResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<AlterProfilePicResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }
    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }



}
