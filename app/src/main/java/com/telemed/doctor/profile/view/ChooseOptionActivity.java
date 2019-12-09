package com.telemed.doctor.profile.view;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.profile.viewmodel.ChooseOptionViewModel;
import com.telemed.doctor.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseOptionActivity extends BaseActivity {
    private RecyclerView rvTeritory;
    private ImageButton ibtnClose;
    private TextView tvHeader;
    private String mType;
    private LanguageOptionAdapter mAdapter;
    private ChooseOptionViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_choose_option);
      //  mViewModel = ViewModelProviders.of(this).get(ChooseOptionViewModel.class);

        mAdapter = new LanguageOptionAdapter();
        // mType --> TAG_BIRTH_CITY TAG_BIRTH_COUNTRY  TAG_NATIONALITY  TAG_SPECIALITY

        if (getIntent() != null) {
            mType = getIntent().getStringExtra("KEY_");
        }
        initView();
        initRecyclerView();


//        if (mType.equals("TAG_NATIONALITY")) {
//
//            tvHeader.setText(getResources().getString(R.string.title_select_country));
//            mAdapter.setData(getDummyNationalityData());
//        } else if (mType.equals("TAG_BIRTH_COUNTRY")) {
//
//            tvHeader.setText(getResources().getString(R.string.title_select_country));
//            mAdapter.setData(getDummyNationalityData());
//        } else if (mType.equals("TAG_BIRTH_CITY")) {
//
//            tvHeader.setText(getResources().getString(R.string.title_select_city));
//            mAdapter.setData(getDummyNationalityData());
//        } else if (mType.equals("TAG_SPECIALITY")) {
//
//            tvHeader.setText(getResources().getString(R.string.title_select_speciality));
//            String[] xxx = getResources().getStringArray(R.array.array_speciality);
//            mAdapter.setData(Arrays.asList(xxx));
//        }

    }

    private void initView() {
        tvHeader = findViewById(R.id.tv_header);
        ibtnClose = findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initRecyclerView() {
        rvTeritory = findViewById(R.id.rv_optionz);
        rvTeritory.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        rvTeritory.setLayoutManager(mLinearLayoutManager);

        rvTeritory.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener((model, position) -> {
//            Intent output = new Intent();
//
//            switch (mType) {
//                case "TAG_NATIONALITY":
//                    output.putExtra("KEY_",(String)"TAG_NATIONALITY");
//                    output.putExtra("TAG_NATIONALITY", model);
//                    setResult(RESULT_OK, output);
//                    finish();
//                    break;
//
//                case "TAG_BIRTH_COUNTRY":
//                    output.putExtra("KEY_",(String)"TAG_BIRTH_COUNTRY");
//                    output.putExtra("TAG_BIRTH_COUNTRY", model);
//                    setResult(RESULT_OK, output);
//                    finish();
//                    break;
//                case "TAG_BIRTH_CITY":
//                    output.putExtra("KEY_",(String)"TAG_BIRTH_CITY");
//                    output.putExtra("TAG_BIRTH_CITY", model);
//                    setResult(RESULT_OK, output);
//                    finish();
//                    break;
//                case "TAG_SPECIALITY":
//                    output.putExtra("KEY_",(String)"TAG_SPECIALITY");
//                    output.putExtra("TAG_SPECIALITY", model);
//                    setResult(RESULT_OK, output);
//                    finish();
//                    break;
//
//
//            }
//
//
//        });

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.custom_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvTeritory.addItemDecoration(dividerItemDecoration);
    }

    private List<String> getDummyNationalityData() {
        List<String> lstOfNation = new ArrayList<>();
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
