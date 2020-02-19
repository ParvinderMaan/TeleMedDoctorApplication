package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllWeekSchedule {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("doctorId")
    @Expose
    private String doctorId;
    @SerializedName("fromTime")
    @Expose
    private String fromTime;
    @SerializedName("toTime")
    @Expose
    private String toTime;
    @SerializedName("weekDays")
    @Expose
    private List<WeekDay> weekDays = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public List<WeekDay> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<WeekDay> weekDays) {
        this.weekDays = weekDays;
    }

    public static class WeekDay {

        @SerializedName("weekDay")
        @Expose
        private Integer weekDay;
        @SerializedName("recSecId")
        @Expose
        private Integer recSecId;

        public Integer getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(Integer weekDay) {
            this.weekDay = weekDay;
        }

        public Integer getRecSecId() {
            return recSecId;
        }

        public void setRecSecId(Integer recSecId) {
            this.recSecId = recSecId;
        }

    }
}
