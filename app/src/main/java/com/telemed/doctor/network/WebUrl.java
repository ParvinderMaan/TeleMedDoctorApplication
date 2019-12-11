package com.telemed.doctor.network;

/**
 * @author Pmaan  on 30-08-2017.
 */

public interface WebUrl {
    // base urls
    String BASE_URL ="https://telemedwebapi.azurewebsites.net/api/"; //


    // headers
    String CONTENT_HEADER ="content-type:application/json";
    String AUTHORIZATION_HEADER = "Authorization:Basic YWRtaW46YWRtaW4=";

    // child urls
    String SIGN_IN ="Auth/Login";
    String SIGN_UP_I ="Auth/RegisterDoctor";
    String SIGN_UP_II ="Auth/AddDoctorPersonalInfo";
    String SIGN_UP_III ="Auth/AddDoctorProfessionalInfo";
    String SIGN_UP_IV ="Auth/AddDoctorBankInfo";
    String SIGN_UP_V ="Auth/RegisterDoctor";
    String SIGN_UP_VI ="Auth/RegisterDoctor";




//    String THOUGHT_OF_DAY="GetInsThought";
//    String FETCH_CHAT_ROOM="GetChatRoomList";


    String VERIFY_USER = "Auth/VerifyUser";
    String RESEND_OTP = "Auth/ResendCode";
    String FETCH_DRILLS="Content/GetDoctorDrillsPI";
    String FETCH_STATE = "Content/GetStatesByCountryId";
}