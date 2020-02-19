package com.telemed.doctor.schedule.model;

public class TimeSlotModel {

    private String id;
    private int status ; //0,1,2
    private String firmName;

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getFirmName() {
        return firmName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getPatientName() {
        return patientName;
    }

    public TimeSlotModel(String id, int status, String firmName, String timeSlot, String patientName) {
        this.id = id;
        this.status = status;
        this.firmName = firmName;
        this.timeSlot = timeSlot;
        this.patientName = patientName;
    }

    private String timeSlot;
    private String patientName;

}
