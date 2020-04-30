package com.telemed.doctor.document.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalHistoryResponse {
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
        private MedicalHistoryRecord medicalHistory;

        public MedicalHistoryRecord getMedicalHistory() {
            return medicalHistory;
        }

        public void setMedicalHistory(MedicalHistoryRecord medicalHistory) {
            this.medicalHistory = medicalHistory;
        }
    }

}
