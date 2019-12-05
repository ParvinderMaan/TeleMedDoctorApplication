package com.telemed.doctor.signin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInRequest {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("discriminator")
    @Expose
    private String discriminator;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }


    @Override
    public String toString() {
        return "SignInRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", discriminator='" + discriminator + '\'' +
                '}';
    }
}
