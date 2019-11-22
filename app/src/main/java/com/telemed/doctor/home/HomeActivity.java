package com.telemed.doctor.home;

import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.telemed.doctor.DoctorDocumentFragment;
import com.telemed.doctor.PatientRatingFragment;
import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.chat.ChatFragment;
import com.telemed.doctor.consult.MyConsultFragment;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
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


public class HomeActivity extends BaseActivity implements HomeFragmentSelectedListener {

    // to support vector icon for lower versions
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_home);
        showFragment("HomeFragment");
    }

    // Note : take care of Toolbar presence
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            popTopMostFragment();

        } else {

            finish();
        }
    }


    @Override
    public void showFragment(String tag) {
        switch (tag) {
            case "HomeFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, HomeFragment.newInstance())
                        .addToBackStack("HomeFragment")
                        .commit();
                break;

            case "ProfileFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, HomeFragment.newInstance())
                        .addToBackStack("ProfileFragment")
                        .commit();
                break;

            case "MyConsultFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, MyConsultFragment.newInstance())
                        .addToBackStack("MyConsultFragment")
                        .commit();
                break;

            case "MyDashboardFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, MyDashboardFragment.newInstance())
                        .addToBackStack("MyDashboardFragment")
                        .commit();
                break;

            case "NotificationFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, NotificationFragment.newInstance())
                        .addToBackStack("NotificationFragment")
                        .commit();
                break;

            case "SettingFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SettingFragment.newInstance())
                        .addToBackStack("SettingFragment")
                        .commit();
                break;

            case "MyScheduleFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, MyScheduleFragment.newInstance())
                        .addToBackStack("MyScheduleFragment")
                        .commit();
                break;

            case "VideoCallTriggerFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, VideoCallTriggerFragment.newInstance())
                        .addToBackStack("VideoCallTriggerFragment")
                        .commit();
                break;
            case "VideoCallFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, VideoCallFragment.newInstance())
                        .addToBackStack("VideoCallFragment")
                        .commit();
                break;

            case "TermAndConditionFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, TermAndConditionFragment.newInstance())
                        .addToBackStack("TermAndConditionFragment")
                        .commit();
                break;

            case "ChangePasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, ChangePasswordFragment.newInstance())
                        .addToBackStack("ChangePasswordFragment")
                        .commit();
                break;

            case "AppointmentConfirmIFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, AppointmentConfirmIFragment.newInstance())
                        .addToBackStack("AppointmentConfirmIFragment")
                        .commit();
                break;

            case "PatientGalleryFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, PatientGalleryFragment.newInstance())
                        .addToBackStack("PatientGalleryFragment")
                        .commit();
                break;

            case "ChatFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, ChatFragment.newInstance())
                        .addToBackStack("ChatFragment")
                        .commit();
                break;

            case "DoctorDocumentFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, DoctorDocumentFragment.newInstance())
                        .addToBackStack("DoctorDocumentFragment")
                        .commit();
                break;

            case "ScheduleSychronizeFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, ScheduleSychronizeFragment.newInstance())
                        .addToBackStack("ScheduleSychronizeFragment")
                        .commit();
                break;

            case "AppointmentSummaryFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, AppointmentSummaryFragment.newInstance())
                        .addToBackStack("AppointmentSummaryFragment")
                        .commit();
                break;

            case "PatientRatingFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, PatientRatingFragment.newInstance())
                        .addToBackStack("PatientRatingFragment")
                        .commit();
                break;
            case "MedicalRecordFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, MedicalRecordFragment.newInstance())
                        .addToBackStack("MedicalRecordFragment")
                        .commit();
                break;
            default:
                // code block

        }

    }

    @Override
    public void popTillFragment(String tag, int flag) {
        getSupportFragmentManager().popBackStack(tag, flag);

    }


    @Override
    public void popTopMostFragment() {
        getSupportFragmentManager().popBackStackImmediate();

    }

    @Override
    public void showDialog(String tag) {
        if (tag.equals("SignOutDialog")) {
            SignOutDialogFragment fragment = SignOutDialogFragment.newInstance();
            fragment.show(getSupportFragmentManager(), "TAG");
        }
    }

    @Override
    public void startActivity(String tag) {
        if(tag.equals("RouterActivity")){
            Intent intent=new Intent(this, RouterActivity.class);
//          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Bundle b=new Bundle();
            b.putInt("KEY_SIGN_OUT",1);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }


}
