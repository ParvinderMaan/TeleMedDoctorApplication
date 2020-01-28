package com.telemed.doctor.profile.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.room.database.TeleMedDatabase;
import com.telemed.doctor.signin.SignInResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
    private final TeleMedDatabase mDatabase;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<ApiResponse<BasicInfoResponse>> resultant;
    private MutableLiveData<BasicInfoResponse.BasicDetail> mBasicDetail;
    private ScheduledExecutorService mService;

    public LiveData<BasicInfoResponse.BasicDetail> getBasicDetail() {
        //mDatabase.basicInfoProfileDao().getBasicDetail()
        //basicDetail
        return mBasicDetail;
    }

    public void setBasicDetail(BasicInfoResponse.BasicDetail basicDetail) {
      this.mBasicDetail.setValue(basicDetail);
//        mService.submit(() -> {
//                   mDatabase.basicInfoProfileDao().insert(basicDetail);
//        });

    }

    public BasicInfoProfileViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");

        mDatabase=((TeleMedApplication)application).getDatabaseInstance();
        mService= Executors.newSingleThreadScheduledExecutor();


        isLoading=new MutableLiveData<>();
        resultant=new MutableLiveData<>();
        mBasicDetail=new MutableLiveData<>();

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
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
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


/*
java.net.SocketTimeoutException
 */

}
