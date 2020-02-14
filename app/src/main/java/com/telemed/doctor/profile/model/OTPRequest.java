package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telemed.doctor.password.model.VerficationRequest;

public class OTPRequest {
    private transient  int id;

    @SerializedName("otp")
    @Expose
    private Integer otp;

    private OTPRequest(Integer otp) {
        super();
        this.id = generateId();
        this.otp = otp;


    }

    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }


    static public class Builder {
        private Integer otp;

        public Builder() {}

        public OTPRequest.Builder setOtp(Integer otp) {
            this.otp = otp;
            return this;
        }

        public OTPRequest build() {
            OTPRequest in = new OTPRequest(otp);
            return in;
        }
    }
}
