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
    String SIGN_OUT ="Auth/Logout";

    // sing up..
    String SIGN_UP_I ="Auth/RegisterDoctor";
    String SIGN_UP_II ="Auth/AddDoctorPersonalInfo";
    String SIGN_UP_III ="Auth/AddDoctorProfessionalInfo";
    String SIGN_UP_IV ="Auth/AddDoctorBankInfo";
    String SIGN_UP_V ="Auth/DoctorFinalRegistration";
    String SIGN_UP_VI ="Auth/RegisterDoctor";

    // password
    String FORGOT_PASSWORD="Auth/ForgotPassword";
    String RESET_PASSWORD = "Auth/ResetPassword";
    String CHANGE_PASSWORD ="Auth/ChangePassword";


    String VERIFY_RESET_EMAIL = "Auth/ResetEmail";

    String VERIFY_USER = "Auth/VerifyUser";
    String RESEND_OTP = "Auth/ResendCode";
    String FETCH_DRILLS="Content/GetDoctorDrillsPI";
    String FETCH_STATE = "Content/GetStatesByCountryId";

    String FETCH_ALL_DOC="Auth/DoctorDocumentList";
    String UPLOAD_FILE = "Auth/AddDoctorDocument";
    String DELETE_FILE = "Auth/DeleteDoctorDocument";
    String UPLOAD_CV="Auth/UpdateDoctorCVGovtIssueRecord";

    // profile
    String CHANGE_EMAIL = "Auth/ChangeEmail";
    String BASIC_PROFILE_INFO = "Doctor/GetDoctorPersonalInfo";
    String PROFESSIONAL_PROFILE_INFO = "Doctor/GetDoctorProfessionalInfo";
    String BANK_PROFILE_INFO = "Doctor/GetDoctorBankInfo";
    String ALTER_PROFILE_PIC = "Auth/UpdateProfilePic";

    String UPDATE_BANK_PROFILE_INFO="Doctor/UpdateDoctorBankInfo";
    String UPDATE_PROFESSIONAL_PROFILE_INFO="Doctor/UpdateDoctorProfessionalInfo";
    String UPDATE_BASIC_PROFILE_INFO = "Doctor/UpdateDoctorPersonalInfo";

    // my consult
    String FETCH_UPCOMING_APPOINTMENT="Doctor/GetNewDoctorUpcomingAppointments";
    String FETCH_PAST_APPOINTMENT="Doctor/GetNewDoctorPastAppointments";

    // schedule....
    String CREATE_WEEK_SCHEDULE="Doctor/CreateRecurringSchedule";
    String DELETE_WEEK_SCHEDULE="Doctor/DeleteRecurringSchedule";
    String UPDATE_WEEK_SCHEDULE="Doctor/UpdateRecurringSchedule";
    String FETCH_WEEK_SCHEDULES="Doctor/GetWeekDayScheduleByDoctorId";       //GetNewRecurringSchedule
    String FETCH_MONTHLY_SCHEDULES="Doctor/GetDoctorDateSlots"; ///api/Doctor/ //GetDoctorMonthlyAvailableSchedules
    String FETCH_AVAIL_TIME_SLOTS="Doctor/GetDateWiseDoctorTimeSlots"; //  //DoctorNewAvailableTimeSlot
    String CREATE_DAY_SCHEDULE="Doctor/CreateRecurringSchedule"; //          //CreateDateofAvalibility
    String DELETE_DAY_SCHEDULE="Doctor/DeleteTimeSlotById";  //DeleteDateofAvalibility
    String EDIT_DAY_SCHEDULE="Doctor/Updateschedule";   //UpdateDateofAvalibility
    String FETCH_DAY_SCHEDULE="Doctor/GetDateWiseDoctorSchedule";
    String DELETE_DATES_SCHEDULE="Doctor/DeleteRecurringScheduleByDates";

    //booking
    String FETCH_PATIENT_DETAIL="Patient/GetPatientConsults";
    String PROCESS_APPOINTMENT_REQUEST="Appointment/UpdateAppointmentStatus";


   // calling
    String FETCH_CALL_SESSION="ConferenceMedia/GetCallSession";
    String FETCH_MEDICAL_RECORD="MedicalHistory/GetPatientMedicalHistory";
    String FETCH_MEDICAL_DETAIL="MedicalHistory/GetMedicalHistoryByPatient";

   // notification
    String FETCH_NOTIFICATION = "Notification/GetNotifications";
    String DELETE_NOTIFICATION="Notification/DeleteNotifications";
    String READ_NOTIFICATION="Notification/ReadNotification";

    //user setting
    String FETCH_USER_SETTING="Content/GetUserSettings";
    String ALTER_USER_SETTING="Content/UpdateUserSettings";

    //Welcome
    String WELCOME_INFO="Notification/DoctorDashboardInfo";

    //past medical history..
    String FETCH_PAST_MEDICAL_HISTORY= "MedicalHistory/GetPastMedicalHistory";
    String ADD_PAST_MEDICAL_HISTORY= "MedicalHistory/AddPastMedicalHistory";
    String UPDATE_PAST_MEDICAL_HISTORY= "MedicalHistory/UpdatePastMedicalHistory";



}