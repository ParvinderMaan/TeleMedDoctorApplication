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

        @SerializedName("returnTimeSlots")
        @Expose
        private List<ReturnTimeSlot> returnTimeSlots = null;

        public List<ReturnTimeSlot> getReturnTimeSlots() {
            return returnTimeSlots;
        }

        public void setReturnTimeSlots(List<ReturnTimeSlot> returnTimeSlots) {
            this.returnTimeSlots = returnTimeSlots;
        }

    }

    public class ReturnTimeSlot {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("scheduleDay")
        @Expose
        private Integer scheduleDay;
        @SerializedName("timeSlotList")
        @Expose
        private List<TimeSlotList> timeSlotList = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getScheduleDay() {
            return scheduleDay;
        }

        public void setScheduleDay(Integer scheduleDay) {
            this.scheduleDay = scheduleDay;
        }

        public List<TimeSlotList> getTimeSlotList() {
            return timeSlotList;
        }

        public void setTimeSlotList(List<TimeSlotList> timeSlotList) {
            this.timeSlotList = timeSlotList;
        }

    }


    public static class TimeSlotList {

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
