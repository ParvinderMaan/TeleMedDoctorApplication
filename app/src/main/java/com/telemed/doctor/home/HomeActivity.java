package com.telemed.doctor.home;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

import com.telemed.doctor.DoctorDocumentFragment;
import com.telemed.doctor.PatientRatingFragment;
import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.SecondaryActivity;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.broadcastreceiver.InternetBroadcastReceiver;
import com.telemed.doctor.chat.ChatFragment;
import com.telemed.doctor.consult.MyConsultFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.medicalrecord.MedicalRecordFragment;
import com.telemed.doctor.dialog.SignOutDialogFragment;
import com.telemed.doctor.notification.NotificationFragment;
import com.telemed.doctor.password.view.OneTimePasswordFragment;
import com.telemed.doctor.profile.view.ChangeEmailFragment;
import com.telemed.doctor.profile.view.OneTimeFragment;
import com.telemed.doctor.profile.view.ProfileDocumentFragment;
import com.telemed.doctor.profile.view.ProfileFragment;
import com.telemed.doctor.schedule.AppointmentConfirmIFragment;
import com.telemed.doctor.schedule.MyScheduleFragment;
import com.telemed.doctor.schedule.PatientGalleryFragment;
import com.telemed.doctor.dashboard.MyDashboardFragment;
import com.telemed.doctor.miscellaneous.TermAndConditionFragment;
import com.telemed.doctor.password.view.ChangePasswordFragment;
import com.telemed.doctor.schedule.ScheduleSychronizeFragment;
import com.telemed.doctor.setting.SettingFragment;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;
import com.telemed.doctor.videocall.AppointmentSummaryFragment;
import com.telemed.doctor.videocall.VideoCallFragment;
import com.telemed.doctor.videocall.VideoCallTriggerFragment;

//, LifecycleObserver, LifecycleOwner
public class HomeActivity extends BaseActivity implements HomeFragmentSelectedListener {

    // to support vector icon for lower versions
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private CustomAlertTextView tvAlertView;
    private String mAccessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_home);

      // getLifecycle().addObserver(this);



//-------------------------------------------------------------------------------------------------
        tvAlertView = findViewById(R.id.tv_alert_view);
//-------------------------------------------------------------------------------------------------

        if (getIntent() != null) {   // fresh --->
            UserInfoWrapper infoWrap = getIntent().getParcelableExtra("KEY_");
            if (infoWrap != null) {
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
            } else {        // already login

                SharedPrefHelper mHelper = ((TeleMedApplication) getApplicationContext()).getSharedPrefInstance();
                mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");

            }
        }
//-------------------------------------------------------------------------------------------------
        showFragment("HomeFragment", null);
//-------------------------------------------------------------------------------------------------
        registerReceiver(mBroadcastReceiver, intentFilter);
//-------------------------------------------------------------------------------------------------
    }


    // Note : take care of Toolbar presence
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

