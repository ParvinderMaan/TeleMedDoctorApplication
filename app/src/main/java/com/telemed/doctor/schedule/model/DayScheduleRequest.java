package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayScheduleRequest {

    @SerializedName("availableDays")
    @Expose
    private List<Integer> availableDays = null;
    @SerializedName("availableDates")
    @Expose
    private List<String> availableDates = null;
    @SerializedName("isAvailable")
    @Expose
    private Boolean isAvailable;
    @SerializedName("timeSlots")
    @Expose
    private List<TimeSlot> timeSlots = null;

    public List<Integer> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<Integer> availableDays) {
        this.availableDays = availableDays;
    }

    public List<String> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<String> availableDates) {
        this.availableDates = availableDates;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }


    public static class TimeSlot {

        @SerializedName("fromTime")
        @Expose
        private String fromTime;
        @SerializedName("toTime")
        @Expose
        private String toTime;
        @SerializedName("timeDiff")
        @Expose
        private Integer timeDiff;

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

        public Integer getTimeDiff() {
            return timeDiff;
        }

        public void setTimeDiff(Integer timeDiff) {
            this.timeDiff = timeDiff;
        }
    }
}
