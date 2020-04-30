package com.telemed.doctor.schedule.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ScheduleViewModel extends AndroidViewModel {
    private final String TAG= ScheduleViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<MonthlyScheduleResponse>> resultAllScheduleIMonth;
    private MutableLiveData<ApiResponse<MonthlyScheduleResponse>> resultAllScheduleIIMonth;
    private MutableLiveData<ApiResponse<MonthlyScheduleResponse>> resultAllScheduleIIIMonth;

    private MutableLiveData<Boolean> isLoading;
   // private MutableLiveData<Boolean> isDialogVisible;
    private MutableLiveData<Boolean> isViewEnabled;
  //  private MutableLiveData<Map<String, String>> headerMap;

    public String getTAG() {
        return TAG;
    }

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        resultAllScheduleIMonth = new MutableLiveData<>();
        resultAllScheduleIIMonth=new MutableLiveData<>();
        resultAllScheduleIIIMonth=new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
   //     isDialogVisible=new MutableLiveData<>();
        isViewEnabled =new MutableLiveData<>();
     //   headerMap=new MutableLiveData<>();
        //---------------------------


    }



    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }



    public MutableLiveData<ApiResponse<MonthlyScheduleResponse>> getResultAllScheduleIMonth() {
        return resultAllScheduleIMonth;
    }

    public MutableLiveData<Boolean> getEnableView() {
        return isViewEnabled;
    }




    public void fetchMonthlySchedules(Map<String, String> map,int monthNo) {
        switch (monthNo){

            case 0:
                this.isLoading.setValue(true);
                this.isViewEnabled.setValue(false);
                mWebService.fetchMonthlySchedules(map,monthNo).enqueue(new Callback<MonthlyScheduleResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Response<MonthlyScheduleResponse> response) {
                        isLoading.setValue(false);
                        isViewEnabled.setValue(true);

                        if (response.isSuccessful() && response.body()!=null) {
                            MonthlyScheduleResponse result = response.body();
                            Log.e(TAG,result.toString());
                            if(result.getStatus()){
                                resultAllScheduleIMonth.setValue(new ApiResponse<>(SUCCESS, result, null));
                            }else {
                                resultAllScheduleIMonth.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                            }
                        }else{
                            String errorMsg = ErrorHandler.reportError(response.code());
                            resultAllScheduleIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                        }




                    }

                    @Override
                    public void onFailure(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Throwable error) {
                        isLoading.setValue(false);
                        isViewEnabled.setValue(true);
                        String errorMsg = ErrorHandler.reportError(error);
                        resultAllScheduleIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                    }
                });
                break;

            case 1:
                this.isLoading.setValue(true);
                this.isViewEnabled.setValue(false);
                mWebService.fetchMonthlySchedules(map,monthNo).enqueue(new Callback<MonthlyScheduleResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Response<MonthlyScheduleResponse> response) {
                        isLoading.setValue(false);
                        isViewEnabled.setValue(true);

                        if (response.isSuccessful() && response.body()!=null) {
                            MonthlyScheduleResponse result = response.body();
                            Log.e(TAG,result.toString());
                            if(result.getStatus()){
                                resultAllScheduleIIMonth.setValue(new ApiResponse<>(SUCCESS, result, null));
                            }else {
                                resultAllScheduleIIMonth.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                            }
                        }else{
                            String errorMsg = ErrorHandler.reportError(response.code());
                            resultAllScheduleIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                        }




                    }

                    @Override
                    public void onFailure(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Throwable error) {
                        isLoading.setValue(false);
                        isViewEnabled.setValue(true);
                        String errorMsg = ErrorHandler.reportError(error);
                        resultAllScheduleIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                    }
                });
                break;

            case 2:
                this.isLoading.setValue(true);
                this.isViewEnabled.setValue(false);
                mWebService.fetchMonthlySchedules(map,monthNo).enqueue(new Callback<MonthlyScheduleResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Response<MonthlyScheduleResponse> response) {
                        isLoading.setValue(false);
                        isViewEnabled.setValue(true);

                        if (response.isSuccessful() && response.body()!=null) {
                            MonthlyScheduleResponse result = response.body();
                            Log.e(TAG,result.toString());
                            if(result.getStatus()){
                                resultAllScheduleIIIMonth.setValue(new ApiResponse<>(SUCCESS, result, null));
                            }else {
                                resultAllScheduleIIIMonth.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                            }
                        }else{
                            String errorMsg = ErrorHandler.reportError(response.code());
                            resultAllScheduleIIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                        }




                    }

                    @Override
                    public void onFailure(@NonNull Call<MonthlyScheduleResponse> call, @NonNull Throwable error) {
                        isLoading.setValue(false);
                        isViewEnabled.setValue(true);
                        String errorMsg = ErrorHandler.reportError(error);
                        resultAllScheduleIIIMonth.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                    }
                });
                break;



        }



    }




    public MutableLiveData<ApiResponse<MonthlyScheduleResponse>> getResultAllScheduleIIMonth() {
        return resultAllScheduleIIMonth;
    }

    public MutableLiveData<ApiResponse<MonthlyScheduleResponse>> getResultAllScheduleIIIMonth() {
        return resultAllScheduleIIIMonth;
    }


//    public MutableLiveData<Boolean> getDialogVisiblility() {
//        return isDialogVisible;
//    }

//    public void setDialogVisiblility(Boolean isDialogVisible) {
//        this.isDialogVisible.setValue(isDialogVisible);
//    }
}
