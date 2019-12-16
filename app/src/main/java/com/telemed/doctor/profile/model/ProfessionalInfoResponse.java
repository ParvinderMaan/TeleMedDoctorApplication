package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfessionalInfoResponse {

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
        @SerializedName("medicalDegree")
        @Expose
        private String medicalDegree;
        @SerializedName("degreeFrom")
        @Expose
        private String degreeFrom;
        @SerializedName("otherDegree")
        @Expose
        private String otherDegree;
        @SerializedName("otherDegreeFrom")
        @Expose
        private String otherDegreeFrom;
        @SerializedName("deaNumber")
        @Expose
        private String deaNumber;
        @SerializedName("npiNumber")
        @Expose
        private String npiNumber;

        public String getMedicalDegree() {
            return medicalDegree;
        }

        public void setMedicalDegree(String medicalDegree) {
            this.medicalDegree = medicalDegree;
        }

        public String getDegreeFrom() {
            return degreeFrom;
        }

        public void setDegreeFrom(String degreeFrom) {
            this.degreeFrom = degreeFrom;
        }

        public String getOtherDegree() {
            return otherDegree;
        }

        public void setOtherDegree(String otherDegree) {
            this.otherDegree = otherDegree;
        }

        public String getOtherDegreeFrom() {
            return otherDegreeFrom;
        }

        public void setOtherDegreeFrom(String otherDegreeFrom) {
            this.otherDegreeFrom = otherDegreeFrom;
        }

        public String getDeaNumber() {
            return deaNumber;
        }

        public void setDeaNumber(String deaNumber) {
            this.deaNumber = deaNumber;
        }

        public String getNpiNumber() {
            return npiNumber;
        }

        public void setNpiNumber(String npiNumber) {
            this.npiNumber = npiNumber;
        }

    }
}
