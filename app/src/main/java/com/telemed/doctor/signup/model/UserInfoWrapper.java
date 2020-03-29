package com.telemed.doctor.signup.model;

import android.os.Parcel;
import android.os.Parcelable;



public class UserInfoWrapper implements Parcelable {
    private String email;
    private Integer otpCode;
    private String accessToken;
    private Integer lastScreenId;
    private Boolean emailConfirmed;
    private String firstName;
    private String lastName;
    private String profilePic;
    private String oldPassword;
    private String patientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public Integer getLastScreenId() {
        return lastScreenId;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public void setLastScreenId(Integer lastScreenId) {
        this.lastScreenId = lastScreenId;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(Integer otpCode) {
        this.otpCode = otpCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public UserInfoWrapper() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeValue(this.otpCode);
        dest.writeString(this.accessToken);
        dest.writeValue(this.lastScreenId);
        dest.writeValue(this.emailConfirmed);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.profilePic);
        dest.writeString(this.oldPassword);
        dest.writeString(this.patientId);
        dest.writeString(this.type);
    }

    protected UserInfoWrapper(Parcel in) {
        this.email = in.readString();
        this.otpCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accessToken = in.readString();
        this.lastScreenId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.emailConfirmed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.profilePic = in.readString();
        this.oldPassword = in.readString();
        this.patientId = in.readString();
        this.type = in.readString();
    }

    public static final Creator<UserInfoWrapper> CREATOR = new Creator<UserInfoWrapper>() {
        @Override
        public UserInfoWrapper createFromParcel(Parcel source) {
            return new UserInfoWrapper(source);
        }

        @Override
        public UserInfoWrapper[] newArray(int size) {
            return new UserInfoWrapper[size];
        }
    };
}

