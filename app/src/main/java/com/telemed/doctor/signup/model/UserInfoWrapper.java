package com.telemed.doctor.signup.model;

import android.os.Parcel;
import android.os.Parcelable;



public class UserInfoWrapper implements Parcelable {
    private String email;
    private Integer otpCode;
    private String accessToken;
    private Integer lastScreenId;
    private Boolean emailConfirmed;

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
    }

    protected UserInfoWrapper(Parcel in) {
        this.email = in.readString();
        this.otpCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accessToken = in.readString();
        this.lastScreenId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.emailConfirmed = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserInfoWrapper> CREATOR = new Parcelable.Creator<UserInfoWrapper>() {
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

