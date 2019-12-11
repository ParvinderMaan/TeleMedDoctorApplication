package com.telemed.doctor.signin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInRequest {
    private transient  int id;

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

    public SignInRequest(String email, String password, String deviceId, String deviceType, String discriminator) {
        super();
        this.id = generateId();
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.discriminator = discriminator;
    }

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

    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }

    static public class Builder {
        private String email;
        private String password;
        private String deviceId;
        private String deviceType;
        private String discriminator;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setDeviceType(String deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public Builder setDiscriminator(String discriminator) {
            this.discriminator = discriminator;
            return this;
        }

        public Builder() {}

        public SignInRequest build() {
            SignInRequest in = new SignInRequest(email, password, deviceId,deviceType,discriminator);
            return in;
        }
    }

}
