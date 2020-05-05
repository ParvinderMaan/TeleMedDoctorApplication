package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMonthSchedule {
//    @SerializedName("doctorId")
//    @Expose
//    private String doctorId;
//    @SerializedName("date")
//    @Expose
//    private String date;
//    @SerializedName("fromTime")
//    @Expose
//    private String fromTime;
//    @SerializedName("toTime")
//    @Expose
//    private String toTime;
    @SerializedName("dateValue")
    @Expose
    private String dateValue;

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    @SerializedName("isPendingAppointment")
    @Expose
    private Boolean anyPendingAppointment;

//    public String getDoctorId() {
//        return doctorId;
//    }
//
//    public void setDoctorId(String doctorId) {
//        this.doctorId = doctorId;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getFromTime() {
//        return fromTime;
//    }
//
//    public void setFromTime(String fromTime) {
//        this.fromTime = fromTime;
//    }
//
//
//    public String getToTime() {
//        return toTime;
//    }
//
//    public void setToTime(String toTime) {
//        this.toTime = toTime;
//    }
//
//    public String getDayName() {
//        return dayName;
//    }
//
//    public void setDayName(String dayName) {
//        this.dayName = dayName;
//    }

    public Boolean getAnyPendingAppointment() {
        return anyPendingAppointment;
    }

    public void setAnyPendingAppointment(Boolean anyPendingAppointment) {
        this.anyPendingAppointment = anyPendingAppointment;
    }

}
