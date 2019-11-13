package com.telemed.doctor.network;

/**
 * @author Pmaan  on 30-08-2017.
 */

public interface WebUrl {
    // base urls
    String BASE_URL ="https://telemedapi.azurewebsites.net/api/";


    // headers
    String CONTENT_HEADER ="Content:json";
    String AUTHORIZATION_HEADER = "Authorization:Basic YWRtaW46YWRtaW4=";

    // child urls
    String THOUGHT_OF_DAY="GetInsThought";
    String FETCH_CHAT_ROOM="GetChatRoomList";


}