package com.telemed.doctor.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.telemed.doctor.helper.Common;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    protected boolean isNetAvail() {
        return Common.isNetworkAvail(getApplicationContext());
    }

    // Note : take care of Toolbar presence
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }




}
