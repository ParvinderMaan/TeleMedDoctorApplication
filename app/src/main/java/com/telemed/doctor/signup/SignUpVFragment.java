package com.telemed.doctor.signup;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpVFragment extends Fragment {
    private int REQUEST_CODE_DOC = 121;


    private TextView tvDocOne, tvDocTwo, tvDocThree, tvDocFour, tvDocFive;
    private ProgressBar pbBarOne, pbBarTwo, pbBarThree, pbBarFour, pbBarFive;
    private ImageButton ibtnUploadOne, ibtnUploadTwo, ibtnUploadThree, ibtnUploadFour, ibtnUploadFive;
    private RelativeLayout rlDocOne, rlDocTwo, rlDocThree, rlDocFour, rlDocFive;
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
//        mDocumentAdapter=new DocumentAdapter();
//        mDocumentAdapter.setOnItemClickListener(mDocumentItemClickListener);
//        initRecyclerView(v);

        /*
         if(getActivity()!=null)
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    ((RouterActivity)getActivity()).finish();
         */

//        initView(v);
//
//        initListener();


        v.findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "please til admin approval", Toast.LENGTH_SHORT).show();
                if(getActivity()!=null)
                    ((RouterActivity)getActivity()).popTillFragment("SignInFragment",0);

            }
        });

    }

    private void initListener() {

        tvDocOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDocument(1);


            }
        });

        ibtnUploadOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbBarOne.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(1,3000);

            }
        });
    }

    private void initView(View v) {
        rlDocOne = v.findViewById(R.id.rl_doc_one);
        rlDocTwo = v.findViewById(R.id.rl_doc_two);
        rlDocThree = v.findViewById(R.id.rl_doc_three);
        rlDocFour = v.findViewById(R.id.rl_doc_four);
        rlDocFive = v.findViewById(R.id.rl_doc_five);


        tvDocOne = v.findViewById(R.id.tv_doc_one);
        tvDocTwo = v.findViewById(R.id.tv_doc_two);
        tvDocThree = v.findViewById(R.id.tv_doc_three);
        tvDocFour = v.findViewById(R.id.tv_doc_four);
        tvDocFive = v.findViewById(R.id.tv_doc_five);

        pbBarOne = v.findViewById(R.id.pb_bar_one);
        pbBarTwo = v.findViewById(R.id.pb_bar_two);
        pbBarThree = v.findViewById(R.id.pb_bar_three);
        pbBarFour = v.findViewById(R.id.pb_bar_four);
        pbBarFive = v.findViewById(R.id.pb_bar_five);

        ibtnUploadOne = v.findViewById(R.id.ibtn_upload_one);
        ibtnUploadTwo = v.findViewById(R.id.ibtn_upload_two);
        ibtnUploadThree = v.findViewById(R.id.ibtn_upload_three);
        ibtnUploadFour = v.findViewById(R.id.ibtn_upload_four);
        ibtnUploadFive = v.findViewById(R.id.ibtn_upload_five);


        rlDocTwo.setVisibility(View.GONE);
        rlDocThree.setVisibility(View.GONE);
        rlDocFour.setVisibility(View.GONE);
        rlDocFive.setVisibility(View.GONE);


    }

//    private void initRecyclerView(View v) {
//
//        rvDocument = v.findViewById(R.id.rv_document);
//        LinearLayoutManager layoutIManager = new LinearLayoutManager(getActivity());
//        rvDocument.setNestedScrollingEnabled(false);
//        rvDocument.setLayoutManager(layoutIManager);
//        rvDocument.setAdapter(mDocumentAdapter);
//
//        mDocumentAdapter.addItemView();
//    }


//    private DocumentAdapter.OnItemClickListener mDocumentItemClickListener=new DocumentAdapter.OnItemClickListener() {
//        @Override
//        public void onItemAddClick() {
//
//            mDocumentAdapter.add(new DocumentInfo());
//
//        }
//
//        @Override
//        public void onItemImageClick(int position, DocumentInfo result) {
//
//
//
//        }
//
//        @Override
//        public void onItemDeleteClick(int adapterPosition, DocumentInfo result) {
//
//
//
//
//        }
//    };

    public void openDocument(int i) {
        Bundle b = new Bundle();
        b.putInt("KEY_DOC", 1);

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimetypes = {"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/pdf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, REQUEST_CODE_DOC, b);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Check which request it is that we're responding to
        //Detects request codes
        if (requestCode == REQUEST_CODE_DOC && resultCode == Activity.RESULT_OK) {
            Uri selectedFile = data.getData();
            int x = data.getIntExtra("KEY_DOC", 0);
            if (x == 1) {
                tvDocOne.setText("" + selectedFile);
                ibtnUploadOne.setVisibility(View.VISIBLE);

            } else if (x == 2) {
                tvDocTwo.setText("" + selectedFile);
            } else if (x == 3) {
                tvDocThree.setText("" + selectedFile);
            } else if (x == 4) {
                tvDocFour.setText("" + selectedFile);
            } else if (x == 5) {
                tvDocFive.setText("" + selectedFile);
            }

        }
    }
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            pbBarOne.setVisibility(View.INVISIBLE);
//---------------------------------------------------------------

//---------------------------------------------------------------
            ibtnUploadOne.setImageResource(R.drawable.ic_success);
            rlDocTwo.setVisibility(View.VISIBLE);
        }
    };

}
