package com.telemed.doctor.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardInfo {
    @SerializedName("pendingAppointmentsCount")
    @Expose
    private Integer pendingAppointmentsCount;
    @SerializedName("upcomingAppointmentsCount")
    @Expose
    private Integer upcomingAppointmentsCount;
    @SerializedName("notificationsCount")
    @Expose
    private Integer notificationsCount;
    @SerializedName("iosAppVersion")
    @Expose
    private String iosAppVersion;
    @SerializedName("androidAppVersion")
    @Expose
    private String androidAppVersion;

    public Integer getPendingAppointmentsCount() {
        return pendingAppointmentsCount;
    }

    public void setPendingAppointmentsCount(Integer pendingAppointmentsCount) {
        this.pendingAppointmentsCount = pendingAppointmentsCount;
    }

    public Integer getUpcomingAppointmentsCount() {
        return upcomingAppointmentsCount;
    }

    public void setUpcomingAppointmentsCount(Integer upcomingAppointmentsCount) {
        this.upcomingAppointmentsCount = upcomingAppointmentsCount;
    }

    public Integer getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(Integer notificationsCount) {
        this.notificationsCount = notificationsCount;
    }

    public String getIosAppVersion() {
        return iosAppVersion;
    }

    public void setIosAppVersion(String iosAppVersion) {
        this.iosAppVersion = iosAppVersion;
    }

    public String getAndroidAppVersion() {
        return androidAppVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        this.androidAppVersion = androidAppVersion;
    }

}
