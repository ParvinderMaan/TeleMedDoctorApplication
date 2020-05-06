package com.telemed.doctor.home.view;

import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.telemed.doctor.document.DoctorDocumentFragment;
import com.telemed.doctor.PatientRatingFragment;
import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.SecondaryActivity;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.broadcastreceiver.InternetBroadcastReceiver;
import com.telemed.doctor.chat.ChatFragment;
import com.telemed.doctor.consult.view.AppointmentHistoryFragment;
import com.telemed.doctor.consult.view.AppointmentUpcomingFragment;
import com.telemed.doctor.consult.view.MyConsultFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.medicalrecord.view.MedicalDetailFragment;
import com.telemed.doctor.medicalrecord.view.MedicalRecordFragment;
import com.telemed.doctor.dialog.SignOutDialogFragment;
import com.telemed.doctor.notification.view.NotificationFragment;
import com.telemed.doctor.profile.view.ChangeEmailFragment;
import com.telemed.doctor.profile.view.OneTimeFragment;
import com.telemed.doctor.profile.view.ProfileDocumentFragment;
import com.telemed.doctor.profile.view.ProfileFragment;
import com.telemed.doctor.appointment.view.AppointmentConfirmIFragment;
import com.telemed.doctor.schedule.model.TimeSlotModel;
import com.telemed.doctor.schedule.view.CreateAvailabilityFragment;
import com.telemed.doctor.schedule.view.DayWiseAvailabilityFragment;
import com.telemed.doctor.schedule.view.DeleteAvailabilityFragment;
import com.telemed.doctor.schedule.view.EditAvailabilityFragment;
import com.telemed.doctor.schedule.view.ScheduleFragment;
import com.telemed.doctor.PatientGalleryFragment;
import com.telemed.doctor.dashboard.MyDashboardFragment;
import com.telemed.doctor.miscellaneous.view.TermAndConditionFragment;
import com.telemed.doctor.password.view.ChangePasswordFragment;
import com.telemed.doctor.schedule.view.ScheduleSychronizeFragment;
import com.telemed.doctor.schedule.view.WeekDayEditAvailabilityFragment;
import com.telemed.doctor.schedule.view.WeekDaysScheduleFragment;
import com.telemed.doctor.setting.view.SettingFragment;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;
import com.telemed.doctor.AppointmentSummaryFragment;
import com.telemed.doctor.videocall.view.VideoCallFragment;
import com.telemed.doctor.videocall.VideoCallTriggerFragment;

