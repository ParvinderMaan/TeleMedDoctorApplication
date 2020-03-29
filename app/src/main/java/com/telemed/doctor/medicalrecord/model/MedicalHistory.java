package com.telemed.doctor.medicalrecord.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalHistory {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("smoking")
    @Expose
    private String smoking;
    @SerializedName("alcohol")
    @Expose
    private String alcohol;
    @SerializedName("drugUse")
    @Expose
    private String drugUse;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("updateOn")
    @Expose
    private String updateOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getDrugUse() {
        return drugUse;
    }

    public void setDrugUse(String drugUse) {
        this.drugUse = drugUse;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = updateOn;
    }




}
