package com.telemed.doctor.interfacer;

public interface OnFragmentSelectedListener {


    void showMyProfileFragment();
    void showMyConsultsFragment();
    void showMyDashboardFragment();
    void showVideoCallTriggerFragment();
    void showVideoCallFragment();
    void showSettingFragment();
    void showTermAndConditionFragment();
    void showChangePasswordFragment();
    void showAppointmentConfirmIFragment();
    void showPatientGalleryFragment();
    void showMyScheduleFragment();
    void showScheduleSychronizeFragment();
    void showMedicalRecordFragment();
    void showPatientRatingFragment();
    void showAppointmentSummaryFragment();
    void showDoctorDocumentFragment();
    // temp method ...
    void popTillFragment(String tag, int flag);
    void popTopMostFragment();
    void showSignOutDialog();
}
