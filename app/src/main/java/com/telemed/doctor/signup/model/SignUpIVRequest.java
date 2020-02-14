package com.telemed.doctor.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpIVRequest {
    private transient int id;

    @SerializedName("routingNumber")
    @Expose
    private String routingNumber;
    @SerializedName("accountNumber")
    @Expose
    private Long accountNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postCode")
    @Expose
    private String postCode;

    public SignUpIVRequest(String routingNumber, Long accountNumber, String address, String city, String postCode) {
        super();
        this.id = generateId();
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
    }
    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }



    static public class Builder {
        private String routingNumber;
        private Long accountNumber;
        private String address;
        private String city;
        private String postCode;
        public Builder() { }

        public Builder setRoutingNumber(String routingNumber) {
            this.routingNumber = routingNumber;
            return this;
        }

        public Builder setAccountNumber(Long accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public SignUpIVRequest build() {
            SignUpIVRequest in=new SignUpIVRequest( routingNumber,  accountNumber,  address,  city,  postCode);
            return in;
        }


    }

}
