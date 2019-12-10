package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OptionResponse {
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

        @SerializedName("languages")
        @Expose
        private List<Language> languages = null;
        @SerializedName("speciliaties")
        @Expose
        private List<Speciliaty> speciliaties = null;
        @SerializedName("gender")
        @Expose
        private List<Gender> gender = null;

        @SerializedName("countries")
        @Expose
        private List<Country> countries = null;

        public List<Language> getLanguages() {
            return languages;
        }

        public void setLanguages(List<Language> languages) {
            this.languages = languages;
        }

        public List<Speciliaty> getSpeciliaties() {
            return speciliaties;
        }

        public void setSpeciliaties(List<Speciliaty> speciliaties) {
            this.speciliaties = speciliaties;
        }

        public List<Gender> getGender() {
            return gender;
        }

        public void setGender(List<Gender> gender) {
            this.gender = gender;
        }

        public List<Country> getCountries() {
            return countries;
        }

        public void setCountries(List<Country> countries) {
            this.countries = countries;
        }

    }

}
