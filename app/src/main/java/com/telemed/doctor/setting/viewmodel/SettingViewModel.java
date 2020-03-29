package com.telemed.doctor.setting.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.setting.model.TimeZone;
import com.telemed.doctor.setting.model.UserSettingRequest;
import com.telemed.doctor.setting.model.UserSettingResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SettingViewModel extends AndroidViewModel {
    private final String TAG=SettingViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<ApiResponse<UserSettingResponse>> resultantUserSetting;
    private MutableLiveData<ApiResponse<UserSettingResponse>> resultantUpdateUserSetting;

    private MutableLiveData<List<Language>> lstOfLanguage;
    private MutableLiveData<List<TimeZone>> lstOfTimeZone;

    public MutableLiveData<Boolean> getEnableView() {
        return mEnableView;
    }

    private MutableLiveData<Boolean> mEnableView;


    public MutableLiveData<List<Language>> getAllLanguages() {
        return lstOfLanguage;
    }

    public MutableLiveData<List<TimeZone>> getTimeZones() {
        return lstOfTimeZone;
    }

    public SettingViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        isLoading=new MutableLiveData<>();
        resultantUserSetting=new MutableLiveData<>();
        resultantUpdateUserSetting=new MutableLiveData<>();
        lstOfLanguage=new MutableLiveData<>();
        lstOfTimeZone=new MutableLiveData<>();
        mEnableView=new MutableLiveData<>();

    }

    public void fetchUserSettings(Map<String, String> mHeaderMap) {
        this.isLoading.setValue(true);
        mEnableView.setValue(false);
        mWebService.fetchUserSettings(mHeaderMap).enqueue(new Callback<UserSettingResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserSettingResponse> call, @NonNull Response<UserSettingResponse> response) {
                isLoading.setValue(false);
                mEnableView.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    UserSettingResponse result = response.body();
                    if(result.getStatus()){
                        resultantUserSetting.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantUserSetting.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantUserSetting.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<UserSettingResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                mEnableView.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantUserSetting.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }



    public MutableLiveData<ApiResponse<UserSettingResponse>> getResultantUserSettings() {
        return resultantUserSetting;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }


    public void setLanguageList(List<Language> languageList) {
            lstOfLanguage.setValue(languageList);
    }

    public MutableLiveData<ApiResponse<UserSettingResponse>> getResultantUpdateUserSetting() {
        return resultantUpdateUserSetting;
    }

    public void updateUserSettings(Map<String, String> header, UserSettingRequest in) {
        this.isLoading.setValue(true);
        mEnableView.setValue(false);
        mWebService.updateUserSettings(header,in).enqueue(new Callback<UserSettingResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserSettingResponse> call, @NonNull Response<UserSettingResponse> response) {
                isLoading.setValue(false);
                mEnableView.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    UserSettingResponse result = response.body();
                    if(result.getStatus()){
                        resultantUpdateUserSetting.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultantUpdateUserSetting.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultantUpdateUserSetting.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }




            }

            @Override
            public void onFailure(@NonNull Call<UserSettingResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                mEnableView.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultantUpdateUserSetting.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public void setTimeZoneList(List<TimeZone> timeZoneList) {
        lstOfTimeZone.setValue(timeZoneList);
    }
}