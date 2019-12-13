package com.telemed.doctor.signup.model;

public class DocInfo {
    private int pos;
    private int id;
    private String path;
    private String name;
    private int status=0;  //0,1,2

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocInfo() {
        this.name = " Add document or pdf";
    }

    public int getPos() {
        return pos;
    }

    public DocInfo(int pos, String path, String name, int status) {
        this.pos = pos;
        this.path = path;
        this.name = name;
        this.status = status;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
