package com.telemed.doctor.consult.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.consult.model.Appointment;
import com.telemed.doctor.consult.model.AppointmentListResponse;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.profile.model.AlterProfilePicResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class MyConsultViewModel extends AndroidViewModel {
    private final String TAG=MyConsultViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final HashMap<String, String> mHeaderMap;
    private final MutableLiveData<List<Appointment>> lstOfAppointment;
    private MutableLiveData<Boolean> isLoading,isViewEnabled;
    private MutableLiveData<ApiResponse<AppointmentListResponse>> resultant;

    public MutableLiveData<List<Appointment>> getAppointmentList() {
        return lstOfAppointment;
    }

    public MyConsultViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        String accessToken = ((TeleMedApplication) application).getSharedPrefInstance()
                .read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        isLoading=new MutableLiveData<>();
        resultant=new MutableLiveData<>();
        isViewEnabled=new MutableLiveData<>();
        lstOfAppointment=new MutableLiveData<>();

        mHeaderMap = new HashMap<>();
        mHeaderMap.put("Authorization","Bearer "+accessToken);
    }


    public void fetchUpcomingAppointments() {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchUpcomingAppointment(mHeaderMap).enqueue(new Callback<AppointmentListResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentListResponse> call, @NonNull Response<AppointmentListResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body()!=null) {
                    AppointmentListResponse result = response.body();
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
            public void onFailure(@NonNull Call<AppointmentListResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultant.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public MutableLiveData<ApiResponse<AppointmentListResponse>> getResultant() {
        return resultant;
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }
    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }


    public void setAppointmentList(List<Appointment> appointmentList) {
        lstOfAppointment.setValue(appointmentList);
    }
}