public class HomeActivity extends BaseActivity implements HomeFragmentSelectedListener {
    private final String TAG= HomeActivity.class.getSimpleName();
    // to support vector icon for lower versions
    static { AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); }
    private CustomAlertTextView tvAlertView;
    private String mAccessToken;

    // refer manifest..
    // dead with notification !
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
     // Toast.makeText(getApplicationContext(), "onNewIntent callled.."+intent.getStringExtra("KEY_WHICH_FRAGMENT"), Toast.LENGTH_SHORT).show();

        if (intent != null && intent.getParcelableExtra("KEY_") != null) {
//            Bundle bundle = getIntent().getBundleExtra("KEY_");
            TimeSlotModel model=intent.getParcelableExtra("KEY_");
            Log.e(TAG,model.toString());
            String showFragmentName=getIntent().getStringExtra("KEY_WHICH_FRAGMENT");
            switch (showFragmentName){
                case "AppointmentConfirmIFragment": showFragment("AppointmentConfirmIFragment",model);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_home);
//-------------------------------------------------------------------------------------------------
        registerReceiver(mBroadcastReceiver, intentFilter);
//-------------------------------------------------------------------------------------------------

//-------------------------------------------------------------------------------------------------
        tvAlertView = findViewById(R.id.tv_alert_view);
//-------------------------------------------------------------------------------------------------
        if (getIntent() != null) {

            if (getIntent().getParcelableExtra("KEY_") != null) {   // fresh --->
                UserInfoWrapper infoWrap = getIntent().getParcelableExtra("KEY_");
                mAccessToken = infoWrap.getAccessToken();
                String firstName = infoWrap.getFirstName();
                String lastName = infoWrap.getLastName();
                String profilePic = infoWrap.getProfilePic();
                SharedPrefHelper mHelper = ((TeleMedApplication) getApplicationContext()).getSharedPrefInstance();
                mHelper.write(SharedPrefHelper.KEY_ACCESS_TOKEN, mAccessToken);
                mHelper.write(SharedPrefHelper.KEY_FIRST_NAME, firstName);
                mHelper.write(SharedPrefHelper.KEY_LAST_NAME, lastName);
                mHelper.write(SharedPrefHelper.KEY_PROFILE_PIC, profilePic);
                mHelper.write(SharedPrefHelper.KEY_SIGN_IN, true);
//-------------------------------------------------------------------------------------------------
                showFragment("HomeFragment", null);
               // Toast.makeText(getApplicationContext(), "fresh login..", Toast.LENGTH_SHORT).show();
//-------------------------------------------------------------------------------------------------
                return;
            }  // alive it with notification
            else if (getIntent().getStringExtra("KEY_WHICH_FRAGMENT") != null) {
               // Toast.makeText(getApplicationContext(), "NEW FUNCTIONALITY callled..", Toast.LENGTH_SHORT).show();
                showFragment("HomeFragment", null);
                //  showFragment("DayWiseAvailabilityFragment",(Object) "2020-03-28");
//                String fragmentName=getIntent().getStringExtra("KEY_WHICH_FRAGMENT");
//                switch (fragmentName){
//                    case "AppointmentConfirmIFragment":
//                        showFragment("HomeFragment", null);
//                        TimeSlotModel model=getIntent().getParcelableExtra("KEY_");
//                        showFragment("AppointmentConfirmIFragment",model);
//                        break;
//                    default:
//                        showFragment("HomeFragment", null);
//                }

                return;
            }
            else {   // already login--->
                SharedPrefHelper mHelper = ((TeleMedApplication) getApplicationContext()).getSharedPrefInstance();
                mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
                showFragment("HomeFragment", null);
              //  Toast.makeText(getApplicationContext(), "already login .."+getIntent().getStringExtra("KEY_WHICH_FRAGMENT"), Toast.LENGTH_SHORT).show();
                return;
            }
        }

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
            onBackPressDoubleClick();
        }
    }

    private void onBackPressDoubleClick() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click back again to exit", Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                handler.removeCallbacks(this);
            }
        }, 2000);

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void showFragment(String tag, Object payload) {
        switch (tag) {
            case "HomeFragment":
                getSupportFragmentManager().beginTransaction()
//                      .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, HomeFragment.newInstance(), "HomeFragment")
                        .addToBackStack("HomeFragment")
                        .commit();
                break;

            case "ProfileFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ProfileFragment.newInstance(), "ProfileFragment")
                        .addToBackStack("ProfileFragment")
                        .commit();
                break;

            case "MyConsultFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, MyConsultFragment.newInstance(), "MyConsultFragment")
                        .addToBackStack("MyConsultFragment")
                        .commit();
                break;

            case "MyDashboardFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, MyDashboardFragment.newInstance(), "MyDashboardFragment")
                        .addToBackStack("MyDashboardFragment")
                        .commit();
                break;

            case "NotificationFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, NotificationFragment.newInstance(), "NotificationFragment")
                        .addToBackStack("NotificationFragment")
                        .commit();
                break;

            case "SettingFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, SettingFragment.newInstance(), "SettingFragment")
                        .addToBackStack("SettingFragment")
                        .commit();
                break;

            case "ScheduleFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, ScheduleFragment.newInstance(), "ScheduleFragment")
                        .addToBackStack("ScheduleFragment")
                        .commit();

                break;

            case "VideoCallTriggerFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, VideoCallTriggerFragment.newInstance(), "VideoCallTriggerFragment")
                        .addToBackStack("VideoCallTriggerFragment")
                        .commit();
                break;
            case "VideoCallFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, VideoCallFragment.newInstance((Object) payload), "VideoCallFragment")
                        .addToBackStack("VideoCallFragment")
                        .commit();
                break;

            case "TermAndConditionFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, TermAndConditionFragment.newInstance(), "TermAndConditionFragment")
                        .addToBackStack("TermAndConditionFragment")
                        .commit();
                break;

            case "ChangePasswordFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ChangePasswordFragment.newInstance(), "ChangePasswordFragment")
                        .addToBackStack("ChangePasswordFragment")
                        .commit();
                break;

            case "AppointmentConfirmIFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, AppointmentConfirmIFragment.newInstance((Object) payload), "AppointmentConfirmIFragment")
                        .addToBackStack("AppointmentConfirmIFragment")
                        .commit();
                break;

            case "PatientGalleryFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, PatientGalleryFragment.newInstance(), "PatientGalleryFragment")
                        .addToBackStack("PatientGalleryFragment")
                        .commit();
                break;

            case "ChatFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ChatFragment.newInstance(), "ChatFragment")
                        .addToBackStack("ChatFragment")
                        .commit();
                break;

            case "DoctorDocumentFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, DoctorDocumentFragment.newInstance(payload), "DoctorDocumentFragment")
                        .addToBackStack("DoctorDocumentFragment")
                        .commit();
                break;

            case "ScheduleSychronizeFragment":
                getSupportFragmentManager().beginTransaction()
                     //   .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, ScheduleSychronizeFragment.newInstance(), "ScheduleSychronizeFragment")
                        .addToBackStack("ScheduleSychronizeFragment")
                        .commit();
                break;

            case "AppointmentSummaryFragment":
                getSupportFragmentManager().beginTransaction()
                     //   .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, AppointmentSummaryFragment.newInstance(), "AppointmentSummaryFragment")
                        .addToBackStack("AppointmentSummaryFragment")
                        .commit();
                break;

            case "PatientRatingFragment":
                getSupportFragmentManager().beginTransaction()
                     //   .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, PatientRatingFragment.newInstance(), "PatientRatingFragment")
                        .addToBackStack("PatientRatingFragment")
                        .commit();
                break;
            case "MedicalRecordFragment":
                getSupportFragmentManager().beginTransaction()
                     //   .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, MedicalRecordFragment.newInstance((Object) payload), "MedicalRecordFragment")
                        .addToBackStack("MedicalRecordFragment")
                        .commit();
                break;

            case "MedicalDetailFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, MedicalDetailFragment.newInstance((Object) payload), "MedicalDetailFragment")
                        .addToBackStack("MedicalDetailFragment")
                        .commit();
                break;
            case "ProfileDocumentFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ProfileDocumentFragment.newInstance(), "ProfileDocumentFragment")
                        .addToBackStack("ProfileDocumentFragment")
                        .commit();

                break;
            case "UpdateEmailFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ChangeEmailFragment.newInstance((Object) payload), "UpdateEmailFragment")
                        .addToBackStack("UpdateEmailFragment")
                        .commit();
                break;

            case "OneTimeFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, OneTimeFragment.newInstance((Object) payload), "OneTimeFragment")
                        .addToBackStack("OneTimeFragment")
                        .commit();
                break;

            case "DayWiseAvailabilityFragment":
                getSupportFragmentManager().beginTransaction()
                       // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, DayWiseAvailabilityFragment.newInstance((Object) payload), "DayWiseAvailabilityFragment")
                        .addToBackStack("DayWiseAvailabilityFragment")
                        .commit();
                break;


            case "WeekDaysScheduleFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, WeekDaysScheduleFragment.newInstance((Object) payload), "WeekDaysScheduleFragment")
                        .addToBackStack("WeekDaysScheduleFragment")
                        .commit();
                break;


            case "AppointmentUpcomingFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, AppointmentUpcomingFragment.newInstance((Object) payload), "AppointmentUpcomingFragment")
                        .addToBackStack("AppointmentUpcomingFragment")
                        .commit();
                break;

            case "AppointmentHistoryFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, AppointmentHistoryFragment.newInstance((Object) payload), "AppointmentHistoryFragment")
                        .addToBackStack("AppointmentHistoryFragment")
                        .commit();
                break;


            case "DeleteAvailabilityFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, DeleteAvailabilityFragment.newInstance(), "DeleteAvailabilityFragment")
                        .addToBackStack("DeleteAvailabilityFragment")
                        .commit();
                break;

            case "CreateAvailabilityFragment":
                getSupportFragmentManager().beginTransaction()
                      //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, CreateAvailabilityFragment.newInstance((Object) payload), "CreateAvailabilityFragment")
                        .addToBackStack("CreateAvailabilityFragment")
                        .commit();
                break;

            case "EditAvailabilityFragment":
                getSupportFragmentManager().beginTransaction()
                     //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, EditAvailabilityFragment.newInstance((Object) payload), "EditAvailabilityFragment")
                        .addToBackStack("EditAvailabilityFragment")
                        .commit();
                break;

            case "WeekDayEditAvailabilityFragment":
                getSupportFragmentManager().beginTransaction()
                        //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fl_container, WeekDayEditAvailabilityFragment.newInstance((Object) payload), "WeekDayEditAvailabilityFragment")
                        .addToBackStack("WeekDayEditAvailabilityFragment")
                        .commit();

                break;
        }

    }

    @Override
    public void popTillFragment(String tag, int flag) {
        getSupportFragmentManager().popBackStack(tag, flag);
    }


    @Override
    public void popTopMostFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void showDialog(String tag) {
        if (tag.equals("SignOutDialog")) {
            SignOutDialogFragment fragment = SignOutDialogFragment.newInstance();
            fragment.show(getSupportFragmentManager(), "TAG");
        }
    }

    @Override
    public void startActivity(String showActivity, String showFragment, Object payload) {

        switch (showActivity) {
            case "RouterActivity":
                Intent intent = new Intent(this, RouterActivity.class);
                Bundle b = new Bundle();
                b.putInt("KEY_SIGN_OUT", 1);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                break;
            case "DeviceSettingActivity":
                Intent intent2 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent2.setData(uri);
                startActivity(intent2);
                break;
            case "SecondaryActivity":
                Intent intent3 = new Intent(this, SecondaryActivity.class);
                Bundle bb = new Bundle();
                bb.putString("TAG_FRAGMENT", showFragment);
                bb.putParcelable("TAG_DATA", (UserInfoWrapper)payload);
                intent3.putExtras(bb);
                startActivity(intent3);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
        }

    }

    @Override
    public void refreshFragment(String tag) {
        switch (tag) {
            case "HomeFragment":

                break;

            case "ProfileFragment":

                break;

            case "MyConsultFragment":

                break;

            case "MyDashboardFragment":

                break;

            case "NotificationFragment":

                break;

            case "SettingFragment":

                break;

            case "ScheduleFragment": {
//                ScheduleFragment fragment = (ScheduleFragment) getSupportFragmentManager().findFragmentByTag("ScheduleFragment");
//                if (fragment != null)
//                    fragment.refreshUi();
            }
            break;

            case "VideoCallTriggerFragment":

                break;
            case "VideoCallFragment":

                break;

            case "TermAndConditionFragment":

                break;

            case "ChangePasswordFragment":

                break;

            case "AppointmentConfirmIFragment":

                break;

            case "PatientGalleryFragment":

                break;

            case "ChatFragment":

                break;

            case "DoctorDocumentFragment":

                break;

            case "ScheduleSychronizeFragment":

                break;

            case "AppointmentSummaryFragment":

                break;

            case "PatientRatingFragment":

                break;
            case "MedicalRecordFragment":

                break;
            case "ProfileDocumentFragment":

                break;
            case "UpdateEmailFragment":

                break;

            case "OneTimeFragment":

                break;

            case "DayWiseAvailabilityFragment": {
//                DayWiseAvailabilityFragment fragment = (DayWiseAvailabilityFragment) getSupportFragmentManager()
//                        .findFragmentByTag("DayWiseAvailabilityFragment");
//                if (fragment != null)
//                    fragment.refreshUi();
            }
            break;


            case "WeekDaysScheduleFragment":

                break;


            case "AppointmentUpcomingFragment":

                break;

            case "AppointmentHistoryFragment":

                break;

        }


    }

    @Override
    public void hideSoftKeyboard() { }

    @Override
    public void signOut() {
        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
        if (fragment != null && fragment.isVisible()) {
            fragment.attemptSignOut(mAccessToken);
        }
    }

    // detect internet throughout
    private final IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    private InternetBroadcastReceiver mBroadcastReceiver = new InternetBroadcastReceiver() {
        @Override
        public void onConnectionChanged() {
            if (isNetAvail()) {
                tvAlertView.showTopAlert("You are online");
                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            } else {
                tvAlertView.showTopAlert("You are offline");
                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorRed));
            }

        }
    };


    @Override
    protected void onDestroy() {
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
        super.onDestroy();
    }
}
