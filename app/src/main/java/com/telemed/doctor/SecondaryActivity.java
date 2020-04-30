package com.telemed.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import com.telemed.doctor.document.DoctorDocumentFragment;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.medicalrecord.view.MedicalRecordFragment;
import com.telemed.doctor.signup.model.UserInfoWrapper;

public class SecondaryActivity extends AppCompatActivity implements HomeFragmentSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_secondary);

        if(getIntent()!=null){
            String showFragment = getIntent().getStringExtra("TAG_FRAGMENT");
            Object info = getIntent().getParcelableExtra("TAG_DATA");
            if(showFragment!=null)
            showFragment(showFragment, info);
        }

    }


    @Override
    public void showFragment(String tag, Object payload) {
        switch (tag) {

            case "PatientGalleryFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, PatientGalleryFragment.newInstance())
                        .commit();
                break;

            case "DoctorDocumentFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, DoctorDocumentFragment.newInstance(payload))
                        .commit();
                break;


            case "AppointmentSummaryFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, AppointmentSummaryFragment.newInstance())
                        .commit();
                break;

            case "PatientRatingFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, PatientRatingFragment.newInstance())
                        .commit();
                break;
            case "MedicalRecordFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, MedicalRecordFragment.newInstance((Object)payload))
                        .commit();
                break;

        }
    }

    @Override
    public void popTillFragment(String tag, int flag) { }

    @Override
    public void popTopMostFragment() {
       finish();
    }

    @Override
    public void showDialog(String tag) { }

    @Override
    public void startActivity(String showActivity, String showFragment, Object payload) {



        switch (showActivity) {

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

    }

    @Override
    public void hideSoftKeyboard() {

    }

    @Override
    public void signOut() { }

    // Note : take care of Toolbar presence
    private void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    // temp method
    public void showFragmentPatientRating(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, PatientRatingFragment.newInstance())
                .commit();
    }


}
