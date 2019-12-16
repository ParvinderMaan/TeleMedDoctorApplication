package com.telemed.doctor.signup.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.signup.model.SignUpIRequest;
import com.telemed.doctor.signup.model.SignUpIResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignUpIViewModel extends AndroidViewModel {
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<SignUpIResponse>> resultant;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isViewEnabled;



    public SignUpIViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultant = new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
    }

    public void attemptSignUp(SignUpIRequest in) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);
        mWebService.attemptSignUpOne(in).enqueue(new Callback<SignUpIResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpIResponse> call, @NonNull Response<SignUpIResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                        SignUpIResponse result = response.body();
                        Log.e("SignUpIVewModel",result.toString());
                        if(result.getStatus()){
                            resultant.setValue(new ApiResponse<>(SUCCESS, result, null));
                        }else {
                            resultant.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                        }
                    }



            }

            @Override
            public void onFailure(@NonNull Call<SignUpIResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<SignUpIResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    void cancelRequest(){


    }

    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }
}
