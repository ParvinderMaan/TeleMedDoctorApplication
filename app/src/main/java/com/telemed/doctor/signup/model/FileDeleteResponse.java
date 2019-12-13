package com.telemed.doctor.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileDeleteResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private FileDeleteResponse.Data data;

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

    public FileDeleteResponse.Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        transient int viewIndex;

        public int getViewIndex() {
            return viewIndex;
        }

        public void setViewIndex(int viewIndex) {
            this.viewIndex = viewIndex;
        }




    }
}
