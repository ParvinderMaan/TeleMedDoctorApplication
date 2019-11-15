package com.telemed.doctor.notification;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.util.DividerItemDecoration;


public class NotificationFragment extends Fragment {


    private RecyclerView rvNotification;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        initRecyclerView(v);

    }






    void initRecyclerView(View v) {
        rvNotification=v.findViewById(R.id.rv_notification);
        rvNotification.setHasFixedSize(true);
        rvNotification.setLayoutManager(new LinearLayoutManager(getActivity()));

        NotificationAdapter mAdapter=new NotificationAdapter(getActivity());
        rvNotification.setAdapter(mAdapter);
     //   mAdapter.setOnClickListener(() -> { });

//        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
//        rvNotification.addItemDecoration(dividerItemDecoration);
    }


}
