package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfessionalInfoRequest {

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

    private ProfessionalInfoRequest(String medicalDegree, String degreeFrom, String otherDegree, String otherDegreeFrom, String deaNumber, String npiNumber) {
        super();
        this.medicalDegree = medicalDegree;
        this.degreeFrom = degreeFrom;
        this.otherDegree = otherDegree;
        this.otherDegreeFrom = otherDegreeFrom;
        this.deaNumber = deaNumber;
        this.npiNumber = npiNumber;
    }
    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }

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


    static public class Builder {
        private String medicalDegree;
        private String degreeFrom;
        private String otherDegree;
        private String otherDegreeFrom;
        private String deaNumber;
        private String npiNumber;

        public Builder setMedicalDegree(String medicalDegree) {
            this.medicalDegree = medicalDegree;
            return this;
        }

        public Builder setDegreeFrom(String degreeFrom) {
            this.degreeFrom = degreeFrom;
            return this;
        }

        public Builder setOtherDegree(String otherDegree) {
            this.otherDegree = otherDegree;
            return this;
        }

        public Builder setOtherDegreeFrom(String otherDegreeFrom) {
            this.otherDegreeFrom = otherDegreeFrom;
            return this;
        }

        public Builder setDeaNumber(String deaNumber) {
            this.deaNumber = deaNumber;
            return this;
        }

        public Builder setNpiNumber(String npiNumber) {
            this.npiNumber = npiNumber;
            return this;
        }

        public Builder() {
        }


        public ProfessionalInfoRequest build() {
            ProfessionalInfoRequest in = new ProfessionalInfoRequest(medicalDegree, degreeFrom, otherDegree, otherDegreeFrom, deaNumber, npiNumber);
            return in;
        }


    }

}
