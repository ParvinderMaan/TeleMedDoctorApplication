package com.telemed.doctor.appointment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telemed.doctor.appointment.AppointmentStatus;

public class AppointmentProcessRequest {

    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("appointmentStatus")
    @Expose
    private Integer appointmentStatus;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(@AppointmentStatus Integer appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }
}
