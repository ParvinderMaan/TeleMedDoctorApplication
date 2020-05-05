package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditDayScheduleRequest {

    @SerializedName("timeDiff")
    @Expose
    private Integer timeDiff;
    @SerializedName("timeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("timeTo")
    @Expose
    private String timeTo;

    public Integer getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(Integer timeDiff) {
        this.timeDiff = timeDiff;
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



}
