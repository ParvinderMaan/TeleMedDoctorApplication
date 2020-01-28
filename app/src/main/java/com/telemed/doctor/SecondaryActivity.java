package com.telemed.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.medicalrecord.MedicalRecordFragment;
import com.telemed.doctor.profile.view.ProfileDocumentFragment;
import com.telemed.doctor.schedule.PatientGalleryFragment;
import com.telemed.doctor.videocall.AppointmentSummaryFragment;

public class SecondaryActivity extends AppCompatActivity implements HomeFragmentSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_secondary);

        if(getIntent()!=null){
            String tag = getIntent().getStringExtra("TAG_FRAGMENT");
            if(tag!=null)
            showFragment(tag, null);
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
                        .add(R.id.fl_container, DoctorDocumentFragment.newInstance())
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
                        .add(R.id.fl_container, MedicalRecordFragment.newInstance())
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
    public void startActivity(String tag, Object object) { }

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
