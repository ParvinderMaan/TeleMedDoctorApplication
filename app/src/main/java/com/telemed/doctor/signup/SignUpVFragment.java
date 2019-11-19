package com.telemed.doctor.signup;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.telemed.doctor.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpVFragment extends Fragment {


    private DocumentAdapter mDocumentAdapter;
    private RecyclerView rvDocument;


    public SignUpVFragment() {
        // Required empty public constructor
    }

    public static SignUpVFragment newInstance() {
        return new SignUpVFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_v, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mDocumentAdapter=new DocumentAdapter();
        mDocumentAdapter.setOnItemClickListener(mDocumentItemClickListener);
        initRecyclerView(v);

        /*
         if(getActivity()!=null)
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    ((RouterActivity)getActivity()).finish();
         */

    }

    private void initRecyclerView(View v) {

        rvDocument = v.findViewById(R.id.rv_document);
        LinearLayoutManager layoutIManager = new LinearLayoutManager(getActivity());
        rvDocument.setNestedScrollingEnabled(false);
        rvDocument.setLayoutManager(layoutIManager);
        rvDocument.setAdapter(mDocumentAdapter);

        mDocumentAdapter.addItemView();
    }


    private DocumentAdapter.OnItemClickListener mDocumentItemClickListener=new DocumentAdapter.OnItemClickListener() {
        @Override
        public void onItemAddClick() {

            mDocumentAdapter.add(new DocumentInfo());

        }

        @Override
        public void onItemImageClick(int position, DocumentInfo result) {



        }

        @Override
        public void onItemDeleteClick(int adapterPosition, DocumentInfo result) {




        }
    };
}