//-------------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            popTopMostFragment();

        } else {

            finish();
        }
    }

    //-------------------------------------------------------------------------------------------------

    @Override
    public void showFragment(String tag, Object payload) {
        switch (tag) {
            case "HomeFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, HomeFragment.newInstance(), "HomeFragment")
                        .addToBackStack("HomeFragment")
                        .commit();
                break;

            case "ProfileFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ProfileFragment.newInstance())
                        .addToBackStack("ProfileFragment")
                        .commit();
                break;

            case "MyConsultFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, MyConsultFragment.newInstance())
                        .addToBackStack("MyConsultFragment")
                        .commit();
                break;

            case "MyDashboardFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, MyDashboardFragment.newInstance())
                        .addToBackStack("MyDashboardFragment")
                        .commit();
                break;

            case "NotificationFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, NotificationFragment.newInstance())
                        .addToBackStack("NotificationFragment")
                        .commit();
                break;

            case "SettingFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, SettingFragment.newInstance())
                        .addToBackStack("SettingFragment")
                        .commit();
                break;

            case "MyScheduleFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, MyScheduleFragment.newInstance())
                        .addToBackStack("MyScheduleFragment")
                        .commit();
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.fl_container, MyScheduleDemoFragment.newInstance())
//                        .addToBackStack("MyScheduleDemoFragment")
//                        .commit();
                break;

            case "VideoCallTriggerFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, VideoCallTriggerFragment.newInstance())
                        .addToBackStack("VideoCallTriggerFragment")
                        .commit();
                break;
            case "VideoCallFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, VideoCallFragment.newInstance((Object)payload))
                        .addToBackStack("VideoCallFragment")
                        .commit();
                break;

            case "TermAndConditionFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, TermAndConditionFragment.newInstance())
                        .addToBackStack("TermAndConditionFragment")
                        .commit();
                break;

            case "ChangePasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ChangePasswordFragment.newInstance())
                        .addToBackStack("ChangePasswordFragment")
                        .commit();
                break;

            case "AppointmentConfirmIFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, AppointmentConfirmIFragment.newInstance())
                        .addToBackStack("AppointmentConfirmIFragment")
                        .commit();
                break;

            case "PatientGalleryFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, PatientGalleryFragment.newInstance())
                        .addToBackStack("PatientGalleryFragment")
                        .commit();
                break;

            case "ChatFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ChatFragment.newInstance())
                        .addToBackStack("ChatFragment")
                        .commit();
                break;

            case "DoctorDocumentFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, DoctorDocumentFragment.newInstance())
                        .addToBackStack("DoctorDocumentFragment")
                        .commit();
                break;

            case "ScheduleSychronizeFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ScheduleSychronizeFragment.newInstance())
                        .addToBackStack("ScheduleSychronizeFragment")
                        .commit();
                break;

            case "AppointmentSummaryFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, AppointmentSummaryFragment.newInstance())
                        .addToBackStack("AppointmentSummaryFragment")
                        .commit();
                break;

            case "PatientRatingFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, PatientRatingFragment.newInstance())
                        .addToBackStack("PatientRatingFragment")
                        .commit();
                break;
            case "MedicalRecordFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, MedicalRecordFragment.newInstance())
                        .addToBackStack("MedicalRecordFragment")
                        .commit();
                break;
            case "ProfileDocumentFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ProfileDocumentFragment.newInstance())
                        .addToBackStack("ProfileDocumentFragment")
                        .commit();

                break;
            case "UpdateEmailFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fl_container, ChangeEmailFragment.newInstance((Object)payload))
                        .addToBackStack("UpdateEmailFragment")
                        .commit();
                break;

            case "OneTimeFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, OneTimeFragment.newInstance((Object) payload), "OneTimeFragment")
                        .addToBackStack("OneTimeFragment")
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
    public void startActivity(String tag, Object object) {
//    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (tag.equals("RouterActivity")) {
            Intent intent = new Intent(this, RouterActivity.class);
            Bundle b = new Bundle();
            b.putInt("KEY_SIGN_OUT", 1);
            intent.putExtras(b);
            startActivity(intent);
            finish();

        } else if (tag.equals("DeviceSettingActivity")) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);

        } else if (tag.equals("SecondaryActivity")) {
            String whichFragment = (String) object;
            Intent intent = new Intent(this, SecondaryActivity.class);
            Bundle b = new Bundle();
            b.putString("TAG_FRAGMENT", whichFragment);
            intent.putExtras(b);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }
    }

    @Override
    public void signOut() {

        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
        if (fragment != null && fragment.isVisible()) {
            fragment.attemptSignOut(mAccessToken);
        }
    }
//-------------------------------------------------------------------------------------------------

    // broadcaster reciever to detect internet throughout the activity
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
//-------------------------------------------------------------------------------------------------


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        Toast.makeText(getApplicationContext(), "onStartEvent", Toast.LENGTH_SHORT).show();


    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    void stop(){
//        Toast.makeText(getApplicationContext(), "onStopEvent", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    void destroy(){
//        Toast.makeText(getApplicationContext(), "destroyEvent", Toast.LENGTH_SHORT).show();
//
//
//    }





    @Override
    protected void onDestroy() {
//-------------------------------------------------------------------------------------------------
        unregisterReceiver(mBroadcastReceiver);
//-------------------------------------------------------------------------------------------------
        super.onDestroy();
    }
}
