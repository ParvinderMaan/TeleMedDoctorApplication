package com.telemed.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.ForgotPasswordFragment;
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
                showFragment("SplashFragment");
                break;

            case 1:
                showFragment("SignInFragment");
                break;
        }


    }

    @Override
    public void showFragment(String tag) {

        switch (tag) {
            case "SplashFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SplashFragment.newInstance())
//                      .addToBackStack("SplashFragment")
                        .commit();
                break;
            case "SignInFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignInFragment.newInstance())
                        .addToBackStack("SignInFragment")
                        .commit();
                break;

            case "SignUpIFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIFragment.newInstance())
                        .addToBackStack("SignUpIFragment")
                        .commit();
                break;
            case "SignUpIIFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIIFragment.newInstance())
                        .addToBackStack("SignUpIIFragment")
                        .commit();
                break;

            case "SignUpIIIFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIIIFragment.newInstance())
                        .addToBackStack("SignUpIIIFragment")
                        .commit();
                break;


            case "SignUpIVFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpIVFragment.newInstance())
                        .addToBackStack("SignUpIVFragment")
                        .commit();
                break;

            case "SignUpVFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, SignUpVFragment.newInstance())
                        .addToBackStack("SignUpVFragment")
                        .commit();
                break;

            case "ForgotPasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_container, ForgotPasswordFragment.newInstance())
                        .addToBackStack("ForgotPasswordFragment")
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


    // Note : take care of Toolbar presence
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }




}
