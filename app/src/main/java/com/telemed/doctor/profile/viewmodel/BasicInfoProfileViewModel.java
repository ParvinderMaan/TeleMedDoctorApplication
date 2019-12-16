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
import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.signin.SignInResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class BasicInfoProfileViewModel extends AndroidViewModel {
    private final String TAG=BasicInfoProfileViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final HashMap<String, String> mHeaderMap;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<ApiResponse<BasicInfoResponse>> resultant;


    public BasicInfoProfileViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        isLoading=new MutableLiveData<>();
        resultant=new MutableLiveData<>();

        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization","Bearer "+accessToken);

    }

    public void fetchBasicInfo() {
        this.isLoading.setValue(true);
        mWebService.fetchBasicProfileInfo(mHeaderMap).enqueue(new Callback<BasicInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicInfoResponse> call, @NonNull Response<BasicInfoResponse> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body()!=null) {
                    BasicInfoResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultant.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }



            }

            @Override
            public void onFailure(@NonNull Call<BasicInfoResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<BasicInfoResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }




}
