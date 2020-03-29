package com.telemed.doctor.setting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSetting {

    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("currentLanguage")
    @Expose
    private String currentLanguage;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getPushNotificationStatus() {
        return pushNotificationStatus;
    }

    public void setPushNotificationStatus(Boolean pushNotificationStatus) {
        this.pushNotificationStatus = pushNotificationStatus;
    }

    public Boolean getEmailNotificationStatus() {
        return emailNotificationStatus;
    }

    public void setEmailNotificationStatus(Boolean emailNotificationStatus) {
        this.emailNotificationStatus = emailNotificationStatus;
    }

    public Boolean getSmsNotificationStatus() {
        return smsNotificationStatus;
    }

    public void setSmsNotificationStatus(Boolean smsNotificationStatus) {
        this.smsNotificationStatus = smsNotificationStatus;
    }

    public Integer getCurrentLanguageId() {
        return currentLanguageId;
    }

    public void setCurrentLanguageId(Integer currentLanguageId) {
        this.currentLanguageId = currentLanguageId;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public Integer getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(Integer timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @SerializedName("timeZoneId")
    @Expose
    private Integer timeZoneId;

    @SerializedName("timeZone")
    @Expose
    private String timeZone;

}
