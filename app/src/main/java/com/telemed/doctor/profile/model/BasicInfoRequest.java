package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicInfoRequest {
    private transient int id;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("primaryLanguageId")
    @Expose
    private Integer primaryLanguageId;

    @SerializedName("secondaryLanguageId")
    @Expose
    private Integer secondaryLanguageId;

    @SerializedName("address1")
    @Expose
    private String address1;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("countryId")
    @Expose
    private Integer countryId;

    @SerializedName("stateId")
    @Expose
    private Integer stateId;

    private BasicInfoRequest(String phoneNumber, Integer primaryLanguageId, Integer secondaryLanguageId, String address1, String city, Integer countryId ,Integer stateId) {
        super();
        this.id = generateId();
        this.phoneNumber = phoneNumber;
        this.primaryLanguageId = primaryLanguageId;
        this.secondaryLanguageId = secondaryLanguageId;
        this.address1 = address1;
        this.city = city;
        this.countryId = countryId;
        this.stateId = stateId;
    }

    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPrimaryLanguageId() {
        return primaryLanguageId;
    }

    public void setPrimaryLanguageId(Integer primaryLanguageId) {
        this.primaryLanguageId = primaryLanguageId;
    }

    public Integer getSecondaryLanguageId() {
        return secondaryLanguageId;
    }

    public void setSecondaryLanguageId(Integer secondaryLanguageId) {
        this.secondaryLanguageId = secondaryLanguageId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }


    static public class Builder {

        private String phoneNumber;
        private Integer primaryLanguageId;
        private Integer secondaryLanguageId;
        private String address1;
        private String city;
        private Integer countryId;
        private Integer stateId;

        public Builder() {
        }

        public BasicInfoRequest.Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public BasicInfoRequest.Builder setPrimaryLanguageId(Integer primaryLanguageId) {
            this.primaryLanguageId = primaryLanguageId;
            return this;
        }

        public BasicInfoRequest.Builder setsecondaryLanguageId(Integer secondaryLanguageId) {
            this.secondaryLanguageId = secondaryLanguageId;
            return this;
        }

        public BasicInfoRequest.Builder setAddress(String address1) {
            this.address1 = address1;
            return this;
        }

        public BasicInfoRequest.Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public BasicInfoRequest.Builder setCountryId(Integer countryId) {
            this.countryId = countryId;
            return this;
        }

        public BasicInfoRequest.Builder setStateId(Integer stateId) {
            this.stateId = stateId;
            return this;
        }

        public BasicInfoRequest build() {
            BasicInfoRequest in = new BasicInfoRequest(phoneNumber, primaryLanguageId, secondaryLanguageId, address1, city, countryId ,stateId);
            return in;
        }

    }
}
