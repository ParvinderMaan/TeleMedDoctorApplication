package com.telemed.doctor;

import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.ForgotPasswordFragment;
import com.telemed.doctor.password.OneTimePasswordFragment;
import com.telemed.doctor.profile.ChooseTeritoryFragment;
import com.telemed.doctor.signin.SignInFragment;
import com.telemed.doctor.signup.SignUpIFragment;
import com.telemed.doctor.signup.SignUpIIFragment;
import com.telemed.doctor.signup.SignUpIIIFragment;
import com.telemed.doctor.signup.SignUpIVFragment;
import com.telemed.doctor.signup.SignUpVFragment;
import com.telemed.doctor.splash.SplashFragment;



public class RouterActivity extends BaseActivity implements RouterFragmentSelectedListener {
    // to support vector icon for lower versions
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_router);
        // case 0  -----> fresh launch
        // case 1  ----------> sign out
        int tag = getIntent().getIntExtra("KEY_SIGN_OUT", 0);
        switch (tag) {
            case 0:
                showFragment("SplashFragment", null);
                break;

            case 1:
                showFragment("SignInFragment", null);
                break;
        }


    }

    @Override
    public void showFragment(String tag, String payload) {

        switch (tag) {
            case "SplashFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SplashFragment.newInstance(), "SplashFragment")
//                      .addToBackStack("SplashFragment")
                        .commit();
                break;
            case "SignInFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignInFragment.newInstance(), "SignInFragment")
                        .addToBackStack("SignInFragment")
                        .commit();
                break;

            case "SignUpIFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIFragment.newInstance(), "SignUpIFragment")
                        .addToBackStack("SignUpIFragment")
                        .commit();
                break;
            case "SignUpIIFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIIFragment.newInstance(), "SignUpIIFragment")
                        .addToBackStack("SignUpIIFragment")
                        .commit();
                break;

            case "SignUpIIIFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIIIFragment.newInstance(), "SignUpIIIFragment")
                        .addToBackStack("SignUpIIIFragment")
                        .commit();
                break;


            case "SignUpIVFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIVFragment.newInstance(), "SignUpIVFragment")
                        .addToBackStack("SignUpIVFragment")
                        .commit();
                break;

            case "SignUpVFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpVFragment.newInstance(), "SignUpVFragment")
                        .addToBackStack("SignUpVFragment")
                        .commit();
                break;

            case "ForgotPasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, ForgotPasswordFragment.newInstance(), "ForgotPasswordFragment")
                        .addToBackStack("ForgotPasswordFragment")
                        .commit();
                break;

            case "ChooseTeritoryFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, ChooseTeritoryFragment.newInstance(payload), "ChooseTeritoryFragment")
                        .addToBackStack("ChooseTeritoryFragment")
                        .commit();
                break;

            case "OneTimePasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, OneTimePasswordFragment.newInstance(payload), "OneTimePasswordFragment")
                        .addToBackStack("OneTimePasswordFragment")
                        .commit();
                break;

            default:
                // code block
        }
    }

    @Override
    public void popTopMostFragment() {
        getSupportFragmentManager().popBackStackImmediate();
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
    public void popTillFragment(String tag, int flag) {
        getSupportFragmentManager().popBackStack(tag, flag);

    }

    @Override
    public void startActivity(String tag) {
        if (tag.equals("HomeActivity"))
            startActivity(new Intent(this, HomeActivity.class));



    }

    @Override
    public void hideSoftKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        }


    }

    @Override
    public void sendDataToFragment(String fragmentTag, String data, String tag) {


        switch (fragmentTag) {

            case "SignUpIIFragment":
                SignUpIIFragment mSignUpIIFragment = (SignUpIIFragment) getSupportFragmentManager().findFragmentByTag("SignUpIIFragment");
                if (mSignUpIIFragment != null) {
                    mSignUpIIFragment.updateUi(data, tag);
                }
                break;
        }

    }


}
