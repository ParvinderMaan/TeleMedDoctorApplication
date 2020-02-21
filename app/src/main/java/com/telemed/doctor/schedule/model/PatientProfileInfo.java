package com.telemed.doctor.schedule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientProfileInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("genderId")
    @Expose
    private Integer genderId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("rating")
    @Expose
    private Double rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
