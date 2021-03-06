package com.telemed.doctor.setting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.setting.model.TimeZone;
import com.telemed.doctor.setting.model.UserSetting;

import java.util.List;

public class UserSettingResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("userSettings")
        @Expose
        private UserSetting userSettings;
        @SerializedName("appLanguagesList")
        @Expose
        private List<Language> appLanguagesList = null;



        @SerializedName("timeZonesList")
        @Expose
        private List<TimeZone> timeZonesList = null;

        public List<TimeZone> getTimeZonesList() {
            return timeZonesList;
        }

        public void setTimeZonesList(List<TimeZone> timeZonesList) {
            this.timeZonesList = timeZonesList;
        }

        public UserSetting getUserSetting() {
            return userSettings;
        }

        public void setUserSetting(UserSetting userSettings) {
            this.userSettings = userSettings;
        }

        public List<Language> getAppLanguagesList() {
            return appLanguagesList;
        }

        public void setAppLanguagesList(List<Language> appLanguagesList) {
            this.appLanguagesList = appLanguagesList;
        }

    }
}
