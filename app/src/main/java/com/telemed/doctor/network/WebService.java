package com.telemed.doctor.network;
import androidx.lifecycle.LiveData;

import com.telemed.doctor.signin.SignInRequest;
import com.telemed.doctor.signin.SignInResponse;
import com.telemed.doctor.signup.model.SignUpIRequest;
import com.telemed.doctor.signup.model.SignUpIResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author Pmaan  on 05-12-2019.
 */

public interface WebService {


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_I)
    Call<SignUpIResponse> attemptSignUpOne(@Body SignUpIRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_II)
    Call<SignUpIResponse> attemptSignUpTwo(@Body SignUpIRequest in);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_III)
    Call<SignUpIResponse> attemptSignUpThree(@Body SignUpIRequest in);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_IV)
    Call<SignUpIResponse> attemptSignUpFour(@Body SignUpIRequest in);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_V)
    Call<SignUpIResponse> attemptSignUpFive(@Body SignUpIRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_VI)
    Call<SignUpIResponse> attemptSignUpSix(@Body SignUpIRequest in);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_IN)
    Call<SignInResponse> attemptSignIn(@Body SignInRequest in);





}

