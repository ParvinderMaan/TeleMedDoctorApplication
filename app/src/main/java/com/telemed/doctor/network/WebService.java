package com.telemed.doctor.network;


import com.telemed.doctor.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Pmaan  on 08-09-2017.
 */

public interface WebService {

//    @GET(WebUrl.THOUGHT_OF_DAY)
//    @Headers({WebUrl.CONTENT_HEADER, WebUrl.AUTHORIZATION_HEADER})
//    Observable<ThoughtOfTheDayResponse> fetchThoughtOfTheDay();

//
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })
//    @GET("Drills/GetDoctorRegisterDrills")
//    Call<DoctorFormData> getDoctorFormData();


        @GET("https://jsonplaceholder.typicode.com/posts")
        Call<List<Post>> fetchAllPost();

    @GET("https://jsonplaceholder.typicode.com/todos/1")
    Call<Post> fetchPostInfo();



}

