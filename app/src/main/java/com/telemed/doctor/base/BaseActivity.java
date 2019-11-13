package com.telemed.doctor.base;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.telemed.doctor.helper.Common;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    protected boolean isNetAvail() {
        return Common.isNetworkAvail(getApplicationContext());
    }

    protected void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
