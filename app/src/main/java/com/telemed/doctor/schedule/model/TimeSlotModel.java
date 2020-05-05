package com.telemed.doctor.schedule.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotModel implements Parcelable {
    @SerializedName("appoinmentDate")
    @Expose
    private String appoinmentDate;
    @SerializedName("timeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("timeTo")
    @Expose
    private String timeTo;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("appointmentStatus")
    @Expose
    private Integer appointmentStatus;

    public TimeSlotModel() {
    }

    public TimeSlotModel(Parcel in) {
        appoinmentDate = in.readString();
        timeFrom = in.readString();
        timeTo = in.readString();
        patientId = in.readString();
        if (in.readByte() == 0) {
            appointmentStatus = null;
        } else {
            appointmentStatus = in.readInt();
        }
    }

    public static final Creator<TimeSlotModel> CREATOR = new Creator<TimeSlotModel>() {
        @Override
        public TimeSlotModel createFromParcel(Parcel in) {
            return new TimeSlotModel(in);
        }

        @Override
        public TimeSlotModel[] newArray(int size) {
            return new TimeSlotModel[size];
        }
    };

    public String getAppoinmentDate() {
        return appoinmentDate;
    }

    public void setAppoinmentDate(String appoinmentDate) {
        this.appoinmentDate = appoinmentDate;
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Integer getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(Integer appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appoinmentDate);
        dest.writeString(timeFrom);
        dest.writeString(timeTo);
        dest.writeString(patientId);
        if (appointmentStatus == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(appointmentStatus);
        }
    }
}
