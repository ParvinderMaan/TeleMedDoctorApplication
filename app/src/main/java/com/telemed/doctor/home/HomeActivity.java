package com.telemed.doctor.home;

import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.WindowManager;

import com.telemed.doctor.DoctorDocumentFragment;
import com.telemed.doctor.PatientRatingFragment;
import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.chat.ChatFragment;
import com.telemed.doctor.consult.MyConsultFragment;
import com.telemed.doctor.medicalrecord.MedicalRecordFragment;
import com.telemed.doctor.miscellaneous.SignOutDialogFragment;
import com.telemed.doctor.notification.NotificationFragment;
import com.telemed.doctor.profile.ProfileFragment;
import com.telemed.doctor.schedule.AppointmentConfirmIFragment;
import com.telemed.doctor.schedule.PatientGalleryFragment;
import com.telemed.doctor.dashboard.MyDashboardFragment;
import com.telemed.doctor.miscellaneous.TermAndConditionFragment;
import com.telemed.doctor.password.ChangePasswordFragment;
import com.telemed.doctor.schedule.MyScheduleFragment;
import com.telemed.doctor.schedule.ScheduleSychronizeFragment;
import com.telemed.doctor.setting.SettingFragment;
import com.telemed.doctor.videocall.AppointmentSummaryFragment;
import com.telemed.doctor.videocall.VideoCallFragment;
import com.telemed.doctor.videocall.VideoCallTriggerFragment;



public class HomeActivity extends BaseActivity {

    // to support vector icon for lower versions
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_home);
        showHomeFragment();

    }

    // Note : take care of Toolbar presence
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showHomeFragment() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, HomeFragment.newInstance())
                .addToBackStack("HomeFragment")
                .commit();
    }

    public void showMyProfileFragment() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, ProfileFragment.newInstance())
                .addToBackStack("ProfileFragment")
                .commit();
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            popTopMostFragment();

        }else{

            finish();
        }
    }

    public void showMyConsultsFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, MyConsultFragment.newInstance())
                .addToBackStack("MyConsultFragment")
                .commit();

    }

    public void showMyDashboardFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, MyDashboardFragment.newInstance())
                .addToBackStack("MyDashboardFragment")
                .commit();

    }


    public void showVideoCallTriggerFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, VideoCallTriggerFragment.newInstance())
                .addToBackStack("VideoCallTriggerFragment")
                .commit();

    }


    public void showVideoCallFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, VideoCallFragment.newInstance())
                .addToBackStack("VideoCallFragment")
                .commit();

    }


   

    public void showSettingFragment() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, SettingFragment.newInstance())
                .addToBackStack("SettingFragment")
                .commit();
    }
    public void showTermAndConditionFragment() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, TermAndConditionFragment.newInstance())
                .addToBackStack("TermAndConditionFragment")
                .commit();
    }

    public void showChangePasswordFragment() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, ChangePasswordFragment.newInstance())
                .addToBackStack("ChangePasswordFragment")
                .commit();
    }

    public void showAppointmentConfirmIFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, AppointmentConfirmIFragment.newInstance())
                .addToBackStack("AppointmentConfirmIFragment")
                .commit();
    }

    public void showPatientGalleryFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, PatientGalleryFragment.newInstance())
                .addToBackStack("PatientGalleryFragment")
                .commit();

    }

    public void showMyScheduleFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, MyScheduleFragment.newInstance())
                .addToBackStack("MyScheduleFragment")
                .commit();
    }

    public void showScheduleSychronizeFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, ScheduleSychronizeFragment.newInstance())
                .addToBackStack("ScheduleSychronizeFragment")
                .commit();
    }

    public void showMedicalRecordFragment() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, MedicalRecordFragment.newInstance())
                .addToBackStack("MedicalRecordFragment")
                .commit();
    }


    public void showPatientRatingFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, PatientRatingFragment.newInstance())
                .addToBackStack("PatientRatingFragment")
                .commit();

    }

    public void showAppointmentSummaryFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, AppointmentSummaryFragment.newInstance())
                .addToBackStack("AppointmentSummaryFragment")
                .commit();

    }

    public void showDoctorDocumentFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, DoctorDocumentFragment.newInstance())
                .addToBackStack("DoctorDocumentFragment")
                .commit();

    }

     // temp method ...
    public void popTillFragment(String tag, int flag) {
        getSupportFragmentManager().popBackStack(tag,flag);

    }

    public void popTopMostFragment() {
        getSupportFragmentManager().popBackStackImmediate();

    }

    public void showSignOutDialog() {
        SignOutDialogFragment fragment= SignOutDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(),"TAG");
    }

    public void showNotificationFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, NotificationFragment.newInstance())
                .addToBackStack("NotificationFragment")
                .commit();

    }

    public void showChatFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, ChatFragment.newInstance())
                .addToBackStack("ChatFragment")
                .commit();


    }
}
