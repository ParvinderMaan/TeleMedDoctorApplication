package com.telemed.doctor.notification.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.helper.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.notification.model.DeleteNotificationResponse;
import com.telemed.doctor.notification.model.NotificationModel;
import com.telemed.doctor.notification.model.NotificationRequest;
import com.telemed.doctor.notification.model.NotificationResponse;
import com.telemed.doctor.notification.model.ReadNotificationResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class NotificationViewModel extends AndroidViewModel {
    private final String TAG = NotificationViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private final MutableLiveData<List<NotificationModel>> lstOfNotification;


    public MutableLiveData<List<NotificationModel>> getNotifications() {
        return lstOfNotification;
    }

    private MutableLiveData<Boolean> isLoading, isViewEnabled, isListLoading, isLastPage;

    private MutableLiveData<ApiResponse<NotificationResponse>> resultFirstNotification, resultNextNotification;
    private MutableLiveData<ApiResponse<DeleteNotificationResponse>> resultDeleteNotification;
    private MutableLiveData<ApiResponse<ReadNotificationResponse>> resultReadNotification;


    public MutableLiveData<ApiResponse<NotificationResponse>> getResultFirstNotification() {
        return resultFirstNotification;
    }

    public MutableLiveData<ApiResponse<NotificationResponse>> getResultNextNotification() {
        return resultNextNotification;
    }

    public MutableLiveData<Boolean> getListLoadingStatus() {
        return isListLoading;
    }

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        isListLoading = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        resultFirstNotification = new MutableLiveData<>();
        resultNextNotification = new MutableLiveData<>();
        resultDeleteNotification= new MutableLiveData<>();
        resultReadNotification= new MutableLiveData<>();
        isViewEnabled = new MutableLiveData<>();
        lstOfNotification = new MutableLiveData<>();
        isLastPage = new MutableLiveData<>();

    }

    public MutableLiveData<Boolean> isLastPage() {
        return isLastPage;
    }

    public void isLastPage(Boolean isLastPage) {
        this.isLastPage.setValue(isLastPage);
    }


    public void fetchFirstNotifications(HashMap<String, String> headerMap, NotificationRequest in) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.fetchNotifications(headerMap, in).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<NotificationResponse> call, @NonNull Response<NotificationResponse> response) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body() != null) {
                    NotificationResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultFirstNotification.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultFirstNotification.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultFirstNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultFirstNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }

    public void fetchNextNotifications(HashMap<String, String> headerMap, NotificationRequest in) {
        mWebService.fetchNotifications(headerMap, in).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<NotificationResponse> call, @NonNull Response<NotificationResponse> response) {
                isListLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    NotificationResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultNextNotification.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultNextNotification.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultNextNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationResponse> call, @NonNull Throwable error) {
                isListLoading.setValue(false);
                String errorMsg = ErrorHandler.reportError(error);
                resultNextNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getViewEnabled() {
        return isViewEnabled;
    }


    public void setNotificationList(List<NotificationModel> appointmentList) {
        lstOfNotification.setValue(appointmentList);
    }

    public void setListLoading(Boolean isListLoading) {
        this.isListLoading.setValue(isListLoading);
    }


    public MutableLiveData<ApiResponse<DeleteNotificationResponse>> getResultDeleteNotification() {
        return resultDeleteNotification;
    }

    public void deleteNotifications(HashMap<String, String> headerMap) {
        this.isLoading.setValue(true);
        isViewEnabled.setValue(false);
        mWebService.deleteNotifications(headerMap).enqueue(new Callback<DeleteNotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeleteNotificationResponse> call, @NonNull Response<DeleteNotificationResponse> response) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                if (response.isSuccessful() && response.body() != null) {
                    DeleteNotificationResponse result = response.body();
                    Log.e(TAG, result.toString());
                    if (result.getStatus()) {
                        resultDeleteNotification.setValue(new ApiResponse<>(SUCCESS, result, null));
                    } else {
                        resultDeleteNotification.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                } else {
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultDeleteNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteNotificationResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isListLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultDeleteNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });


    }


    public MutableLiveData<ApiResponse<ReadNotificationResponse>> getResultReadNotification() {
        return resultReadNotification;
    }

    public void readNotification(HashMap<String, String> map, Integer notificationId) {
        this.isLoading.setValue(true);
        this.isViewEnabled.setValue(false);

        mWebService.readNotification(map,notificationId).enqueue(new Callback<ReadNotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReadNotificationResponse> call, @NonNull Response<ReadNotificationResponse> response) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);

                if (response.isSuccessful() && response.body()!=null) {
                    ReadNotificationResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        resultReadNotification.setValue(new ApiResponse<>(SUCCESS, result, null));
                    }else {
                        resultReadNotification.setValue(new ApiResponse<>(FAILURE, null, result.getMessage()));
                    }
                }else{
                    String errorMsg = ErrorHandler.reportError(response.code());
                    resultReadNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
                }

            }

            @Override
            public void onFailure(@NonNull Call<ReadNotificationResponse> call, @NonNull Throwable error) {
                isLoading.setValue(false);
                isViewEnabled.setValue(true);
                String errorMsg = ErrorHandler.reportError(error);
                resultReadNotification.setValue(new ApiResponse<>(FAILURE, null, errorMsg));
            }
        });

    }
}


