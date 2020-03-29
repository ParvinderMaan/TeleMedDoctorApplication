package com.telemed.doctor.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDocumentResponse {
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


        @SerializedName("otherDocList")
        @Expose
        private ImportantDocument otherDocList;


        @SerializedName("documentList")
        @Expose
        private List<DocumentInfo> documentList = null;

        public List<DocumentInfo> getDocumentList() {
            return documentList;
        }

        public void setDocumentList(List<DocumentInfo> documentList) {
            this.documentList = documentList;
        }

        public ImportantDocument getOtherDocList() {
            return otherDocList;
        }

        public void setOtherDocList(ImportantDocument otherDocList) {
            this.otherDocList = otherDocList;
        }

    }

    public static class ImportantDocument {

        @SerializedName("cv")
        @Expose
        private String cv;
        @SerializedName("govtIssueId")
        @Expose
        private String govtIssueId;

        public String getCv() {
            return cv;
        }

        public void setCv(String cv) {
            this.cv = cv;
        }

        public String getGovtIssueId() {
            return govtIssueId;
        }

        public void setGovtIssueId(String govtIssueId) {
            this.govtIssueId = govtIssueId;
        }
    }


}
