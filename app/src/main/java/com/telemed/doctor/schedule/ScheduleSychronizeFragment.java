package com.telemed.doctor.schedule;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.util.NonSwipeViewPager;


public class ScheduleSychronizeFragment extends Fragment {
    private WeeklyScheduleAdapter mAdapter;
    private Button btnSynchronizeSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;


    public static ScheduleSychronizeFragment newInstance() {
        return new ScheduleSychronizeFragment() ;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();

    }

    private void initView(View v) {
        btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);
        //@ hide for this screen
        btnSynchronizeSchedule.setVisibility(View.GONE);
        ibtnClose = v.findViewById(R.id.ibtn_close);


    }

    private void initListener() {
        ibtnClose.setOnClickListener(v13 -> {
            if(mFragmentListener!=null){
                mFragmentListener.popTopMostFragment();
            }

        });


    }


}
