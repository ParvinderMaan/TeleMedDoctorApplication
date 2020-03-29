package com.telemed.doctor.profile.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.profile.model.BankInfoRequest;
import com.telemed.doctor.profile.model.BankInfoResponse;
import com.telemed.doctor.profile.model.ProfileUpdateResponse;

import java.util.HashMap;

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
    private MutableLiveData<ApiResponse<BankInfoResponse>> resultantFetchBankInfo;
    private MutableLiveData<ApiResponse<ProfileUpdateResponse>> resultantUpdateBankInfo;
    private final HashMap<String, String> mHeaderMap;
    private MutableLiveData<Boolean> mEditableView;
    private MutableLiveData<Boolean> mEnableView;


    public MutableLiveData<Boolean> getEditableView() {
        return mEditableView;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return mEnableView;
    }

    public void setEnableView(Boolean enableView) {
        this.mEnableView.setValue(enableView);
    }

    public BankInfoProfileViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();

        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");

        isLoading=new MutableLiveData<>();
        resultantFetchBankInfo=new MutableLiveData<>();
        resultantUpdateBankInfo=new MutableLiveData<>();
        mEditableView=new MutableLiveData<>();
        mEnableView=new MutableLiveData<>();
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
                        resultantFetchBankInfo.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantFetchBankInfo.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantFetchBankInfo.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<BankInfoResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultantFetchBankInfo.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public void updateBankInfo(BankInfoRequest in) {
        this.isLoading.setValue(true);
        this.mEnableView.setValue(false);
        mWebService.updateBankInfo(mHeaderMap,in).enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileUpdateResponse> call, @NonNull Response<ProfileUpdateResponse> response) {
                isLoading.setValue(false);
                mEnableView.setValue(true);
                mEditableView.setValue(false);

                if (response.isSuccessful() && response.body()!=null) {
                    ProfileUpdateResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultantUpdateBankInfo.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantUpdateBankInfo.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantUpdateBankInfo.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<ProfileUpdateResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                mEnableView.setValue(true);
                mEditableView.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultantUpdateBankInfo.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });



    }

    public MutableLiveData<ApiResponse<BankInfoResponse>> getFetchResultant() {
        return resultantFetchBankInfo;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public void setEditableView(boolean enableView) {
        this.mEditableView.setValue(enableView);
    }

    public MutableLiveData<ApiResponse<ProfileUpdateResponse>> getUpdateResultant() {
        return resultantUpdateBankInfo;
    }



}
