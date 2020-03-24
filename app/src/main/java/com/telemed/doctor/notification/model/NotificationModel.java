package com.telemed.doctor.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NotificationModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fromUserId")
    @Expose
    private String fromUserId;
    @SerializedName("toUserId")
    @Expose
    private String toUserId;
    @SerializedName("fromUserName")
    @Expose
    private String fromUserName;
    @SerializedName("fromUserEmail")
    @Expose
    private String fromUserEmail;
    @SerializedName("fromUserProfilePic")
    @Expose
    private String fromUserProfilePic;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("isRead")
    @Expose
    private Boolean isRead;
    @SerializedName("timeInMinutes")
    @Expose
    private Integer timeInMinutes;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserEmail() {
        return fromUserEmail;
    }

    public void setFromUserEmail(String fromUserEmail) {
        this.fromUserEmail = fromUserEmail;
    }

    public String getFromUserProfilePic() {
        return fromUserProfilePic;
    }

    public void setFromUserProfilePic(String fromUserProfilePic) {
        this.fromUserProfilePic = fromUserProfilePic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Integer timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}