package com.telemed.doctor.videocall.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCallDetailResponse {

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
        @SerializedName("mediaValue")
        @Expose
        private VideoCallDetail mediaValue;
        @SerializedName("openTokApiKey")
        @Expose
        private Integer openTokApiKey;

        public VideoCallDetail getMediaValue() {
            return mediaValue;
        }

        public void setMediaValue(VideoCallDetail mediaValue) {
            this.mediaValue = mediaValue;
        }

        public Integer getOpenTokApiKey() {
            return openTokApiKey;
        }

        public void setOpenTokApiKey(Integer openTokApiKey) {
            this.openTokApiKey = openTokApiKey;
        }

    }

}
