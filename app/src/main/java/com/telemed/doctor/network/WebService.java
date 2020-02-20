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
import com.telemed.doctor.profile.model.BankInfoRequest;
import com.telemed.doctor.profile.model.BankInfoResponse;
import com.telemed.doctor.profile.model.BasicInfoRequest;
import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.profile.model.ChangeEmailRequest;
import com.telemed.doctor.profile.model.ChangeEmailResponse;
import com.telemed.doctor.profile.model.OTPRequest;
import com.telemed.doctor.profile.model.OTPResponse;
import com.telemed.doctor.profile.model.OptionResponse;
import com.telemed.doctor.profile.model.ProfessionalInfoRequest;
import com.telemed.doctor.profile.model.ProfessionalInfoResponse;
import com.telemed.doctor.profile.model.ProfileUpdateResponse;
import com.telemed.doctor.profile.model.StateResponse;
import com.telemed.doctor.schedule.model.DayScheduleDeletionResponse;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.model.DayScheduleAlterationResponse;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.model.ScheduleTimeSlotResponse;
import com.telemed.doctor.schedule.model.WeekScheduleRequest;
import com.telemed.doctor.schedule.model.CreateWeekScheduleResponse;
import com.telemed.doctor.schedule.model.WeekScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteWeekScheduleResponse;
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
import com.telemed.doctor.signup.model.SignUpVResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

  // signup
    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_I)
    Call<SignUpIResponse> attemptSignUpOne(@Body SignUpIRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.VERIFY_USER)
    Call<VerificationResponse> attemptVerifyUser(@Body VerficationRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.VERIFY_RESETEMAIL)
    Call<OTPResponse> attemptResetEmail(@HeaderMap Map<String, String> headers , @Body OTPRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.RESEND_OTP)
    Call<ResendOtpResponse> attemptResendOtp(@Body JsonObject in);

    @Headers({WebUrl.CONTENT_HEADER})
    @GET(WebUrl.FETCH_DRILLS)
    Call<OptionResponse> fetchDrillInfo();

    @Headers({WebUrl.CONTENT_HEADER})
    @GET(WebUrl.FETCH_STATE)
    Call<StateResponse> fetchStateInfo(@Query("CountryId") String id);


    @POST(WebUrl.SIGN_UP_II)
    Call<SignUpIIResponse> attemptSignUpTwo(@HeaderMap Map<String, String> headers, @Body SignUpIIRequest in);


    @POST(WebUrl.SIGN_UP_III)
    Call<SignUpIIIResponse> attemptSignUpThree(@HeaderMap Map<String, String> headers, @Body SignUpIIIRequest in);


    @POST(WebUrl.SIGN_UP_IV)
    Call<SignUpIVResponse> attemptSignUpFour(@HeaderMap Map<String, String> headers, @Body SignUpIVRequest in);


    @POST(WebUrl.SIGN_UP_V)
    Call<SignUpVResponse> attemptSignUpFive(@HeaderMap Map<String, String> headers);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_UP_VI)
    Call<SignUpIResponse> attemptSignUpSix(@Body SignUpIRequest in);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.SIGN_IN)
    Call<SignInResponse> attemptSignIn(@Body SignInRequest in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.CHANGE_EMAIL)
    Call<ChangeEmailResponse> attemptChangeEmail(@HeaderMap Map<String, String> headers,@Body ChangeEmailRequest in);


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


    @POST(WebUrl.UPDATE_BANK_PROFILE_INFO)
    Call<ProfileUpdateResponse> updateBankInfo(@HeaderMap Map<String, String> headers, @Body BankInfoRequest in);


    @POST(WebUrl.UPDATE_PROFESSIONAL_PROFILE_INFO)
    Call<ProfileUpdateResponse> updateProfessionalProfileInfo(@HeaderMap Map<String, String> headers, @Body ProfessionalInfoRequest in);


    @POST(WebUrl.UPDATE_BASIC_PROFILE_INFO)
    Call<ProfileUpdateResponse> updateBasicProfileInfo(@HeaderMap Map<String, String> headers, @Body SignUpIVRequest in);

    @POST(WebUrl.UPDATE_BASIC_PROFILE_INFO)
    Call<ProfileUpdateResponse> updateBasicDoctorProfileInfo(@HeaderMap Map<String, String> headers, @Body BasicInfoRequest in);


    @Multipart
    @POST(WebUrl.ALTER_PROFILE_PIC)
    Call<AlterProfilePicResponse> alterProfilePic(@HeaderMap Map<String, String> token,
                                                  @Part MultipartBody.Part imgFile);  // fileData



    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.RESET_PASSWORD)
    Call<ResetPasswordResponse> attemptResetPassword(@Body ResetPasswordRequest in);


    @GET(WebUrl.FETCH_UPCOMING_APPOINTMENT)
    Call<AppointmentListResponse> fetchUpcomingAppointment(@HeaderMap Map<String, String> token);


    @POST(WebUrl.CREATE_WEEK_SCHEDULE)
    Call<CreateWeekScheduleResponse> createWeekSchedule(@HeaderMap Map<String, String> headers, @Body WeekScheduleRequest in);


    @GET(WebUrl.FETCH_WEEK_SCHEDULES)
    Call<WeekScheduleResponse> fetchWeekSchedules(@HeaderMap Map<String, String> token);

    @DELETE(WebUrl.DELETE_WEEK_SCHEDULE)
    Call <DeleteWeekScheduleResponse> deleteWeekSchedule(@Query("Id") int scheduleId,@HeaderMap Map<String, String> token);


    @GET(WebUrl.FETCH_MONTHLY_SCHEDULES)
    Call<MonthlyScheduleResponse> fetchMonthlySchedules(@HeaderMap Map<String, String> token, @Query("CurrentPage") int pageNo);

    @GET(WebUrl.FETCH_AVAIL_TIME_SLOTS)
    Call<ScheduleTimeSlotResponse> fetchScheduleTimeSlots(@HeaderMap Map<String, String> token, @Query("appointmentDate") String appointmentDate);


    @POST(WebUrl.CREATE_DAY_SCHEDULE)
    Call<DayScheduleAlterationResponse> createDaySchedule(@HeaderMap Map<String, String> headers, @Body DayScheduleRequest in);


    @DELETE(WebUrl.DELETE_DAY_SCHEDULE)
    Call <DayScheduleDeletionResponse> deleteDaySchedule(@Query("Id") String scheduleId, @HeaderMap Map<String, String> token);





}

