package com.telemed.doctor.profile.view;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseActivity;
import com.telemed.doctor.profile.model.Country;
import com.telemed.doctor.profile.model.Gender;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.profile.model.OptionResponse;
import com.telemed.doctor.profile.model.Speciliaty;
import com.telemed.doctor.profile.model.State;
import com.telemed.doctor.profile.model.StateResponse;
import com.telemed.doctor.profile.viewmodel.ChooseOptionViewModel;
import com.telemed.doctor.util.DividerItemDecoration;

import java.util.List;

public class ChooseOptionActivity extends BaseActivity {
    private RecyclerView rvOptionz;
    private ImageButton ibtnClose;
    private TextView tvHeader;
    private TextView tvAlertView;
    private ProgressBar progressBar;
    private String mType;
    //@adapter
    private LanguageOptionAdapter mLanguageOneAdapter;
    private LanguageOptionAdapter mLanguageTwoAdapter;
    private GenderOptionAdapter mGenderAdapter;
    private SpecialityOptionAdapter mSpecialityAdapter;
    private CountryOptionAdapter mCountryAdapter;
    private NationalityOptionAdapter mNationalityAdapter;
    private StateOptionAdapter mStateAdapter;

    private ChooseOptionViewModel mViewModel;
    private String mCountryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_choose_option);
        mViewModel = ViewModelProviders.of(this).get(ChooseOptionViewModel.class);


        if (getIntent() != null) {
            mType = getIntent().getStringExtra("KEY_");
            mCountryId = getIntent().getStringExtra("KEY_COUNTRY_ID");
        }
        initView();
        initRecyclerView();
        initValue();

        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getStateResultant().observe(this, response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        StateResponse.Data result = response.getData().getData();
                        mViewModel.setStateList(result.getStates());
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.setVisibility(View.VISIBLE);
                        tvAlertView.setText(response.getErrorMsg());
                        tvAlertView.append("\n Click to retry");
                    }
                    break;

            }

        });

        mViewModel.getResultant().observe(this, response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        tvAlertView.setVisibility(View.GONE);
                        OptionResponse.Data result = response.getData().getData();
                        mViewModel.setGenderList(result.getGender());
                        mViewModel.setLanguageList(result.getLanguages());
                        mViewModel.setSpecialityList(result.getSpeciliaties());
                        mViewModel.setCountryList(result.getCountries());
                        mViewModel.setNationalityList(result.getNationalities());
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.setVisibility(View.VISIBLE);
                        tvAlertView.setText(response.getErrorMsg());
                        tvAlertView.append("\n Click to retry");
                    }
                    break;

            }

        });






    }

    private void initView() {
        tvAlertView = findViewById(R.id.tv_alert_view);
        progressBar = findViewById(R.id.progress_bar);
        tvHeader = findViewById(R.id.tv_header);
        ibtnClose = findViewById(R.id.ibtn_close);

        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView.setOnClickListener(v->{
            if(mCountryId==null){
                mViewModel.fetchDoctorDrills();

            }else{
                mViewModel.fetchStateFromCountry(mCountryId);

            }
        });

        ibtnClose.setOnClickListener(v->{
            onBackPressed();
        });

    }

    private void initRecyclerView() {
        rvOptionz = findViewById(R.id.rv_optionz);
        rvOptionz.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        rvOptionz.setLayoutManager(mLinearLayoutManager);


        mLanguageOneAdapter=new LanguageOptionAdapter();
        mLanguageTwoAdapter=new LanguageOptionAdapter();
        mSpecialityAdapter=new SpecialityOptionAdapter();
        mNationalityAdapter=new NationalityOptionAdapter();
        mCountryAdapter=new CountryOptionAdapter();
        mGenderAdapter=new GenderOptionAdapter();
        mStateAdapter=new StateOptionAdapter();


        mLanguageOneAdapter.setListener(item -> {
            Intent intent = new Intent();
            intent.putExtra("KEY_", "TAG_LANGUAGE_ONE");
            intent.putExtra("KEY_NAME",item.getName());
            intent.putExtra("KEY_ID",item.getId());
            setResult(RESULT_OK, intent);
            finish();
        });

        mLanguageTwoAdapter.setListener(item -> {
            Intent intent = new Intent();
            intent.putExtra("KEY_", "TAG_LANGUAGE_TWO");
            intent.putExtra("KEY_NAME",item.getName());
            intent.putExtra("KEY_ID",item.getId());
            setResult(RESULT_OK, intent);
            finish();
        });


        mGenderAdapter.setListener(item -> {
            Intent intent = new Intent();
            intent.putExtra("KEY_", "TAG_GENDER");
            intent.putExtra("KEY_NAME",item.getName());
            intent.putExtra("KEY_ID",item.getId());
            setResult(RESULT_OK, intent);
            finish();

        });

        mSpecialityAdapter.setListener(item -> {
            Intent intent = new Intent();
            intent.putExtra("KEY_", "TAG_SPECIALITY");
            intent.putExtra("KEY_NAME",item.getName());
            intent.putExtra("KEY_ID",item.getId());
            setResult(RESULT_OK, intent);
            finish();
        });


        mCountryAdapter.setListener(item -> {
            Intent intent = new Intent();
            intent.putExtra("KEY_", "TAG_COUNTRY");
            intent.putExtra("KEY_NAME",item.getName());
            intent.putExtra("KEY_ID",item.getId());
            setResult(RESULT_OK, intent);
            finish();


        });

        mNationalityAdapter.setListener(item -> {
            Intent intent = new Intent();
            intent.putExtra("KEY_", "TAG_NATIONALITY");
            intent.putExtra("KEY_NAME",item.getName());
            intent.putExtra("KEY_ID",item.getId());
            setResult(RESULT_OK, intent);
            finish();
        });


        mStateAdapter.setListener(item -> {
            Intent intent = new Intent();
            intent.putExtra("KEY_", "TAG_STATE");
            intent.putExtra("KEY_NAME",item.getName());
            intent.putExtra("KEY_ID",item.getId());
            setResult(RESULT_OK, intent);
            finish();
        });


        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.custom_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvOptionz.addItemDecoration(dividerItemDecoration);
    }

    private void initValue() {
        switch (mType) {
            case "TAG_NATIONALITY":
                tvHeader.setText(getResources().getString(R.string.title_select_nationality));
                rvOptionz.setAdapter(mNationalityAdapter);
                mViewModel.getNationalities().observe(this, mNationalityObserver);
                mViewModel.fetchDoctorDrills();

                break;

            case "TAG_STATE":
                tvHeader.setText(getResources().getString(R.string.title_select_state));
                rvOptionz.setAdapter(mStateAdapter);
                mViewModel.getState().observe(this, mStateObserver);
                mViewModel.fetchStateFromCountry(mCountryId); //
                break;

            case "TAG_SPECIALITY":
                tvHeader.setText(getResources().getString(R.string.title_select_speciality));
                rvOptionz.setAdapter(mSpecialityAdapter);
                mViewModel.getSpeciality().observe(this, mSpecialityObserver);
                mViewModel.fetchDoctorDrills();
                break;

            case "TAG_LANGUAGE_ONE":
                tvHeader.setText(getResources().getString(R.string.title_select_language));
                mViewModel.getLanguage().observe(this, mLanguageObserver);
                rvOptionz.setAdapter(mLanguageOneAdapter);
                mViewModel.fetchDoctorDrills();
                break;

            case "TAG_LANGUAGE_TWO":
                tvHeader.setText(getResources().getString(R.string.title_select_language));
                rvOptionz.setAdapter(mLanguageTwoAdapter);
                mViewModel.getLanguage().observe(this, mLanguageObserver);
                mViewModel.fetchDoctorDrills();
                break;

            case "TAG_GENDER":
                tvHeader.setText(getResources().getString(R.string.title_select_gender));
                rvOptionz.setAdapter(mGenderAdapter);
                mViewModel.getGender().observe(this, mGenderObserver);
                mViewModel.fetchDoctorDrills();
                break;


            case "TAG_COUNTRY":
                tvHeader.setText(getResources().getString(R.string.title_select_country));
                rvOptionz.setAdapter(mCountryAdapter);
                mViewModel.getCountries().observe(this, mCountryObserver);
                mViewModel.fetchDoctorDrills();
                break;


        }


    }

    private Observer<List<Language>> mLanguageObserver=new Observer<List<Language>>() {
        @Override
        public void onChanged(List<Language> languages) {
            if(!languages.isEmpty()) {
                mLanguageOneAdapter.addAll(languages);
                mLanguageTwoAdapter.addAll(languages);
            }
        }
    };
    private Observer<List<Speciliaty>> mSpecialityObserver=new Observer<List<Speciliaty>>() {
        @Override
        public void onChanged(List<Speciliaty> speciliaties) {
            if(!speciliaties.isEmpty()){
                mSpecialityAdapter.addAll(speciliaties);
            }

        }
    };

    private Observer<List<Country>> mCountryObserver=new Observer<List<Country>>() {
        @Override
        public void onChanged(List<Country> countries) {
            if(!countries.isEmpty()) {
                mCountryAdapter.addAll(countries);
            }

        }
    };
    private Observer<List<Country>> mNationalityObserver=new Observer<List<Country>>() {
        @Override
        public void onChanged(List<Country> countries) {
            if(!countries.isEmpty()) {
                mNationalityAdapter.addAll(countries);
            }

        }
    };




    private Observer<List<State>> mStateObserver=new Observer<List<State>>() {
        @Override
        public void onChanged(List<State> state) {
            if(!state.isEmpty()) {
                mStateAdapter.clear();
                mStateAdapter.addAll(state);
                tvAlertView.setVisibility(View.INVISIBLE);
                rvOptionz.setVisibility(View.VISIBLE);
            }else {
                tvAlertView.setVisibility(View.VISIBLE);
                tvAlertView.setText("No country selected yet");
                rvOptionz.setVisibility(View.INVISIBLE);
            }

        }
    };

    private Observer<List<Gender>> mGenderObserver =new Observer<List<Gender>>() {
        @Override
        public void onChanged(List<Gender> genders) {
            if(!genders.isEmpty()) mGenderAdapter.addAll(genders);

        }
    };



}
