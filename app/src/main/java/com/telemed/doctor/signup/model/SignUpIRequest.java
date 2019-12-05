package com.telemed.doctor.signup.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpIRequest {
    private transient  int id;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirmPassword")
    @Expose
    private String confirmPassword;


    @NonNull
    @Override
    public String toString() {
        return "SignUpIRequest{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }

    private SignUpIRequest(String email, String password, String confirmPassword) {
        super();
        this.id = generateId();
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }
    private int generateId() {
        //Generate an id with some mechanism
        int id = 0;
        return id;
    }

    static public class Builder {
        private String email;
        private String password;
        private String confirmPassword;

        public Builder() {}
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }
        public SignUpIRequest build() {
            SignUpIRequest in = new SignUpIRequest(email, password, confirmPassword);
            return in;
        }
    }
}
