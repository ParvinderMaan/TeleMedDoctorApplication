package com.telemed.doctor.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentInfo {
    //-----------------------------------------------------------
//  ===========================================
//  0		Add document or pdf       X
//	1		File added             upload Btn
//  2       File uploaded          delete  Btn
// ============================================
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("documentName")
    @Expose
    private String documentName;
    @SerializedName("actualName")
    @Expose
    private String actualName;
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("isVerified")
    @Expose
    private Boolean isVerified;

    // for local use...
    transient int status=0; // default


    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DocumentInfo() { }


//  ====================================================
  // 0 -kuch ni huya //  1 ----->add doc ho gyi --> 2----->Date add ho gyi hai  3--->File upload ho gyi hai
// ------------------------------------------------------------









    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

}
