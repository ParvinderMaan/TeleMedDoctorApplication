package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankInfoRequest {
    private transient int id;

    @SerializedName("routingNumber")
    @Expose
    private String routingNumber;
    @SerializedName("accountNumber")
    @Expose
    private Long accountNumber;
    @SerializedName("cardNumber")
    @Expose
    private Long cardNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postCode")
    @Expose
    private String postCode;

    private BankInfoRequest(String routingNumber, Long accountNumber, Long cardNumber, String address, String city, String postCode) {
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


    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    static public class Builder {
        private String routingNumber;
        private Long accountNumber;
        private Long cardNumber;
        private String address;
        private String city;
        private String postCode;

        public Builder() {
        }

        public Builder setCardNumber(Long cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

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

        public BankInfoRequest build() {
            BankInfoRequest in = new BankInfoRequest(routingNumber, accountNumber, cardNumber, address, city, postCode);
            return in;
        }

    }
}
