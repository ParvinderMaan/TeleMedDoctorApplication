package com.telemed.doctor.network;

import com.google.gson.JsonObject;
import com.telemed.doctor.consult.model.AppointmentListResponse;
import com.telemed.doctor.miscellaneous.model.SignOutResponse;
import com.telemed.doctor.password.model.ForgotPasswordResponse;
import com.telemed.doctor.password.model.ResetPasswordRequest;
import com.telemed.doctor.password.model.ResetPasswordResponse;
import com.telemed.doctor.password.model.VerficationRequest;
import com.telemed.doctor.password.model.VerificationResponse;
import com.telemed.doctor.password.model.ResendOtpResponse;
import com.telemed.doctor.profile.model.AlterProfilePicResponse;
import com.telemed.doctor.profile.model.BankInfoResponse;
import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.profile.model.OptionResponse;
import com.telemed.doctor.profile.model.ProfessionalInfoResponse;
import com.telemed.doctor.profile.model.StateResponse;
import com.telemed.doctor.signin.SignInRequest;
import com.telemed.doctor.signin.SignInResponse;
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.model.FileDeleteResponse;
import com.telemed.doctor.signup.model.FileUploadResponse;
import com.telemed.doctor.signup.model.SignUpIIIRequest;
import com.telemed.doctor.signup.model.SignUpIIIResponse;
import com.telemed.doctor.signup.model.SignUpIIRequest;
import com.telemed.doctor.signup.model.SignUpIIResponse;
import com.telemed.doctor.signup.model.SignUpIRequest;
import com.telemed.doctor.signup.model.SignUpIResponse;
import com.telemed.doctor.signup.model.SignUpIVRequest;
import com.telemed.doctor.signup.model.SignUpIVResponse;
import com.telemed.doctor.signup.model.SignUpVRequest;
import com.telemed.doctor.signup.model.SignUpVResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<SignUpIIResponse> attemptSignUpTwo(@HeaderMap Map<String, String> headers, @Body SignUpIIRequest in);


//    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_III)
    Call<SignUpIIIResponse> attemptSignUpThree(@HeaderMap Map<String, String> headers, @Body SignUpIIIRequest in);


//    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_IV)
    Call<SignUpIVResponse> attemptSignUpFour(@HeaderMap Map<String, String> headers, @Body SignUpIVRequest in);


//    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_V)
    Call<SignUpVResponse> attemptSignUpFive(@HeaderMap Map<String, String> headers);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_VI)
    Call<SignUpIResponse> attemptSignUpSix(@Body SignUpIRequest in);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_IN)
    Call<SignInResponse> attemptSignIn(@Body SignInRequest in);


    @POST(WebUrl.SIGN_OUT)
    Call<SignOutResponse> attemptSignOut(@HeaderMap Map<String, String> token);

    @GET(WebUrl.FETCH_ALL_DOC)
    Call<AllDocumentResponse> fetchAllDocument(@HeaderMap Map<String, String> token);




    @Multipart
    @POST(WebUrl.UPLOAD_FILE)
    Call<FileUploadResponse> attemptUploadFile(@HeaderMap Map<String, String> token,
                                               @Part MultipartBody.Part docFile);  // fileData

//    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.DELETE_FILE)
    Call<FileDeleteResponse> attemptDeleteFile(@HeaderMap Map<String, String> token, @Query("DocumentId") Integer id);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.FORGOT_PASSWORD)
    Call<ForgotPasswordResponse> attemptForgotPassword(@Body JsonObject in);


    // profile
    @GET(WebUrl.BASIC_PROFILE_INFO)
    Call<BasicInfoResponse> fetchBasicProfileInfo(@HeaderMap Map<String, String> token);

    @GET(WebUrl.PROFESSIONAL_PROFILE_INFO)
    Call<ProfessionalInfoResponse> fetchProfessionalProfileInfo(@HeaderMap Map<String, String> token);

    @GET(WebUrl.BANK_PROFILE_INFO)
    Call<BankInfoResponse> fetchBankProfileInfo(@HeaderMap Map<String, String> token);


    @Multipart
    @POST(WebUrl.ALTER_PROFILE_PIC)
    Call<AlterProfilePicResponse> alterProfilePic(@HeaderMap Map<String, String> token,
                                                  @Part MultipartBody.Part imgFile);  // fileData



    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.RESET_PASSWORD)
    Call<ResetPasswordResponse> attemptResetPassword(@Body ResetPasswordRequest in);


    @GET(WebUrl.FETCH_UPCOMING_APPOINTMENT)
    Call<AppointmentListResponse> fetchUpcomingAppointment(@HeaderMap Map<String, String> token);

}

