package com.telemed.doctor.network;

import com.google.gson.JsonObject;
import com.telemed.doctor.appointment.model.AppointmentProcessRequest;
import com.telemed.doctor.appointment.model.AppointmentProcessResponse;
import com.telemed.doctor.consult.model.PastAppointmentResponse;
import com.telemed.doctor.consult.model.AppointmentRequest;
import com.telemed.doctor.consult.model.UpcomingAppointmentResponse;
import com.telemed.doctor.document.model.MedicalHistoryRequest;
import com.telemed.doctor.document.model.MedicalHistoryResponse;
import com.telemed.doctor.home.model.WelcomeInfoResponse;
import com.telemed.doctor.medicalrecord.model.MedicalDetailResponse;
import com.telemed.doctor.medicalrecord.model.MedicalRecordResponse;
import com.telemed.doctor.notification.model.DeleteNotificationResponse;
import com.telemed.doctor.notification.model.NotificationRequest;
import com.telemed.doctor.notification.model.NotificationResponse;
import com.telemed.doctor.notification.model.ReadNotificationResponse;
import com.telemed.doctor.password.model.ChangePasswordRequest;
import com.telemed.doctor.password.model.ChangePasswordResponse;
import com.telemed.doctor.schedule.model.DayScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteScheduleRequest;
import com.telemed.doctor.schedule.model.DeleteWeekDayScheduleRequest;
import com.telemed.doctor.schedule.model.EditDayScheduleRequest;
import com.telemed.doctor.schedule.model.PatientDetailResponse;
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
import com.telemed.doctor.schedule.model.WeekDayAvailabilityResponse;
import com.telemed.doctor.schedule.model.WeekScheduleRequest;
import com.telemed.doctor.schedule.model.CreateWeekScheduleResponse;
import com.telemed.doctor.schedule.model.WeekScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteWeekScheduleResponse;
import com.telemed.doctor.setting.model.UserSettingRequest;
import com.telemed.doctor.setting.model.UserSettingResponse;
import com.telemed.doctor.signin.model.SignInRequest;
import com.telemed.doctor.signin.model.SignInResponse;
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.model.CvFileUploadResponse;
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
import com.telemed.doctor.videocall.model.VideoCallDetailResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @POST(WebUrl.VERIFY_RESET_EMAIL)
    Call<OTPResponse> attemptResetEmail(@HeaderMap Map<String, String> headers, @Body OTPRequest in);

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
    Call<ChangeEmailResponse> attemptChangeEmail(@HeaderMap Map<String, String> headers, @Body ChangeEmailRequest in);


    @POST(WebUrl.SIGN_OUT)
    Call<SignOutResponse> attemptSignOut(@HeaderMap Map<String, String> token);

    @GET(WebUrl.FETCH_ALL_DOC)
    Call<AllDocumentResponse> fetchAllDocument(@HeaderMap Map<String, String> token);


    @Multipart
    @POST(WebUrl.UPLOAD_FILE)
    Call<FileUploadResponse> attemptUploadFile(@HeaderMap Map<String, String> token,
                                               @Part MultipartBody.Part docFile,@Part("expiryDate") RequestBody dateOfExpiry);  // fileData

    @POST(WebUrl.DELETE_FILE)
    Call<FileDeleteResponse> attemptDeleteFile(@HeaderMap Map<String, String> token, @Query("DocumentId") Integer id);


    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.FORGOT_PASSWORD)
    Call<ForgotPasswordResponse> attemptForgotPassword(@Body JsonObject in);

    @Headers({WebUrl.CONTENT_HEADER})
    @POST(WebUrl.CHANGE_PASSWORD)
    Call<ChangePasswordResponse> attemptChangePassword(@HeaderMap Map<String, String> token,@Body ChangePasswordRequest in);


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


    @POST(WebUrl.FETCH_UPCOMING_APPOINTMENT)
    Call<UpcomingAppointmentResponse> fetchUpcomingAppointment(@HeaderMap Map<String, String> token, @Body AppointmentRequest in);

    @POST(WebUrl.FETCH_PAST_APPOINTMENT)
    Call<PastAppointmentResponse> fetchPastAppointment(@HeaderMap Map<String, String> token, @Body AppointmentRequest in);


    @POST(WebUrl.CREATE_WEEK_SCHEDULE)
    Call<CreateWeekScheduleResponse> createWeekSchedule(@HeaderMap Map<String, String> headers, @Body WeekScheduleRequest in);


    @GET(WebUrl.FETCH_WEEK_SCHEDULES)
    Call<WeekScheduleResponse> fetchWeekSchedules(@HeaderMap Map<String, String> token);

   // @DELETE(WebUrl.DELETE_WEEK_SCHEDULE)
    @HTTP(method = "DELETE", path = WebUrl.DELETE_WEEK_SCHEDULE, hasBody = true)
    Call<DayScheduleResponse> deleteWeekSchedule(@HeaderMap Map<String, String> token,@Body DeleteWeekDayScheduleRequest in);


    @GET(WebUrl.FETCH_MONTHLY_SCHEDULES)
    Call<MonthlyScheduleResponse> fetchMonthlySchedules(@HeaderMap Map<String, String> token, @Query("month") int month,@Query("year") int year);


    @GET(WebUrl.FETCH_AVAIL_TIME_SLOTS)
    Call<ScheduleTimeSlotResponse> fetchScheduleTimeSlots(@HeaderMap Map<String, String> token, @Query("selectedDate") String selectedDate);


    @POST(WebUrl.CREATE_DAY_SCHEDULE)
    Call<DayScheduleAlterationResponse> createDaySchedule(@HeaderMap Map<String, String> headers, @Body DayScheduleRequest in,@Query("isDate") Boolean isDate);


    @GET(WebUrl.FETCH_DAY_SCHEDULE)
    Call<DayScheduleResponse> fetchDaySchedule(@HeaderMap Map<String, String> headers, @Query("selectedDate") String selectedDate);

    @DELETE(WebUrl.DELETE_DAY_SCHEDULE)
    Call<DayScheduleResponse> deleteDaySchedule(@HeaderMap Map<String, String> token,@Query("timeSlotId") Integer timeSlotId);


    @PUT(WebUrl.EDIT_DAY_SCHEDULE)
    Call<DayScheduleResponse> editDaySchedule(@HeaderMap Map<String, String> token, @Body EditDayScheduleRequest in,@Query("timeSlotId") Integer timeSlotId);

  //  @DELETE(WebUrl.DELETE_DATES_SCHEDULE)
    @HTTP(method = "DELETE", path = WebUrl.DELETE_DATES_SCHEDULE, hasBody = true)
    Call<DayScheduleResponse> deleteDatesSchedule(@HeaderMap Map<String, String> token,@Body DeleteScheduleRequest in);




    @GET(WebUrl.FETCH_PATIENT_DETAIL)
    Call<PatientDetailResponse> fetchPatientDetail(@HeaderMap Map<String, String> token, @Query("PatientId") String patientId);

    @POST(WebUrl.PROCESS_APPOINTMENT_REQUEST)
    Call<AppointmentProcessResponse> processAppointmentRequest(@HeaderMap Map<String, String> headers, @Body AppointmentProcessRequest in);

    @GET(WebUrl.FETCH_CALL_SESSION)
    Call<VideoCallDetailResponse> fetchVideoCallDetail(@HeaderMap Map<String, String> token, @Query("AppointmentId") Integer appointmentId);


    @GET(WebUrl.FETCH_MEDICAL_RECORD)
    Call<MedicalRecordResponse> fetchMedicalRecord(@HeaderMap Map<String, String> token, @Query("PatientId") String patientId);


    @GET(WebUrl.FETCH_MEDICAL_DETAIL)
    Call<MedicalDetailResponse> fetchMedicalDetails(@HeaderMap Map<String, String> token, @Query("userId") String patientId, @Query("Type") String historyType);

    @POST(WebUrl.FETCH_NOTIFICATION)
    Call<NotificationResponse> fetchNotifications(@HeaderMap HashMap<String, String> headerMap, @Body NotificationRequest in);

    @POST(WebUrl.DELETE_NOTIFICATION)
    Call<DeleteNotificationResponse> deleteNotifications(@HeaderMap HashMap<String, String> headerMap);


    @POST(WebUrl.READ_NOTIFICATION)
    Call<ReadNotificationResponse> readNotification(@HeaderMap HashMap<String, String> headerMap,@Query("NotificationId") Integer notificationId);

    @GET(WebUrl.FETCH_USER_SETTING)
    Call<UserSettingResponse> fetchUserSettings(@HeaderMap Map<String, String> token);

    @POST(WebUrl.ALTER_USER_SETTING)
    Call<UserSettingResponse> updateUserSettings(@HeaderMap Map<String, String> mHeaderMap,@Body UserSettingRequest in);


    @GET(WebUrl.WELCOME_INFO)
    Call<WelcomeInfoResponse> fetchWelcomeInfo(@HeaderMap Map<String, String> token);


    @Multipart
    @POST(WebUrl.UPLOAD_CV)
    Call<CvFileUploadResponse> attemptCVUploadFile(@HeaderMap Map<String, String> token,
                                                   @Part MultipartBody.Part imgFile);  // fileData

    @Multipart
    @POST(WebUrl.UPLOAD_CV)
    Call<CvFileUploadResponse> attemptGovtRegisteredUploadFile(@HeaderMap Map<String, String> token,
                                                               @Part MultipartBody.Part imgFile, @Part("GovtIssueId") RequestBody info);  // fileData


    @GET(WebUrl.FETCH_PAST_MEDICAL_HISTORY)
    Call<MedicalHistoryResponse> fetchPastMedicalHistory(@HeaderMap Map<String, String> token,@Query("AppointmentId") Integer appointmentId);



    @POST(WebUrl.ADD_PAST_MEDICAL_HISTORY)
    Call<MedicalHistoryResponse> addPastMedicalHistory(@HeaderMap Map<String, String> mHeaderMap, @Body MedicalHistoryRequest in);


    @POST(WebUrl.UPDATE_PAST_MEDICAL_HISTORY)
    Call<MedicalHistoryResponse> updatePastMedicalHistory(@HeaderMap Map<String, String> mHeaderMap,@Body MedicalHistoryRequest in);

    @GET(WebUrl.FETCH_WEEK_DAY_SCHEDULE)
    Call<WeekDayAvailabilityResponse> fetchWeekDaySchedule(@HeaderMap Map<String, String> map, @Query("weekDay") Integer selectedWeekDay);
}

