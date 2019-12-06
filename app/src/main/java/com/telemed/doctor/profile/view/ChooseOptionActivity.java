package com.telemed.doctor.profile.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseOptionActivity extends AppCompatActivity {
    public static String  COUNTRY="TYPE_COUNTRY";
    public static String  CITY="TYPE_CITY";
    private RecyclerView rvTeritory;
    private RouterFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private TextView tvHeader;
    private String mType;
    private ChooseOptionAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);
        mAdapter=new ChooseOptionAdapter();
       // TAG_BIRTH_CITY TAG_BIRTH_COUNTRY  TAG_NATIONALITY  TAG_SPECIALITY

        if(getIntent()!=null){
            mType=getIntent().getStringExtra("KEY_");

        }
        initView();
        initRecyclerView();


        if(mType.equals("TAG_NATIONALITY")){

            tvHeader.setText("Select Country");
            mAdapter.setData(getDummyNationalityData());


        }else if(mType.equals("TAG_BIRTH_COUNTRY")){

            tvHeader.setText("Select Country");
            mAdapter.setData(getDummyNationalityData());

        }else if(mType.equals("TAG_BIRTH_CITY")){

            tvHeader.setText("Select City");
            mAdapter.setData(getDummyNationalityData());

        }else if(mType.equals("TAG_SPECIALITY")){

            tvHeader.setText("Select Speciality");
            String[] xxx = getResources().getStringArray(R.array.array_speciality);
            mAdapter.setData(Arrays.asList(xxx));

        }

    }

    private void initView() {
        tvHeader=findViewById(R.id.tv_header);
        ibtnClose=findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     onBackPressed();
            }
        });

    }

    private void initRecyclerView() {
        rvTeritory=findViewById(R.id.rv_territory);
        rvTeritory.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(this);
        rvTeritory.setLayoutManager(mLinearLayoutManager);

        rvTeritory.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((model, position) -> {

            switch (mType){

                case "TAG_NATIONALITY":
                    if(mFragmentListener!=null){
                        mFragmentListener.sendDataToFragment("SignUpIIFragment",model,"TAG_NATIONALITY");
                        mFragmentListener.popTopMostFragment();
                    }
                    break;

                case "TAG_BIRTH_COUNTRY":
                    if(mFragmentListener!=null){
                        mFragmentListener.sendDataToFragment("SignUpIIFragment",model,"TAG_BIRTH_COUNTRY");
                        mFragmentListener.popTopMostFragment();
                    }
                    break;
                case "TAG_BIRTH_CITY":
                    if(mFragmentListener!=null){
                        mFragmentListener.sendDataToFragment("SignUpIIFragment",model,"TAG_BIRTH_CITY");
                        mFragmentListener.popTopMostFragment();
                    }
                    break;
                case "TAG_SPECIALITY":
                    if(mFragmentListener!=null){
                        mFragmentListener.sendDataToFragment("SignUpIIFragment",model,"TAG_SPECIALITY");
                        mFragmentListener.popTopMostFragment();
                    }
                    break;


            }


        });

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.custom_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvTeritory.addItemDecoration(dividerItemDecoration);
    }

    private List<String> getDummyNationalityData() {
        List<String> lstOfNation=new ArrayList<>();
        lstOfNation.add("Austria");
        lstOfNation.add("Australia");
        lstOfNation.add("Afghanistan");
        lstOfNation.add("Albania");
        lstOfNation.add("Andorra");
        lstOfNation.add("Angola");

        lstOfNation.add("Belarus");
        lstOfNation.add("Belgium");
        lstOfNation.add("Belize");
        lstOfNation.add("Chile");
        lstOfNation.add("China");
        lstOfNation.add("Colombia");

        lstOfNation.add("Fiji");
        lstOfNation.add("Finland");
        lstOfNation.add("France");

        lstOfNation.add("Austria");
        lstOfNation.add("Australia");
        lstOfNation.add("Afghanistan");
        lstOfNation.add("Albania");
        lstOfNation.add("Andorra");
        lstOfNation.add("Angola");

        return lstOfNation;
    }




}
