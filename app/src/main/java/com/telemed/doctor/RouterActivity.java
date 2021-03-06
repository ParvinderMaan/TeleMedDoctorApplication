package com.telemed.doctor;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.dialog.SignUpSuccessDialogFragment;
import com.telemed.doctor.home.view.HomeActivity;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.dialog.AbortDialogFragment;
import com.telemed.doctor.password.view.ForgotPasswordFragment;
import com.telemed.doctor.password.view.OneTimePasswordFragment;
import com.telemed.doctor.password.view.ResetPasswordFragment;
import com.telemed.doctor.signin.view.SignInFragment;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.signup.view.SignUpIFragment;
import com.telemed.doctor.signup.view.SignUpIIFragment;
import com.telemed.doctor.signup.view.SignUpIIIFragment;
import com.telemed.doctor.signup.view.SignUpIVFragment;
import com.telemed.doctor.signup.view.SignUpVFragment;
import com.telemed.doctor.splash.SplashFragment;


public class RouterActivity extends BaseActivity implements RouterFragmentSelectedListener {
    private final String TAG = RouterActivity.class.getSimpleName();
    // to support vector icon for lower versions
    static { AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideStatusBar();
        super.onCreate(savedInstanceState);
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
    public void showFragment(String tag, Object payload) {

        switch (tag) {
            case "SplashFragment":
                getSupportFragmentManager().beginTransaction()
                         .add(R.id.fl_container, SplashFragment.newInstance(), "SplashFragment")
                         .commitAllowingStateLoss(); // commitAllowingStateLoss
//                       .addToBackStack("SplashFragment")

                break;
            case "SignInFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, SignInFragment.newInstance(), "SignInFragment")
                        .addToBackStack("SignInFragment")
                        .commit();
                break;

            case "SignUpIFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, SignUpIFragment.newInstance(), "SignUpIFragment")
                        .addToBackStack("SignUpIFragment")
                        .commit();
                break;
            case "SignUpIIFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, SignUpIIFragment.newInstance((Object) payload), "SignUpIIFragment")
                        .addToBackStack("SignUpIIFragment")
                        .commit();
                break;

            case "SignUpIIIFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, SignUpIIIFragment.newInstance((Object) payload), "SignUpIIIFragment")
                        .addToBackStack("SignUpIIIFragment")
                        .commit();
                break;


            case "SignUpIVFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, SignUpIVFragment.newInstance((Object) payload), "SignUpIVFragment")
                        .addToBackStack("SignUpIVFragment")
                        .commit();
                break;

            case "SignUpVFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, SignUpVFragment.newInstance((Object) payload), "SignUpVFragment")
                        .addToBackStack("SignUpVFragment")
                        .commit();
                break;

            case "ForgotPasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, ForgotPasswordFragment.newInstance(), "ForgotPasswordFragment")
                        .addToBackStack("ForgotPasswordFragment")
                        .commit();
                break;


            case "OneTimePasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, OneTimePasswordFragment.newInstance((Object) payload), "OneTimePasswordFragment")
                        .addToBackStack("OneTimePasswordFragment")
                        .commit();
                break;

            case "ResetPasswordFragment":
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .add(R.id.fl_container, ResetPasswordFragment.newInstance((Object) payload), "ResetPasswordFragment")
                        .addToBackStack("ResetPasswordFragment")
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

    // 1  --> finish
    // 2 ---> pop
    // 3,4,5...---> Abort Signup
    @Override
    public void onBackPressed() {
        switch (getTopFragmentName()) {
            case "SignInFragment":
                onBackPressDoubleClick();
                break;

            case "SignUpIFragment":
                popTopMostFragment();
                break;
            case "SignUpIIFragment":
                abortSignUpDialog();
                break;

            case "SignUpIIIFragment":
                abortSignUpDialog();
                break;


            case "SignUpIVFragment":
                abortSignUpDialog();
                break;

            case "SignUpVFragment":
                abortSignUpDialog();
                break;

            case "ForgotPasswordFragment":
                popTopMostFragment();
                break;


            case "OneTimePasswordFragment":
                abortSignUpDialog();
                break;
            default:
                // do nothing when count is One
                finish();


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
    public void popTillFragment(String tag, int flag) {
        getSupportFragmentManager().popBackStack(tag, flag);

    }

    @Override
    public void abortSignUpDialog() {
        AbortDialogFragment fragment = AbortDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), "TAG");
    }

    @Override
    public void showSignUpSuccessDialog(String msg) {
        SignUpSuccessDialogFragment fragment = SignUpSuccessDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(), "TAG");
    }

    @Override
    public void startActivity(String tag, Object payload) {
        if (tag.equals("HomeActivity")) {
            Intent in=new Intent(this, HomeActivity.class);
            in.putExtra("KEY_",((UserInfoWrapper) payload));
            startActivity(in);
            finish();
        }
    }

    @Override
    public void hideSoftKeyboard() {

//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (imm != null) {
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            }
//
//        }

        if (getWindow() != null) {
            getWindow().getDecorView();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
        }

    }


    public String getTopFragmentName() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return "";
        }
        return getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
    }




    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideSoftKeyboard();
        }
        return super.dispatchTouchEvent(ev);
    }





}
