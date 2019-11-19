package com.telemed.doctor.chat;

public class ChatModel {


    public String chatId="";
    public String mediaUrl;
    public String messageId;
    public String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ChatModel{" +
                "chatId='" + chatId + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                ", senderId='" + senderId + '\'' +
                ", messageThumbUrl='" + messageThumbUrl + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public String message="";

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageThumbUrl() {
        return messageThumbUrl;
    }

    public void setMessageThumbUrl(String messageThumbUrl) {
        this.messageThumbUrl = messageThumbUrl;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String messageType;
    public String senderId="";
    public String messageThumbUrl="";
    public long timeStamp;
    public boolean seen=false;

    public boolean isSeen() {
        return seen;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    private boolean isSelected;
}
