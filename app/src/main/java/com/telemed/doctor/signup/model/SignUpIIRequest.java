package com.telemed.doctor.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpIIRequest {
    private transient int id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("birthCity")
    @Expose
    private String birthCity;
    @SerializedName("birthCountry")
    @Expose
    private String birthCountry;
    @SerializedName("nationalityId")
    @Expose
    private Integer nationalityId;
    @SerializedName("genderId")
    @Expose
    private Integer genderId;
    @SerializedName("specialityId")
    @Expose
    private Integer specialityId;
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
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("prefAddress")
    @Expose
    private String prefAddress;


    public SignUpIIRequest(String firstName, String lastName, String dateOfBirth, String birthCity,
                           String birthCountry, Integer nationalityId, Integer genderId,
                           Integer specialityId, Integer primaryLanguageId, Integer secondaryLanguageId,
                           String address1, String city, Integer countryId, Integer stateId, String postalCode,
                           String prefAddress) {
        super();
        this.id = generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.birthCity = birthCity;
        this.birthCountry = birthCountry;
        this.nationalityId = nationalityId;
        this.genderId = genderId;
        this.specialityId = specialityId;
        this.primaryLanguageId = primaryLanguageId;
        this.secondaryLanguageId = secondaryLanguageId;
        this.address1 = address1;
        this.city = city;
        this.countryId = countryId;
        this.stateId = stateId;
        this.postalCode = postalCode;
        this.prefAddress = prefAddress;
    }

    @Override
    public String toString() {
        return "SignUpIIRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", birthCity='" + birthCity + '\'' +
                ", birthCountry='" + birthCountry + '\'' +
                ", nationalityId=" + nationalityId +
                ", genderId=" + genderId +
                ", specialityId=" + specialityId +
                ", primaryLanguageId=" + primaryLanguageId +
                ", secondaryLanguageId=" + secondaryLanguageId +
                ", address1='" + address1 + '\'' +
                ", city='" + city + '\'' +
                ", countryId=" + countryId +
                ", stateId=" + stateId +
                ", postalCode='" + postalCode + '\'' +
                ", prefAddress='" + prefAddress + '\'' +
                '}';
    }

    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }


    static public class Builder {
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String birthCity;
        private String birthCountry;
        private Integer nationalityId;
        private Integer genderId;
        private Integer specialityId;
        private Integer primaryLanguageId;
        private Integer secondaryLanguageId;
        private String address1;
        private String city;
        private Integer countryId;
        private Integer stateId;
        private String postalCode;
        private String prefAddress;

        public Builder() {
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder setBirthCity(String birthCity) {
            this.birthCity = birthCity;
            return this;
        }

        public Builder setBirthCountry(String birthCountry) {
            this.birthCountry = birthCountry;
            return this;
        }

        public Builder setNationalityId(Integer nationalityId) {
            this.nationalityId = nationalityId;
            return this;
        }

        public Builder setGenderId(Integer genderId) {
            this.genderId = genderId;
            return this;
        }

        public Builder setSpecialityId(Integer specialityId) {
            this.specialityId = specialityId;
            return this;
        }

        public Builder setPrimaryLanguageId(Integer primaryLanguageId) {
            this.primaryLanguageId = primaryLanguageId;
            return this;
        }

        public Builder setSecondaryLanguageId(Integer secondaryLanguageId) {
            this.secondaryLanguageId = secondaryLanguageId;
            return this;
        }

        public Builder setAddress1(String address1) {
            this.address1 = address1;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setCountryId(Integer countryId) {
            this.countryId = countryId;
            return this;
        }

        public Builder setStateId(Integer stateId) {
            this.stateId = stateId;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setPrefAddress(String prefAddress) {
            this.prefAddress = prefAddress;
            return this;
        }

        public SignUpIIRequest build() {
            SignUpIIRequest in = new SignUpIIRequest(firstName, lastName, dateOfBirth, birthCity,
                    birthCountry, nationalityId, genderId,
                    specialityId, primaryLanguageId, secondaryLanguageId,
                    address1, city, countryId, stateId, postalCode,
                    prefAddress);
            return in;
        }
    }
}