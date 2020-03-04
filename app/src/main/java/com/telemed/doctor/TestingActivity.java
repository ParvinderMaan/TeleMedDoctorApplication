package com.telemed.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.profile.model.Country;

import java.util.List;

public class TestingActivity extends BaseActivity {
    private RelativeLayout mRlParent;
    static int id = 1;
    static int idd = 2;

    private LinearLayout llBottom;
    private String whichViewIsSmall;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_testing);

//
//        TeleMedDatabase mDatabase = ((TeleMedApplication) getApplicationContext()).getDatabaseInstance();
//        mDatabase.countryDao().getAllCountries().observe(this, new Observer<List<Country>>() {
//           @Override
//           public void onChanged(List<Country> countries) {
//               for (Country var : countries) {
//                  Log.e("",var.getName());
//               }
//
//           }
//       });


        //----------------------------------------------------------------------------------------------
        initView();
    //    whichViewIsSmall="publisher";
    //----------------------------------------------------------------------------------------------
  //      initListener();
    }

    private void initView() {
        mRlParent = findViewById(R.id.rl_parent);
        llBottom=findViewById(R.id.ll_bottom);

         // subscriber ---->
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        mSubscriberViewContainer = new FrameLayout(this);
        mSubscriberViewContainer.setLayoutParams(params);
        mSubscriberViewContainer.setId(id);
        mSubscriberViewContainer.setBackgroundColor(getResources().getColor(R.color.colorRed));
        mRlParent.addView(mSubscriberViewContainer,0);

        // publisher  ---->
        RelativeLayout.LayoutParams paramss = new RelativeLayout.LayoutParams((int)getResources().getDimension(R.dimen._90sdp),(int)getResources().getDimension(R.dimen._120sdp));
        paramss.setMargins(0, 0, (int)getResources().getDimension(R.dimen._10sdp), 0);
        paramss.addRule(RelativeLayout.ABOVE,llBottom.getId());
        paramss.addRule(RelativeLayout.ALIGN_PARENT_END);
        mPublisherViewContainer = new FrameLayout(this);
        mPublisherViewContainer.setLayoutParams(paramss);
        mPublisherViewContainer.setId(idd);
        mPublisherViewContainer.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        mRlParent.addView(mPublisherViewContainer,1);

    }

    public void toogleView(){

        switch (whichViewIsSmall){

            case "publisher":
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
                params.removeRule(RelativeLayout.ABOVE);
                params.removeRule(RelativeLayout.ALIGN_PARENT_END);
                mPublisherViewContainer.setLayoutParams(params);
                whichViewIsSmall= "subscriber";

                RelativeLayout.LayoutParams paramss = new RelativeLayout.LayoutParams((int)getResources().getDimension(R.dimen._90sdp),
                        (int)getResources().getDimension(R.dimen._120sdp));
                paramss.addRule(RelativeLayout.ABOVE,llBottom.getId());
                paramss.addRule(RelativeLayout.ALIGN_PARENT_END);
                mSubscriberViewContainer.setLayoutParams(paramss);


                break;

            case "subscriber":
                RelativeLayout.LayoutParams paramsss = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
                paramsss.removeRule(RelativeLayout.ABOVE);
                paramsss.removeRule(RelativeLayout.ALIGN_PARENT_END);
                mSubscriberViewContainer.setLayoutParams(paramsss);
                whichViewIsSmall= "publisher";


                RelativeLayout.LayoutParams paramx = new RelativeLayout.LayoutParams((int)getResources().getDimension(R.dimen._90sdp),
                        (int)getResources().getDimension(R.dimen._120sdp));
                paramx.addRule(RelativeLayout.ABOVE,llBottom.getId());
                paramx.addRule(RelativeLayout.ALIGN_PARENT_END);

                mPublisherViewContainer.setLayoutParams(paramx);

                break;





        }

    }

    void initListener() {
        mPublisherViewContainer.setOnClickListener(v -> {
            if(whichViewIsSmall.equals("publisher"))
                toogleView();

        });

        mSubscriberViewContainer.setOnClickListener(v -> {
            if(whichViewIsSmall.equals("subscriber"))
                toogleView();

        });



/*
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


     */
    }


}
/*
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fl_container"
    android:orientation="vertical"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".TestingActivity">
    <LinearLayout
        android:id="@+id/ll_top"
        android:background="@color/colorBlue"
        android:gravity="center"
        android:visibility="visible"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="100dp">
    </LinearLayout>

    <Button
        android:id="@+id/button"
       android:layout_centerInParent="true"
        android:layout_alignParentEnd="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Show" />

    <Button
        android:id="@+id/button2"
        android:layout_centerInParent="true"
        android:layout_alignParentStart="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hide" />

    <Button
        android:id="@+id/button3"
        android:layout_centerInParent="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="ON / OFF"/>


    <LinearLayout
        android:id="@+id/ll_bottom"
        android:background="@color/colorBlue"
        android:layout_alignParentBottom="true"
        android:gravity="center"
        android:visibility="visible"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="100dp">
    </LinearLayout>
</RelativeLayout>
 */