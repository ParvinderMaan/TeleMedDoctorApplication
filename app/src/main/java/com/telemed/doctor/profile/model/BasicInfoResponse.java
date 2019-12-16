package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicInfoResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("id")
        @Expose
        private String id;
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
        @SerializedName("nationality")
        @Expose
        private String nationality;
        @SerializedName("genderId")
        @Expose
        private Integer genderId;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("specialityId")
        @Expose
        private Integer specialityId;
        @SerializedName("speciality")
        @Expose
        private String speciality;
        @SerializedName("primaryLanguageId")
        @Expose
        private Integer primaryLanguageId;
        @SerializedName("primaryLanguage")
        @Expose
        private String primaryLanguage;
        @SerializedName("secondaryLanguageId")
        @Expose
        private Integer secondaryLanguageId;
        @SerializedName("secondaryLanguage")
        @Expose
        private String secondaryLanguage;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("countryId")
        @Expose
        private Integer countryId;
        @SerializedName("countryName")
        @Expose
        private String countryName;
        @SerializedName("stateId")
        @Expose
        private Integer stateId;
        @SerializedName("stateName")
        @Expose
        private String stateName;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @SerializedName("email")
        @Expose
        private String email;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getBirthCity() {
            return birthCity;
        }

        public void setBirthCity(String birthCity) {
            this.birthCity = birthCity;
        }

        public String getBirthCountry() {
            return birthCountry;
        }

        public void setBirthCountry(String birthCountry) {
            this.birthCountry = birthCountry;
        }

        public Integer getNationalityId() {
            return nationalityId;
        }

        public void setNationalityId(Integer nationalityId) {
            this.nationalityId = nationalityId;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public Integer getGenderId() {
            return genderId;
        }

        public void setGenderId(Integer genderId) {
            this.genderId = genderId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getSpecialityId() {
            return specialityId;
        }

        public void setSpecialityId(Integer specialityId) {
            this.specialityId = specialityId;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public Integer getPrimaryLanguageId() {
            return primaryLanguageId;
        }

        public void setPrimaryLanguageId(Integer primaryLanguageId) {
            this.primaryLanguageId = primaryLanguageId;
        }

        public String getPrimaryLanguage() {
            return primaryLanguage;
        }

        public void setPrimaryLanguage(String primaryLanguage) {
            this.primaryLanguage = primaryLanguage;
        }

        public Integer getSecondaryLanguageId() {
            return secondaryLanguageId;
        }

        public void setSecondaryLanguageId(Integer secondaryLanguageId) {
            this.secondaryLanguageId = secondaryLanguageId;
        }

        public String getSecondaryLanguage() {
            return secondaryLanguage;
        }

        public void setSecondaryLanguage(String secondaryLanguage) {
            this.secondaryLanguage = secondaryLanguage;
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

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

    }

}
