package com.telemed.doctor.password.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResendOtpResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("otpCode")
    @Expose
    private Integer otpCode;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(Integer otpCode) {
        this.otpCode = otpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    protected ResendOtpResponse(Parcel in) {
        byte statusVal = in.readByte();
        status = statusVal == 0x02 ? null : statusVal != 0x00;
        otpCode = in.readByte() == 0x00 ? null : in.readInt();
        message = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (status == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (status ? 0x01 : 0x00));
        }
        if (otpCode == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(otpCode);
        }
        dest.writeString(message);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ResendOtpResponse> CREATOR = new Parcelable.Creator<ResendOtpResponse>() {
        @Override
        public ResendOtpResponse createFromParcel(Parcel in) {
            return new ResendOtpResponse(in);
        }

        @Override
        public ResendOtpResponse[] newArray(int size) {
            return new ResendOtpResponse[size];
        }
    };
}
