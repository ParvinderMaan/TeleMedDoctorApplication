package com.telemed.doctor.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeEmailRequest {

    @SerializedName("newEmail")
    @Expose
    private String newEmail;
    @SerializedName("currentPassword")
    @Expose
    private String currentPassword;

    public ChangeEmailRequest(String newEmail, String currentPassword) {
        super();
        this.newEmail = newEmail;
        this.currentPassword = currentPassword;
    }

    public String getnewEmail() {
        return newEmail;
    }

    public void setnewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getcurrentPassword() {
        return currentPassword;
    }

    public void setcurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    static public class Builder {
        private String newEmail;
        private String currentPassword;

        public ChangeEmailRequest.Builder setnewEmail(String newEmail) {
            this.newEmail = newEmail;
            return this;
        }

        public ChangeEmailRequest.Builder setcurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        public Builder() {}

        public ChangeEmailRequest build() {
            ChangeEmailRequest in = new ChangeEmailRequest(newEmail, currentPassword);
            return in;
        }
    }

}
