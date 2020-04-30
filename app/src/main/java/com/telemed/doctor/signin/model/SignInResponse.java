package com.telemed.doctor.signin.model;

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
        @Expose(serialize = false, deserialize = false)
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
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;

        @SerializedName("pic")
        @Expose
        private String profilePic;

        @SerializedName("timeZoneId")
        @Expose
        private Integer timeZoneId;

        public Integer getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(Integer timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getTimeZoneCode() {
            return timeZoneCode;
        }

        public void setTimeZoneCode(String timeZoneCode) {
            this.timeZoneCode = timeZoneCode;
        }

        @SerializedName("timeZone")
        @Expose
        private String timeZone;
        @SerializedName("timeZoneCode")
        @Expose
        private String timeZoneCode;

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
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
            dest.writeString(this.firstName);
            dest.writeString(this.lastName);
            dest.writeString(this.profilePic);
            dest.writeValue(this.timeZoneId);
            dest.writeString(this.timeZone);
            dest.writeString(this.timeZoneCode);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.email = in.readString();
            this.accessToken = in.readString();
            this.lastScreenId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.emailConfirmed = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.profilePic = in.readString();
            this.timeZoneId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.timeZone = in.readString();
            this.timeZoneCode = in.readString();
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
