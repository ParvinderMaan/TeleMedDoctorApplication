package com.telemed.doctor.notification;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;


public class NotificationFragment extends Fragment {


    private RecyclerView rvNotification;
    private FloatingActionButton fbtnDeleteAllNotification;
    private HomeFragmentSelectedListener mFragmentListener;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_notification, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        initRecyclerView(v);

        ImageButton ibtnClose = v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {

            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        fbtnDeleteAllNotification = v.findViewById(R.id.fbtn_delete_all);
        fbtnDeleteAllNotification.setOnClickListener(v1 -> {

            fbtnDeleteAllNotification.hide();
        });


    }


    private void initRecyclerView(View v) {
        rvNotification = v.findViewById(R.id.rv_notification);
        rvNotification.setHasFixedSize(true);
        rvNotification.setLayoutManager(new LinearLayoutManager(requireActivity()));

        NotificationAdapter mAdapter = new NotificationAdapter(requireActivity());
        rvNotification.setAdapter(mAdapter);
        //   mAdapter.setOnClickListener(() -> { });

//        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
//        rvNotification.addItemDecoration(dividerItemDecoration);
    }


}
