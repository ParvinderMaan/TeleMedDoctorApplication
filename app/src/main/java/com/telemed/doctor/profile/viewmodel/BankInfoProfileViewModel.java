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
import com.telemed.doctor.profile.model.BankInfoResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class BankInfoProfileViewModel extends AndroidViewModel {
    private final String TAG=BankInfoProfileViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<ApiResponse<BankInfoResponse>> resultant;
    private final HashMap<String, String> mHeaderMap;


    public BankInfoProfileViewModel(@NonNull Application application) {
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

    public void fetchBankInfo() {
        this.isLoading.setValue(true);
        mWebService.fetchBankProfileInfo(mHeaderMap).enqueue(new Callback<BankInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<BankInfoResponse> call, @NonNull Response<BankInfoResponse> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body()!=null) {
                    BankInfoResponse result = response.body();
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
            public void onFailure(@NonNull Call<BankInfoResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<BankInfoResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

}
