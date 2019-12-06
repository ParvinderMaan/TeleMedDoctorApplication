package com.telemed.doctor.signup.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
public class SignUpIResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;

    @Override
    public String toString() {
        return "SignUpIResponse{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", otpCode=" + otpCode +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("otpCode")
    @Expose
    private Integer otpCode;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(Integer otpCode) {
        this.otpCode = otpCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static class Data { }

}
*/




public class SignUpIResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Data implements Parcelable {
        @Expose (serialize = false, deserialize = false)
        private  String email;

        @SerializedName("otpCode")
        @Expose
        private Integer otpCode;
        @SerializedName("accessToken")
        @Expose
        private String accessToken;

        public Integer getOtpCode() {
            return otpCode;
        }

        public void setOtpCode(Integer otpCode) {
            this.otpCode = otpCode;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.email);
            dest.writeValue(this.otpCode);
            dest.writeString(this.accessToken);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.email = in.readString();
            this.otpCode = (Integer) in.readValue(Integer.class.getClassLoader());
            this.accessToken = in.readString();
        }

        public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }
}
