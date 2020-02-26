package com.telemed.doctor.consult.view;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.schedule.viewmodel.AppointmentHistoryViewModel;

public class AppointmentHistoryFragment extends Fragment {

    private AppointmentHistoryViewModel mViewModel;

    public static AppointmentHistoryFragment newInstance() {
        return new AppointmentHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout._fragment_appointment_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AppointmentHistoryViewModel.class);
        // TODO: Use the ViewModel
    }

}
