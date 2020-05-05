package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeekScheduleResponse {

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

        @SerializedName("weekDayTimeSlots")
        @Expose
        private List<WeekDayTimeSlot> weekDayTimeSlots = null;

        public List<WeekDayTimeSlot> getWeekDayTimeSlots() {
            return weekDayTimeSlots;
        }

        public void setWeekDayTimeSlots(List<WeekDayTimeSlot> weekDayTimeSlots) {
            this.weekDayTimeSlots = weekDayTimeSlots;
        }
    }

    public static class WeekDayTimeSlot {

        @SerializedName("weekDayDetail")
        @Expose
        private List<WeekDayDetail> weekDayDetail = null;
        @SerializedName("timeSlots")
        @Expose
        private List<TimeSlot> timeSlots = null;

        public List<WeekDayDetail> getWeekDayDetail() {
            return weekDayDetail;
        }

        public void setWeekDayDetail(List<WeekDayDetail> weekDayDetail) {
            this.weekDayDetail = weekDayDetail;
        }

        public List<TimeSlot> getTimeSlots() {
            return timeSlots;
        }

        public void setTimeSlots(List<TimeSlot> timeSlots) {
            this.timeSlots = timeSlots;
        }

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


    public static class WeekDayDetail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("weekDay")
        @Expose
        private Integer weekDay;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(Integer weekDay) {
            this.weekDay = weekDay;
        }

    }
}
