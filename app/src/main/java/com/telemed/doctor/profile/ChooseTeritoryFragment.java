package com.telemed.doctor.profile;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseTeritoryFragment extends Fragment {
    public static String  COUNTRY="TYPE_COUNTRY";
    public static String  CITY="TYPE_CITY";
    private RecyclerView rvTeritory;
    private RouterFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private TextView tvHeader;
    private String mType;
    private TeritoryAdapter mAdapter;


    public static ChooseTeritoryFragment newInstance(String tag) {
        ChooseTeritoryFragment chooseTeritoryFragment=new ChooseTeritoryFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY_",tag);
        chooseTeritoryFragment.setArguments(bundle);
       return chooseTeritoryFragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
             mType=getArguments().getString("KEY_");

        }
        mAdapter=new TeritoryAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_teritory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        tvHeader=v.findViewById(R.id.tv_header);
        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFragmentListener!=null){
                    mFragmentListener.popTopMostFragment();
                }
            }
        });
        initRecyclerView(v);


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
    private void initRecyclerView(View v) {
        rvTeritory=v.findViewById(R.id.rv_territory);
        rvTeritory.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
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

        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider);
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
    /*










     */
}
