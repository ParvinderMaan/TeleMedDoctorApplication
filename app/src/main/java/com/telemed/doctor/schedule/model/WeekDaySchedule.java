package com.telemed.doctor.schedule.model;

import java.util.List;

public class WeekDaySchedule {

    List<WeekScheduleResponse.WeekDayDetail> weekDayDetail= null;

    public List<WeekScheduleResponse.WeekDayDetail> getWeekDayDetail() {
        return weekDayDetail;
    }

    public void setWeekDayDetail(List<WeekScheduleResponse.WeekDayDetail> weekDayDetail) {
        this.weekDayDetail = weekDayDetail;
    }

    private List<WeekScheduleResponse.TimeSlot> timeSlots = null;



    public List<WeekScheduleResponse.TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<WeekScheduleResponse.TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

}
