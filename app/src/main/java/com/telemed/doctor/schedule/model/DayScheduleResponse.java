package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayScheduleResponse {
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

        @SerializedName("availableTimes")
        @Expose
        private List<DayAvailabilityModel> availableTimes = null;

        public List<DayAvailabilityModel> getAvailableTimes() {
            return availableTimes;
        }

        public void setAvailableTimes(List<DayAvailabilityModel> availableTimes) {
            this.availableTimes = availableTimes;
        }

    }

}