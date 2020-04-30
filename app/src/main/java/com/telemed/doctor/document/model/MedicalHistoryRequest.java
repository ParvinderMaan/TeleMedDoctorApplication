package com.telemed.doctor.document.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telemed.doctor.document.model.Assessment;
import com.telemed.doctor.document.model.Drug;

import java.util.List;

public class MedicalHistoryRequest {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("appointmentId")
    @Expose
    private Integer appointmentId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("symptom")
    @Expose
    private String symptom;
    @SerializedName("drugsList")
    @Expose
    private List<Drug> drugsList = null;
    @SerializedName("subjective")
    @Expose
    private String subjective;
    @SerializedName("objective")
    @Expose
    private String objective;
    @SerializedName("precautions")
    @Expose
    private String precautions;
    @SerializedName("assessmentList")
    @Expose
    private List<Assessment> assessmentList = null;
    @SerializedName("notes")
    @Expose
    private String notes;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public List<Drug> getDrugsList() {
        return drugsList;
    }

    public void setDrugsList(List<Drug> drugsList) {
        this.drugsList = drugsList;
    }

    public String getSubjective() {
        return subjective;
    }

    public void setSubjective(String subjective) {
        this.subjective = subjective;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
