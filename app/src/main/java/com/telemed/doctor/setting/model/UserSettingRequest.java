package com.telemed.doctor.setting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSettingRequest {
    @SerializedName("pushNotificationStatus")
    @Expose
    private Boolean pushNotificationStatus;
    @SerializedName("emailNotificationStatus")
    @Expose
    private Boolean emailNotificationStatus;
    @SerializedName("smsNotificationStatus")
    @Expose
    private Boolean smsNotificationStatus;
    @SerializedName("currentLanguageId")
    @Expose
    private Integer currentLanguageId;

    @SerializedName("timeZoneId")
    @Expose
    private Integer timeZoneId;

    public static UserSettingRequest builder() {
        return new UserSettingRequest();
    }

    public Boolean getPushNotificationStatus() {
        return pushNotificationStatus;
    }

    public UserSettingRequest setPushNotificationStatus(Boolean pushNotificationStatus) {
        this.pushNotificationStatus = pushNotificationStatus;
        return this;
    }

    public Boolean getEmailNotificationStatus() {
        return emailNotificationStatus;
    }

    public UserSettingRequest setEmailNotificationStatus(Boolean emailNotificationStatus) {
        this.emailNotificationStatus = emailNotificationStatus;
        return this;
    }

    public Boolean getSmsNotificationStatus() {
        return smsNotificationStatus;
    }

    public UserSettingRequest setSmsNotificationStatus(Boolean smsNotificationStatus) {
        this.smsNotificationStatus = smsNotificationStatus;
        return this;
    }

    public Integer getCurrentLanguageId() {
        return currentLanguageId;
    }

    public UserSettingRequest setCurrentLanguageId(Integer currentLanguageId) {
        this.currentLanguageId = currentLanguageId;
        return this;
    }

    public Integer getTimeZoneId() {
        return timeZoneId;
    }



    public UserSettingRequest setCurrentTimeZone(Integer timeZoneId) {
        this.timeZoneId = timeZoneId;
        return this;
    }
}
