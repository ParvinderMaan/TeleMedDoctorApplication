package com.telemed.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.signup.view.SignUpVFragment;

public class TestingActivity extends AppCompatActivity implements RouterFragmentSelectedListener {
    String a="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOiI2ZDViMjIwMS00M2QzLTQwMTItOGMwOS1kNTIwYzQ3NTRhZGEiLCJyb2xlIjoiRG9jdG9yIiwiRGV2aWNlSWQiOiJ0ZXN0IERldmljZUlEIiwibmJmIjoxNTc2MDQxMjE4LCJleHAiOjE1NzYxMjc2MTgsImlhdCI6MTU3NjA0MTIxOH0.TyRcbm9neFAhJAj5badtnaLwrREQz3NnF3-KtbABeFA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fl_container, SignUpVFragment.newInstance(a))
//                .commit();
    }

    @Override
    public void showFragment(String tag, Object payload) {

    }

    @Override
    public void popTopMostFragment() {

    }

    @Override
    public void popTillFragment(String tag, int flag) {

    }

    @Override
    public void abortSignUpDialog() {

    }

    @Override
    public void showSignUpSuccessDialog(String msg) {

    }



    @Override
    public void startActivity(String tag, Object payload) {

    }

    @Override
    public void hideSoftKeyboard() {

    }


}
