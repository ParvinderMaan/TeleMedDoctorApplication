package com.telemed.doctor.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadNotificationResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ReadNotificationResponse.Data data;

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

    public ReadNotificationResponse.Data getData() {
        return data;
    }

    public void setData(ReadNotificationResponse.Data data) {
        this.data = data;
    }

    public static class Data {
    }

}