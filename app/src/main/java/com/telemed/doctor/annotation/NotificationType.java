package com.telemed.doctor.annotation;
import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@StringDef({NotificationType.ADMIN_NOTE,NotificationType.NEW_APPOINTMENT_REQUEST,
            NotificationType.CANCELLED_APPOINTMENT,NotificationType.CHAT_MESSAGE_REVIEVED,
            NotificationType.PATIENT_WAITING,NotificationType.CALL_REMIND_ALERT,
            NotificationType.CALL_REMAINING_TIME_ALERT,NotificationType.PENDING_APPOINTMENTS_REMINDER})
@Retention(RetentionPolicy.SOURCE)
public @interface NotificationType {
    String ADMIN_NOTE="6";  // general msg from admin !
    String NEW_APPOINTMENT_REQUEST="2"; //new  appnt by patient
    String CANCELLED_APPOINTMENT="3";  //cancelled appnt by patient
    String CHAT_MESSAGE_REVIEVED="5"; // chat msg recieved !
    String PATIENT_WAITING="8";    // during call if doctor is not present...
    String CALL_REMIND_ALERT="9"; // a few minutes before the start of the call!
    String CALL_REMAINING_TIME_ALERT="10"; // a few minutes before the end of the call !
    String PENDING_APPOINTMENTS_REMINDER = "12";
}
