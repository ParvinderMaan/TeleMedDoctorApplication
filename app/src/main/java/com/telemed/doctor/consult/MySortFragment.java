package com.telemed.doctor.consult;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.util.DividerItemDecoration;


public class MySortFragment extends Fragment {


    private RecyclerView rvMyConsult;
    private SortAdapter mAdapter;

    public MySortFragment() {
        // Required empty public constructor
    }

    public static MySortFragment newInstance() {
        return new MySortFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_sort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initRecyclerView(v);


        v.findViewById(R.id.ibtn_close).setOnClickListener(v1 -> {
            if(getActivity()!=null)
                ((HomeActivity)getActivity()).popTopMostFragment();
        });




        v.findViewById(R.id.btn_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    private void initRecyclerView(View v) {
        rvMyConsult =v.findViewById(R.id.rv_my_consult);
        rvMyConsult.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvMyConsult.setLayoutManager(mLinearLayoutManager);

         mAdapter=new SortAdapter();
        rvMyConsult.setAdapter(mAdapter);



        if(getActivity()!=null) {
            Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider_blue);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
            rvMyConsult.addItemDecoration(dividerItemDecoration);
        }

    }
}
