package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeekScheduleRequest {
    @SerializedName("recSecId")
    @Expose
    private Integer recSecId;
    @SerializedName("weekDays")
    @Expose
    private List<Integer> weekDays = null;
    @SerializedName("fromTime")
    @Expose
    private String fromTime;
    @SerializedName("toTime")
    @Expose
    private String toTime;

    public Integer getRecSecId() {
        return recSecId;
    }

    public void setRecSecId(Integer recSecId) {
        this.recSecId = recSecId;
    }

    public List<Integer> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<Integer> weekDays) {
        this.weekDays = weekDays;
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
}
