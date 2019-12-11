package com.telemed.doctor.signin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInResponse {
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

    public static class Data implements Parcelable {
        @Expose(serialize=false,deserialize = false)
        private String email;

        @SerializedName("accessToken")
        @Expose
        private String accessToken;
        @SerializedName("lastScreenId")
        @Expose
        private Integer lastScreenId;
        @SerializedName("emailConfirmed")
        @Expose
        private Boolean emailConfirmed;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Integer getLastScreenId() {
            return lastScreenId;
        }

        public void setLastScreenId(Integer lastScreenId) {
            this.lastScreenId = lastScreenId;
        }

        public Boolean getEmailConfirmed() {
            return emailConfirmed;
        }

        public void setEmailConfirmed(Boolean emailConfirmed) {
            this.emailConfirmed = emailConfirmed;
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
            dest.writeString(this.accessToken);
            dest.writeValue(this.lastScreenId);
            dest.writeValue(this.emailConfirmed);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.email = in.readString();
            this.accessToken = in.readString();
            this.lastScreenId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.emailConfirmed = (Boolean) in.readValue(Boolean.class.getClassLoader());
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
