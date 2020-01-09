package com.telemed.doctor.consult.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AppointmentListResponse {

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

        @SerializedName("appointmentList")
        @Expose
        private List<Appointment> appointmentList = null;

        public List<Appointment> getAppointmentList() {
            return appointmentList;
        }

        public void setAppointmentList(List<Appointment> appointmentList) {
            this.appointmentList = appointmentList;
        }

    }
}
