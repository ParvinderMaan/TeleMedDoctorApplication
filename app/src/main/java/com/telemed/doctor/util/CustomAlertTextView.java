package com.telemed.doctor.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.widget.AppCompatTextView;

import com.telemed.doctor.R;

/**
 * @author Pmaan on 5/12/19.
 *         Copyright © All rights reserved.
 */

public class CustomAlertTextView extends AppCompatTextView {
    private static final int DISPLAY_INTERVAL = 2000;

    public CustomAlertTextView(Context context) {
        super(context);
    }

    public CustomAlertTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAlertTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void showTopAlert(String msg) {
        this.setText(msg);
        TranslateAnimation animate = new TranslateAnimation(0,0,0,0);
        animate.setAnimationListener(mListener);
        animate.setDuration(DISPLAY_INTERVAL);
        animate.setFillAfter(false);
        this.startAnimation(animate);

    }

    private Animation.AnimationListener mListener=new Animation.AnimationListener(){

        @Override
        public void onAnimationStart(Animation animation) {
            CustomAlertTextView.this.setVisibility(View.VISIBLE);

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            CustomAlertTextView.this.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };



     private void release(){

         if(mListener!=null){
             mListener=null;
         }

     }
}
