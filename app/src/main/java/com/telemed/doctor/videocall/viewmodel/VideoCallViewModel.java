package com.telemed.doctor.videocall.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class VideoCallViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isAllPermGranted;
    private MutableLiveData<Boolean> isDeviceSettingVisited;
    private MutableLiveData<Boolean> isPermissionLayoutVisible;  // hide other view
    private MutableLiveData<Boolean> isMainLayoutVisible,isTopBottomLayoutVisible;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isCallWaiting;

    public MutableLiveData<Boolean> getTopBottomLayoutVisibility() {
        return isTopBottomLayoutVisible;
    }
    public Boolean getTopBottomLayoutVisibilityVal() {
        return isTopBottomLayoutVisible.getValue();
    }

    public void setTopBottomLayoutVisible(Boolean isTopBottomLayoutVisible) {
        this.isTopBottomLayoutVisible.setValue(isTopBottomLayoutVisible);
    }

    public VideoCallViewModel(@NonNull Application application) {
        super(application);
        isAllPermGranted=new MutableLiveData<>();
        isPermissionLayoutVisible=new MutableLiveData<>();
        isMainLayoutVisible=new MutableLiveData<>();
        isDeviceSettingVisited=new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
        isCallWaiting =new MutableLiveData<>();
        isTopBottomLayoutVisible=new MutableLiveData<>();

    //-----------------------------------------------------------

    }


    public MutableLiveData<Boolean> getPermissionLayoutVisible() {
        return isPermissionLayoutVisible;
    }


    public void setPermissionLayoutVisibility(Boolean isPermissionLayoutVisible) {
        this.isPermissionLayoutVisible.setValue(isPermissionLayoutVisible);
    }

    public MutableLiveData<Boolean> getPermGrantedStatus() {
        return isAllPermGranted;
    }

    public MutableLiveData<Boolean> getDeviceSettingVisitedStatus() {
        return isDeviceSettingVisited;
    }

    public MutableLiveData<Boolean> getMainLayoutVisibility() {
        return isMainLayoutVisible;
    }


    public void setMainLayoutVisible(Boolean isMainLayoutVisible) {
        this.isMainLayoutVisible.setValue(isMainLayoutVisible);
    }

    public Boolean getDeviceSettingVisitedStatusValue() {
        return isDeviceSettingVisited.getValue();
    }

    public void setDeviceSettingVisited(Boolean isDeviceSettingVisited) {
        this.isDeviceSettingVisited.setValue(isDeviceSettingVisited);
    }

    public void setAllPermGranted(Boolean isAllPermGranted) {
        this.isAllPermGranted.setValue(isAllPermGranted);
    }

    public MutableLiveData<Boolean> getCallWaitingStatus() {
        return isCallWaiting;
    }

    public void setCallWaitingStatus(Boolean isOtherUsrLive) {
        this.isCallWaiting.setValue(isOtherUsrLive);
    }

    public void setProgress(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public Boolean getPermGrantedStatusValue() {
        return isAllPermGranted.getValue();
    }

    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }


    public Boolean getMainLayoutVisibilityValue() {
        return isMainLayoutVisible.getValue();
    }
}
