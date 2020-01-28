package com.telemed.doctor.profile.view;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.profile.viewmodel.ChooseOptionViewModel;
import com.telemed.doctor.profile.viewmodel.ProfileDocumentViewModel;
import com.telemed.doctor.signup.model.DocumentInfo;
import com.telemed.doctor.util.DividerItemDecoration;

import java.util.ArrayList;

public class ProfileDocumentFragment extends Fragment {
    private RecyclerView rvOptionz;
    private ImageButton ibtnClose;
    private TextView tvHeader;
    private TextView tvAlertView;
    private ProgressBar progressBar;
    private String mType;
    //@adapter
    private ProfileDocumentAdapter mProfileDocumentAdapter;
    private ProfileDocumentViewModel mViewModel;
    private RecyclerView rvDocument;
    private HomeFragmentSelectedListener mFragmentListener;

    public static ProfileDocumentFragment newInstance() {
        return new ProfileDocumentFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileDocumentAdapter=new ProfileDocumentAdapter();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_document_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileDocumentViewModel.class);
        initView(v);
        initRecyclerView(v);
    }

    private void initView(View v) {
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        progressBar = v.findViewById(R.id.progress_bar);
        tvHeader = v.findViewById(R.id.tv_header);
        ibtnClose = v.findViewById(R.id.ibtn_close);

        // @initialization
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView.setOnClickListener(vv->{

            // !!!!!!!


        });

        ibtnClose.setOnClickListener(vv->{
             if(mFragmentListener!=null){
                 mFragmentListener.popTopMostFragment();
             }
        });

    }

    private void initRecyclerView(View v) {
        rvDocument = v.findViewById(R.id.rv_document);
        rvDocument.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(requireActivity());
        rvOptionz.setLayoutManager(mLinearLayoutManager);
        rvDocument.setAdapter(mProfileDocumentAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.custom_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvOptionz.addItemDecoration(dividerItemDecoration);
    }



}
