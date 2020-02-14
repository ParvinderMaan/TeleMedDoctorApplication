package com.telemed.doctor.profile.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    private BasicDetail data;

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

    public BasicDetail getData() {
        return data;
    }

    public void setData(BasicDetail data) {
        this.data = data;
    }

    @Entity(tableName = "basicinfo")
    public static class BasicDetail {
        @PrimaryKey(autoGenerate = false)
        @NonNull
        @SerializedName("id")
        @Expose
        private String id;

        @ColumnInfo(name = "firstName")
        @SerializedName("firstName")
        @Expose
        private String firstName;

        @ColumnInfo(name = "lastName")
        @SerializedName("lastName")
        @Expose
        private String lastName;

        @ColumnInfo(name = "dateOfBirth")
        @SerializedName("dateOfBirth")
        @Expose
        private String dateOfBirth;

        @ColumnInfo(name = "birthCity")
        @SerializedName("birthCity")
        @Expose
        private String birthCity;

        @ColumnInfo(name = "birthCountry")
        @SerializedName("birthCountry")
        @Expose
        private String birthCountry;

        @Ignore
        @SerializedName("nationalityId")
        @Expose
        private Integer nationalityId;


        @ColumnInfo(name = "nationality")
        @SerializedName("nationality")
        @Expose
        private String nationality;


        @Ignore
        @SerializedName("genderId")
        @Expose
        private Integer genderId;


        @ColumnInfo(name = "gender")
        @SerializedName("gender")
        @Expose
        private String gender;

        @Ignore
        @SerializedName("specialityId")
        @Expose
        private Integer specialityId;

        @ColumnInfo(name = "speciality")
        @SerializedName("speciality")
        @Expose
        private String speciality;


        @Ignore
        @SerializedName("primaryLanguageId")
        @Expose
        private Integer primaryLanguageId;


        @ColumnInfo(name = "primaryLanguage")
        @SerializedName("primaryLanguage")
        @Expose
        private String primaryLanguage;

        @Ignore
        @SerializedName("secondaryLanguageId")
        @Expose
        private Integer secondaryLanguageId;

        @ColumnInfo(name = "secondaryLanguage")
        @SerializedName("secondaryLanguage")
        @Expose
        private String secondaryLanguage;

        @ColumnInfo(name = "address1")
        @SerializedName("address1")
        @Expose
        private String address1;


        @ColumnInfo(name = "city")
        @SerializedName("city")
        @Expose
        private String city;


        @Ignore
        @SerializedName("countryId")
        @Expose
        private Integer countryId;

        @ColumnInfo(name = "countryName")
        @SerializedName("countryName")
        @Expose
        private String countryName;


        @Ignore
        @SerializedName("stateId")
        @Expose
        private Integer stateId;


        @ColumnInfo(name = "stateName")
        @SerializedName("stateName")
        @Expose
        private String stateName;

        @ColumnInfo(name = "email")
        @SerializedName("email")
        @Expose
        private String email;

        @ColumnInfo(name = "phoneNumber")
        @SerializedName("phoneNumber")
        private String phoneNumber;

        public String getPhoneNumber() {
            return phoneNumber;
        }

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String phoneNumber) {
            this.email = email;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

}
