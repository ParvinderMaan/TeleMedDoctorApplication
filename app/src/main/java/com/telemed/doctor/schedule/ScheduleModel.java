package com.telemed.doctor.schedule;

import java.util.List;

public class ScheduleModel {

    private List<String> availableDays;
    private List<String> bookedDays;

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public List<String> getBookedDays() {
        return bookedDays;
    }

    public void setBookedDays(List<String> bookedDays) {
        this.bookedDays = bookedDays;
    }
}
