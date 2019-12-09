package com.telemed.doctor.profile.view;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.base.OnItemClickListener;
import com.telemed.doctor.profile.model.Gender;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.profile.model.Speciliaty;
import com.telemed.doctor.profile.viewmodel.ChooseOptionViewModel;
import com.telemed.doctor.signup.view.SignUpIIFragment;
import com.telemed.doctor.util.CustomAlertTextView;
import com.telemed.doctor.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class ChooseOptionFragment extends BaseFragment {
    private RecyclerView rvOptionz;
    private ImageButton ibtnClose;
    private TextView tvHeader;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private String mType;
    private LanguageOptionAdapter mLanguageOneAdapter;
    private LanguageOptionAdapter mLanguageTwoAdapter;

    private GenderOptionAdapter mGenderAdapter;
    private SpecialityOptionAdapter mSpecialityAdapter;

    private ChooseOptionViewModel mViewModel;
    private ChooseOptionFragmentSelectedListener mFragmentListener;



    public static ChooseOptionFragment newInstance(String tag) {
        ChooseOptionFragment fragment=new ChooseOptionFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY_",tag);
        fragment.setArguments(bundle);
       return fragment;
    }

    public void setOnChooseOptionSelectedListener(ChooseOptionFragmentSelectedListener listener ) {
        mFragmentListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLanguageOneAdapter=new LanguageOptionAdapter();
        mLanguageTwoAdapter=new LanguageOptionAdapter();

        mSpecialityAdapter=new SpecialityOptionAdapter();
        mGenderAdapter=new GenderOptionAdapter();
        if(getArguments()!=null){
             mType=getArguments().getString("KEY_");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(getParentFragment()).get(ChooseOptionViewModel.class);
        return inflater.inflate(R.layout.fragment_choose_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initRecyclerView(v);
       // setHeader
        initValue();



        if(!isNetAvail()){
            tvAlertView.showTopAlert("No Internet");
            return ;
        }

        mViewModel.fetchDrills();


        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));





    }

    private void initValue() {
        switch (mType){
            case "TAG_NATIONALITY":
                tvHeader.setText(getResources().getString(R.string.title_select_country));
//                mAdapter.setData(getDummyNationalityData());
                break;


            case  "TAG_BIRTH_COUNTRY":
                tvHeader.setText(getResources().getString(R.string.title_select_country));
//                mAdapter.setData(getDummyNationalityData());
                break;


            case  "TAG_BIRTH_CITY":
                tvHeader.setText(getResources().getString(R.string.title_select_city));
//                mAdapter.setData(getDummyNationalityData());
                break;

            case "TAG_SPECIALITY":
                tvHeader.setText(getResources().getString(R.string.title_select_speciality));
                rvOptionz.setAdapter(mSpecialityAdapter);
                mViewModel.getSpeciality().observe(this,mSpecialityObserver);
                break;

            case "TAG_LANGUAGE_ONE":
                tvHeader.setText(getResources().getString(R.string.title_select_language));
                mViewModel.getLanguage().observe(this, mLanguageObserver);
                rvOptionz.setAdapter(mLanguageOneAdapter);
                break;

            case "TAG_LANGUAGE_TWO":
                tvHeader.setText(getResources().getString(R.string.title_select_language));
                rvOptionz.setAdapter(mLanguageTwoAdapter);
                mViewModel.getLanguage().observe(this, mLanguageObserver);
                break;

            case "TAG_GENDER":
                tvHeader.setText(getResources().getString(R.string.title_select_gender));
                rvOptionz.setAdapter(mGenderAdapter);
                mViewModel.getGender().observe(this, mGenderObserver);
                break;

        }


    }

    private void initView(View v) {
        tvHeader=v.findViewById(R.id.tv_header);
        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(mFragmentListener!=null){
//                    mFragmentListener.dismissFragment();
//                }
                if (getParentFragment() != null) {
                    ((SignUpIIFragment)getParentFragment()).popChooseOptionFragment();
                }
            }
        });
        progressBar=v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView = v.findViewById(R.id.tv_alert_view);
        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tvAlertView.setTextColor(getResources().getColor(R.color.colorRed));
    }

    private void initRecyclerView(View v) {
        rvOptionz =v.findViewById(R.id.rv_optionz);
        rvOptionz.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvOptionz.setLayoutManager(mLinearLayoutManager);


        mLanguageOneAdapter.setListener(new OnItemClickListener<Language>() {
            @Override
            public void onItemClicked(Language item) {
                         if(mFragmentListener!=null)
                             mFragmentListener.onItemSelected(item,"TAG_LANGUAGE_ONE");
            }
        });

        mGenderAdapter.setListener(new OnItemClickListener<Gender>() {
            @Override
            public void onItemClicked(Gender item) {
                if(mFragmentListener!=null)
                    mFragmentListener.onItemSelected(item,"TAG_GENDER");
            }
        });

        mSpecialityAdapter.setListener(new OnItemClickListener<Speciliaty>() {
            @Override
            public void onItemClicked(Speciliaty item) {
                if(mFragmentListener!=null)
                    mFragmentListener.onItemSelected(item,"TAG_SPECIALITY");
            }
        });


        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvOptionz.addItemDecoration(dividerItemDecoration);
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
    private Observer<List<Gender>> mGenderObserver =new Observer<List<Gender>>() {
        @Override
        public void onChanged(List<Gender> genders) {
            if(!genders.isEmpty()) mGenderAdapter.addAll(genders);

        }
    };

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
           if(!speciliaties.isEmpty()) mSpecialityAdapter.addAll(speciliaties);

        }
    };

    public interface ChooseOptionFragmentSelectedListener {

        void onItemSelected(Object data, String type);

    }

    /*










     */
}
