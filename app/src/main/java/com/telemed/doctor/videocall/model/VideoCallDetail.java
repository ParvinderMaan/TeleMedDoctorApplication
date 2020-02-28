package com.telemed.doctor.videocall.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCallDetail implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("doctorId")
    @Expose
    private String doctorId;
    @SerializedName("sessionId")
    @Expose
    private String sessionId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("expireTime")
    @Expose
    private Double expireTime;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("endAt")
    @Expose
    private String endAt;
    @SerializedName("ratings")
    @Expose
    private Integer ratings;
    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;

    //!!!
    @Expose(serialize = false,deserialize = false)
    private Integer openTokApiKey;

    //!!!
    @Expose(serialize = false,deserialize = false)
    private String firstName;

    //!!!
    @Expose(serialize = false,deserialize = false)
    private String lastName;


    //!!!
    @Expose(serialize = false,deserialize = false)
    private String profilePic;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getOpenTokApiKey() {
        return openTokApiKey;
    }

    public void setOpenTokApiKey(Integer openTokApiKey) {
        this.openTokApiKey = openTokApiKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Double expireTime) {
        this.expireTime = expireTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }



    public VideoCallDetail() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.appointmentId);
        dest.writeString(this.patientId);
        dest.writeString(this.doctorId);
        dest.writeString(this.sessionId);
        dest.writeString(this.token);
        dest.writeValue(this.expireTime);
        dest.writeString(this.createdAt);
        dest.writeString(this.endAt);
        dest.writeValue(this.ratings);
        dest.writeValue(this.isCompleted);
        dest.writeValue(this.openTokApiKey);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.profilePic);
    }

    protected VideoCallDetail(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.appointmentId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.patientId = in.readString();
        this.doctorId = in.readString();
        this.sessionId = in.readString();
        this.token = in.readString();
        this.expireTime = (Double) in.readValue(Double.class.getClassLoader());
        this.createdAt = in.readString();
        this.endAt = in.readString();
        this.ratings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isCompleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.openTokApiKey = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.profilePic = in.readString();
    }

    public static final Parcelable.Creator<VideoCallDetail> CREATOR = new Parcelable.Creator<VideoCallDetail>() {
        @Override
        public VideoCallDetail createFromParcel(Parcel source) {
            return new VideoCallDetail(source);
        }

        @Override
        public VideoCallDetail[] newArray(int size) {
            return new VideoCallDetail[size];
        }
    };
}
