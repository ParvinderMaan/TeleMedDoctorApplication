package com.telemed.doctor.enumeration;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.telemed.doctor.enumeration.FragmentType.SPLASH_FRAGMENT;

// NOT USED YET !!!!!!!!!!!!!!!
//  !!!!!!!!!!!!!!!!!!!!
//      !!!!!!!!!!!!!!!!!!!!
@Retention(RetentionPolicy.SOURCE)
@StringDef({SPLASH_FRAGMENT})
public @interface FragmentType {

    String SPLASH_FRAGMENT = "SplashFragment";
    String SIGN_IN_FRAGMENT= "SignInFragment";
    String SIGN_UP_I_FRAGMENT = "SignUpIFragment";
    String SIGN_UP_II_FRAGMENT= "SignUpIIFragment";
    String SIGN_UP_III_FRAGMENT = "SignUpIIIFragment";
    String SIGN_UP_IV_FRAGMENT = "SignUpIVFragment";
    String SIGN_UP_V_FRAGMENT = "SignUpVFragment";
    String FORGOT_PASSWORD_FRAGMENT = "ForgotPasswordFragment";
    String ONE_TIME_PASSWORD_FRAGMENT = "OneTimePasswordFragment";
    String RESET_PASSWORD_FRAGMENT = "ResetPasswordFragment";

    //ProfileFragment
    //MyConsultFragment
    //MyDashboardFragment
    //NotificationFragment
    //SettingFragment
    //SignOutDialog
    //MyScheduleFragment
    //PatientGalleryFragment
    //AppointmentSummaryFragment
    //DoctorDocumentFragment


}
    // Declare the constants
