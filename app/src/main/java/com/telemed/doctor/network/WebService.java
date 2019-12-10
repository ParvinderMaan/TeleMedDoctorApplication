package com.telemed.doctor.network;

import com.google.gson.JsonObject;
import com.telemed.doctor.password.model.VerficationRequest;
import com.telemed.doctor.password.model.VerificationResponse;
import com.telemed.doctor.password.model.ResendOtpResponse;
import com.telemed.doctor.profile.model.OptionResponse;
import com.telemed.doctor.profile.model.StateResponse;
import com.telemed.doctor.signin.SignInRequest;
import com.telemed.doctor.signin.SignInResponse;
import com.telemed.doctor.signup.model.SignUpIIRequest;
import com.telemed.doctor.signup.model.SignUpIRequest;
import com.telemed.doctor.signup.model.SignUpIResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Pmaan  on 05-12-2019.
 */

public interface WebService {


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_I)
    Call<SignUpIResponse> attemptSignUpOne(@Body SignUpIRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.VERIFY_USER)
    Call<VerificationResponse> attemptVerifyUser(@Body VerficationRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.RESEND_OTP)
    Call<ResendOtpResponse> attemptResendOtp(@Body JsonObject in);

    @Headers({WebUrl.CONTENT_HEADER})
    @GET(WebUrl.FETCH_DRILLS)
    Call<OptionResponse> fetchDrillInfo();

    @Headers({WebUrl.CONTENT_HEADER})
    @GET(WebUrl.FETCH_STATE)
    Call<StateResponse> fetchStateInfo(@Query("CountryId") String id);


//   @Headers({WebUrl.CONTENT_HEADER,})
    @POST(WebUrl.SIGN_UP_II)
    Call<SignUpIResponse> attemptSignUpTwo(@HeaderMap Map<String, String> headers, @Body SignUpIIRequest in);


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

