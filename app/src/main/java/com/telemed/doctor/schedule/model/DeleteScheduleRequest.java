package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteScheduleRequest {

    @SerializedName("selectedDays")
    @Expose
    private List<Integer> selectedDays = null;
    @SerializedName("selectedDates")
    @Expose
    private List<String> selectedDates = null;

    public List<Integer> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(List<Integer> selectedDays) {
        this.selectedDays = selectedDays;
    }

    public List<String> getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(List<String> selectedDates) {
        this.selectedDates = selectedDates;
    }
}
