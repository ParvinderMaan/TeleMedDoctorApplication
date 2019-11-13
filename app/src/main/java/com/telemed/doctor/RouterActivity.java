package com.telemed.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.WindowManager;

import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.home.HomeFragment;
import com.telemed.doctor.password.ForgotPasswordFragment;
import com.telemed.doctor.signin.SignInFragment;
import com.telemed.doctor.signup.SignUpIFragment;
import com.telemed.doctor.signup.SignUpIIFragment;
import com.telemed.doctor.signup.SignUpIIIFragment;
import com.telemed.doctor.splash.SplashFragment;

public class RouterActivity extends BaseActivity {
    // to support vector icon for lower versions
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_router);
        showSplashFragment();
        // -----> fresh launch  case  (getBundle()==?)
        // or ----------> sign out case

    }
    public void showSplashFragment() {
        getSupportFragmentManager().beginTransaction()
                 .add(R.id.fl_container, SplashFragment.newInstance())
                 .addToBackStack("SplashFragment")
                 .commit();

    }

    public void showSignInFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, SignInFragment.newInstance())
                .addToBackStack("SignInFragment")
                .commit();


    }

    public void showSignUpIFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, SignUpIFragment.newInstance())
                .addToBackStack("SignUpIFragment")
                .commit();



    }

    public void showSignUpIIFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, SignUpIIFragment.newInstance())
                .addToBackStack("SignUpIIFragment")
                .commit();

    }
    public void showSignUpIIIFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, SignUpIIIFragment.newInstance())
                .addToBackStack("SignUpIIIFragment")
                .commit();

    }


    public void showForgotPasswordFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, ForgotPasswordFragment.newInstance())
                .addToBackStack("ForgotPasswordFragment")
                .commit();

    }

    public void popTopMostFragment() {
        getSupportFragmentManager().popBackStackImmediate();
    }



    // Note : take care of Toolbar presence
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()>2){
            popTopMostFragment();

        }else{
            finish();
        }
    }
}
