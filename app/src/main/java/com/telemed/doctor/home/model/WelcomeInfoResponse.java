package com.telemed.doctor.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telemed.doctor.home.model.DashboardInfo;

public class WelcomeInfoResponse {
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

        @SerializedName("dashboardInfo")
        @Expose
        private DashboardInfo dashboardInfo;
        @SerializedName("serverTime")
        @Expose
        private String serverTime;

        public DashboardInfo getDashboardInfo() {
            return dashboardInfo;
        }

        public void setDashboardInfo(DashboardInfo dashboardInfo) {
            this.dashboardInfo = dashboardInfo;
        }

        public String getServerTime() {
            return serverTime;
        }

        public void setServerTime(String serverTime) {
            this.serverTime = serverTime;
        }

    }
}
