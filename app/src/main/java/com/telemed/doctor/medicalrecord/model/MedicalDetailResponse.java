package com.telemed.doctor.medicalrecord.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicalDetailResponse {
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

        @SerializedName("medicalHistory")
        @Expose
        private List<MedicalHistory> medicalHistory = null;

        public List<MedicalHistory> getMedicalHistory() {
            return medicalHistory;
        }

        public void setMedicalHistory(List<MedicalHistory> medicalHistory) {
            this.medicalHistory = medicalHistory;
        }

    }

    public static class MedicalHistory {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("symptom")
        @Expose
        private Object symptom;
        @SerializedName("drug")
        @Expose
        private Object drug;
        @SerializedName("consultDate")
        @Expose
        private String consultDate;
        @SerializedName("subjective")
        @Expose
        private Object subjective;
        @SerializedName("objective")
        @Expose
        private Object objective;
        @SerializedName("doctorId")
        @Expose
        private String doctorId;
        @SerializedName("doctorName")
        @Expose
        private Object doctorName;
        @SerializedName("patientId")
        @Expose
        private String patientId;
        @SerializedName("updatedOn")
        @Expose
        private String updatedOn;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getSymptom() {
            return symptom;
        }

        public void setSymptom(Object symptom) {
            this.symptom = symptom;
        }

        public Object getDrug() {
            return drug;
        }

        public void setDrug(Object drug) {
            this.drug = drug;
        }

        public String getConsultDate() {
            return consultDate;
        }

        public void setConsultDate(String consultDate) {
            this.consultDate = consultDate;
        }

        public Object getSubjective() {
            return subjective;
        }

        public void setSubjective(Object subjective) {
            this.subjective = subjective;
        }

        public Object getObjective() {
            return objective;
        }

        public void setObjective(Object objective) {
            this.objective = objective;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public Object getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(Object doctorName) {
            this.doctorName = doctorName;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }

    }
}
