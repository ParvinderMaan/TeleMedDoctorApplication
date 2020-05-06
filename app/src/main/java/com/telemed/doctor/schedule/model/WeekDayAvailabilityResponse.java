package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeekDayAvailabilityResponse {
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


    public static class AvailableTime {

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("appoinmentDate")
        @Expose
        private String appoinmentDate;
        @SerializedName("timeFrom")
        @Expose
        private String timeFrom;
        @SerializedName("timeTo")
        @Expose
        private String timeTo;
        @SerializedName("patientId")
        @Expose
        private String patientId;
        @SerializedName("appointmentStatus")
        @Expose
        private Integer appointmentStatus;

        public String getAppoinmentDate() {
            return appoinmentDate;
        }

        public void setAppoinmentDate(String appoinmentDate) {
            this.appoinmentDate = appoinmentDate;
        }

        public String getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(String timeFrom) {
            this.timeFrom = timeFrom;
        }

        public String getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(String timeTo) {
            this.timeTo = timeTo;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public Integer getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(Integer appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

    }

    public static class Data {

        @SerializedName("availableTimes")
        @Expose
        private List<AvailableTime> availableTimes = null;

        public List<AvailableTime> getAvailableTimes() {
            return availableTimes;
        }

        public void setAvailableTimes(List<AvailableTime> availableTimes) {
            this.availableTimes = availableTimes;
        }

    }
}
