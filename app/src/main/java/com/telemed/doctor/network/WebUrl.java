package com.telemed.doctor.network;

/**
 * @author Pmaan  on 30-08-2017.
 */

public interface WebUrl {
    // base urls
    String IMAGE_URL="https://telemedwebapi.azurewebsites.net/";
    String BASE_URL ="https://telemedwebapi.azurewebsites.net/api/"; // https://telemedwebapi-dev.azurewebsites.net/api/
                                                                      // https://telemedwebapi.azurewebsites.net/api/

    // headers
    String CONTENT_HEADER ="content-type:application/json";
    String AUTHORIZATION_HEADER = "Authorization:Basic YWRtaW46YWRtaW4=";

    // child urls
    String SIGN_IN ="Auth/Login";
    String SIGN_UP_I ="Auth/RegisterDoctor";
    String SIGN_UP_II ="Auth/AddDoctorPersonalInfo";
    String SIGN_UP_III ="Auth/AddDoctorProfessionalInfo";
    String SIGN_UP_IV ="Auth/AddDoctorBankInfo";
    String SIGN_UP_V ="Auth/DoctorFinalRegistration";
    String SIGN_UP_VI ="Auth/RegisterDoctor";

    String FORGOT_PASSWORD="Auth/ForgotPassword";
    String SIGN_OUT ="Auth/Logout";


    String VERIFY_RESETEMAIL = "Auth/ResetEmail";

    String VERIFY_USER = "Auth/VerifyUser";
    String RESEND_OTP = "Auth/ResendCode";
    String FETCH_DRILLS="Content/GetDoctorDrillsPI";
    String FETCH_STATE = "Content/GetStatesByCountryId";

    String FETCH_ALL_DOC="Auth/DoctorDocumentList";
    String UPLOAD_FILE = "Auth/AddDoctorDocument";
    String DELETE_FILE = "Auth/DeleteDoctorDocument";


    String RESET_PASSWORD = "Auth/ResetPassword";

    // profile
    String CHANGE_EMAIL = "Auth/ChangeEmail";
    String BASIC_PROFILE_INFO = "Doctor/GetDoctorPersonalInfo";
    String PROFESSIONAL_PROFILE_INFO = "Doctor/GetDoctorProfessionalInfo";
    String BANK_PROFILE_INFO = "Doctor/GetDoctorBankInfo";
    String ALTER_PROFILE_PIC = "Auth/UpdateProfilePic";

    String UPDATE_BANK_PROFILE_INFO="Doctor/UpdateDoctorBankInfo";
    String UPDATE_PROFESSIONAL_PROFILE_INFO="Doctor/UpdateDoctorProfessionalInfo";
    String UPDATE_BASIC_PROFILE_INFO = "Doctor/UpdateDoctorPersonalInfo";


    String FETCH_UPCOMING_APPOINTMENT="Doctor/GetNewDoctorUpcomingAppointments";
    String FETCH_PAST_APPOINTMENT="Doctor/GetNewDoctorPastAppointments";

    // schedule....
    String CREATE_WEEK_SCHEDULE="Doctor/CreateRecurringSchedule";
    String DELETE_WEEK_SCHEDULE="Doctor/DeleteRecurringSchedule";
    String UPDATE_WEEK_SCHEDULE="Doctor/UpdateRecurringSchedule";
    String FETCH_WEEK_SCHEDULES="Doctor/GetNewRecurringSchedule";
    String FETCH_MONTHLY_SCHEDULES="Doctor/GetDoctorMonthlyAvailableSchedules";
    String FETCH_AVAIL_TIME_SLOTS="Doctor/DoctorNewAvailableTimeSlot";
    String CREATE_DAY_SCHEDULE="Doctor/CreateDateofAvalibility";
    String DELETE_DAY_SCHEDULE="Doctor/DeleteDateofAvalibility";

    //booking
    String FETCH_PATIENT_DETAIL="Patient/GetPatientConsults";
    String PROCESS_APPOINTMENT_REQUEST="Appointment/UpdateAppointmentStatus";


   // calling
    String FETCH_CALL_SESSION="ConferenceMedia/GetCallSession";
    String FETCH_MEDICAL_RECORD="MedicalHistory/GetPatientMedicalHistory";

}