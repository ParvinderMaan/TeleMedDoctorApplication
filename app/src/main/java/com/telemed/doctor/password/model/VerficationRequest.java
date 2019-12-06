package com.telemed.doctor.password.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerficationRequest {
    private transient  int id;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    private VerficationRequest(String email, Integer otp) {
        super();
        this.id = generateId();
        this.email = email;
        this.otp = otp;


    }

    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }


    static public class Builder {
        private String email;
        private Integer otp;

        public Builder() {}
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setOtp(Integer otp) {
            this.otp = otp;
            return this;
        }

        public VerficationRequest build() {
            VerficationRequest in = new VerficationRequest(email, otp);
            return in;
        }
    }
}
