package com.telemed.doctor.consult.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment implements Parcelable {

    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("appointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("specialityId")
    @Expose
    private Integer specialityId;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;

    public Integer getApiKey() {
        return apiKey;
    }

    public void setApiKey(Integer apiKey) {
        this.apiKey = apiKey;
    }

    @SerializedName("sessionId")
    @Expose
    private String sessionId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("apiKey")
    @Expose
    private Integer apiKey;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.appointmentId);
        dest.writeString(this.userId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.gender);
        dest.writeString(this.appointmentDate);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeValue(this.specialityId);
        dest.writeString(this.profilePic);
        dest.writeString(this.sessionId);
        dest.writeString(this.token);
        dest.writeValue(this.apiKey);
    }

    public Appointment() {
    }

    protected Appointment(Parcel in) {
        this.appointmentId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.gender = in.readString();
        this.appointmentDate = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.specialityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.profilePic = in.readString();
        this.sessionId = in.readString();
        this.token = in.readString();
        this.apiKey = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Appointment> CREATOR = new Parcelable.Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}
