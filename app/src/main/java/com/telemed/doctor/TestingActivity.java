package com.telemed.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

public class TestingActivity extends AppCompatActivity {
    private LinearLayout llTop,llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
         llTop = findViewById(R.id.ll_top);
         llBottom = findViewById(R.id.ll_bottom);


         Button btnShow = findViewById(R.id.button);
         btnShow.setOnClickListener(v -> {
             ObjectAnimator objectAnimY=ObjectAnimator.ofFloat(llTop,"y",0.0f);
             objectAnimY.setDuration(200);
//-----------------------------------------------------------------------------------------------------------------
             ObjectAnimator objectAnimYY=ObjectAnimator.ofFloat(llBottom,"y",llBottom.getY()-llBottom.getHeight());
             objectAnimYY.setDuration(200);
//-----------------------------------------------------------------------------------------------------------------
              AnimatorSet animatorSet = new AnimatorSet();
              animatorSet.play(objectAnimY).with(objectAnimYY);
              animatorSet.start();

         });

        Button btnHide = findViewById(R.id.button2);
        btnHide.setOnClickListener(v -> {
            ObjectAnimator objectAnimY=ObjectAnimator.ofFloat(llTop,"y",(float)-llTop.getHeight());
            objectAnimY.setDuration(200);
//-----------------------------------------------------------------------------------------------------------------
            ObjectAnimator objectAnimYY=ObjectAnimator.ofFloat(llBottom,"y",(llBottom.getY()+llBottom.getHeight()));
            objectAnimYY.setDuration(200);
//-----------------------------------------------------------------------------------------------------------------
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(objectAnimY).with(objectAnimYY);
            animatorSet.start();
        });

    }




}
