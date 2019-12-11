package com.telemed.doctor.signup.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telemed.doctor.signup.model.SignUpIResponse;

public class OtpWrapper {
    @Expose(serialize = false, deserialize = false)
    private String email;

    @SerializedName("otpCode")
    @Expose
    private Integer otpCode;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

