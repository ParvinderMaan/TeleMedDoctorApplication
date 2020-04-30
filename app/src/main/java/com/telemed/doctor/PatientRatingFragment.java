package com.telemed.doctor;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;


public class PatientRatingFragment extends Fragment {
     private ImageButton ibtnRatingStarOne, ibtnRatingStarTwo, ibtnRatingStarThree,
             ibtnRatingStarFour, ibtnRatingStarFive;
     private ImageButton ibtnClose;
     private HomeFragmentSelectedListener mFragmentListener;

    public  static PatientRatingFragment newInstance() {
       return new PatientRatingFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patient_rating, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        initView(v);

        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {
            if(mFragmentListener!=null){
                mFragmentListener.popTopMostFragment();
            }
                 // requireActivity().finish();
        });
    }

    private void initView(View v) {
        ibtnRatingStarOne=v.findViewById(R.id.ibtn_star_one);
        ibtnRatingStarTwo=v.findViewById(R.id.ibtn_star_two);
        ibtnRatingStarThree=v.findViewById(R.id.ibtn_star_three);
        ibtnRatingStarFour=v.findViewById(R.id.ibtn_star_four);
        ibtnRatingStarFive=v.findViewById(R.id.ibtn_star_five);

        ibtnRatingStarOne.setOnClickListener(mRatingClickListener);
        ibtnRatingStarTwo.setOnClickListener(mRatingClickListener);
        ibtnRatingStarThree.setOnClickListener(mRatingClickListener);
        ibtnRatingStarFour.setOnClickListener(mRatingClickListener);
        ibtnRatingStarFive.setOnClickListener(mRatingClickListener);



        ibtnRatingStarOne.setSelected(true);
        ibtnRatingStarTwo.setSelected(true);

    }


    private View.OnClickListener mRatingClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           switch (v.getId()){

               case R.id.ibtn_star_one:
                   if(!ibtnRatingStarOne.isSelected()){
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(false);
                       ibtnRatingStarThree.setSelected(false);
                       ibtnRatingStarFour.setSelected(false);
                       ibtnRatingStarFive.setSelected(false);
                   }else {
                       ibtnRatingStarOne.setSelected(false);
                       ibtnRatingStarTwo.setSelected(false);
                       ibtnRatingStarThree.setSelected(false);
                       ibtnRatingStarFour.setSelected(false);
                       ibtnRatingStarFive.setSelected(false);
                   }

                   break;
               case R.id.ibtn_star_two:
                   if(!ibtnRatingStarTwo.isSelected()){
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(true);
                       ibtnRatingStarThree.setSelected(false);
                       ibtnRatingStarFour.setSelected(false);
                       ibtnRatingStarFive.setSelected(false);

                   }else {
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(false);
                       ibtnRatingStarThree.setSelected(false);
                       ibtnRatingStarFour.setSelected(false);
                       ibtnRatingStarFive.setSelected(false);

                   }

                   break;
               case R.id.ibtn_star_three:
                   if(!ibtnRatingStarThree.isSelected()){
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(true);
                       ibtnRatingStarThree.setSelected(true);
                       ibtnRatingStarFour.setSelected(false);
                       ibtnRatingStarFive.setSelected(false);
                   }else {
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(true);
                       ibtnRatingStarThree.setSelected(false);
                       ibtnRatingStarFour.setSelected(false);
                       ibtnRatingStarFive.setSelected(false);
                   }

                   break;
               case R.id.ibtn_star_four:
                   if(!ibtnRatingStarFour.isSelected()){
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(true);
                       ibtnRatingStarThree.setSelected(true);
                       ibtnRatingStarFour.setSelected(true);
                       ibtnRatingStarFive.setSelected(false);
                   }else {
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(true);
                       ibtnRatingStarThree.setSelected(true);
                       ibtnRatingStarFour.setSelected(false);
                       ibtnRatingStarFive.setSelected(false);

                   }


                   break;
               case R.id.ibtn_star_five:
                   if(!ibtnRatingStarFive.isSelected()){

                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(true);
                       ibtnRatingStarThree.setSelected(true);
                       ibtnRatingStarFour.setSelected(true);
                       ibtnRatingStarFive.setSelected(true);
                   }else {
                       ibtnRatingStarOne.setSelected(true);
                       ibtnRatingStarTwo.setSelected(true);
                       ibtnRatingStarThree.setSelected(true);
                       ibtnRatingStarFour.setSelected(true);
                       ibtnRatingStarFive.setSelected(false);

                   }

                   break;

           }


        }
    };
}
