package com.telemed.doctor.schedule.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotModel implements Parcelable {

    @SerializedName("doctorId")
    @Expose
    private String doctorId;

    @Override
    public String toString() {
        return "TimeSlotModel{" +
                "doctorId='" + doctorId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", slotFrom='" + slotFrom + '\'' +
                ", slotTo='" + slotTo + '\'' +
                ", appointmentStatus=" + appointmentStatus +
                ", appointmentId=" + appointmentId +
                '}';
    }

    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("appointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("slotFrom")
    @Expose
    private String slotFrom;
    @SerializedName("slotTo")
    @Expose
    private String slotTo;
    @SerializedName("appointmentStatus")
    @Expose
    private Integer appointmentStatus;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getSlotFrom() {
        return slotFrom;
    }

    public void setSlotFrom(String slotFrom) {
        this.slotFrom = slotFrom;
    }

    public String getSlotTo() {
        return slotTo;
    }

    public void setSlotTo(String slotTo) {
        this.slotTo = slotTo;
    }

    public Integer getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(Integer appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.doctorId);
        dest.writeString(this.patientId);
        dest.writeString(this.patientName);
        dest.writeString(this.appointmentDate);
        dest.writeString(this.slotFrom);
        dest.writeString(this.slotTo);
        dest.writeValue(this.appointmentStatus);
        dest.writeValue(this.appointmentId);
    }

    public TimeSlotModel() {
    }

    protected TimeSlotModel(Parcel in) {
        this.doctorId = in.readString();
        this.patientId = in.readString();
        this.patientName = in.readString();
        this.appointmentDate = in.readString();
        this.slotFrom = in.readString();
        this.slotTo = in.readString();
        this.appointmentStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.appointmentId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TimeSlotModel> CREATOR = new Parcelable.Creator<TimeSlotModel>() {
        @Override
        public TimeSlotModel createFromParcel(Parcel source) {
            return new TimeSlotModel(source);
        }

        @Override
        public TimeSlotModel[] newArray(int size) {
            return new TimeSlotModel[size];
        }
    };
}
