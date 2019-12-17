package com.telemed.doctor.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentInfo {
    private int pos;
    @SerializedName("id")
    @Expose
    private Integer id;
    private String path;
    @SerializedName("documentName")
    @Expose
    private String documentName;
    private int status=0;    // 0,1,2
//-----------------------------------------------------------
//  ===========================================
//  0		Add document or pdf    X
//	1		File added             upload Btn
//  2       File uploaded          delete  Btn
//==============================================


//------------------------------------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentInfo() {
//        this.documentName = " Add document or pdf";
    }  // default msg

    public int getPos() {
        return pos;
    }

    public DocumentInfo(int pos, String path,int status) {
        this.pos = pos;
        this.path = path;
        this.status = status;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return documentName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.documentName = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
