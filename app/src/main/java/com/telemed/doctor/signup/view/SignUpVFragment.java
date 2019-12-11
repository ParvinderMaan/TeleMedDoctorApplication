package com.telemed.doctor.signup.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.filepicker.FilePickerActivity;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.signup.viewmodel.SignUpVViewModel;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;


import java.util.ArrayList;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpVFragment extends BaseFragment {
    private final String TAG=SignUpVFragment.class.getSimpleName();
    private static final int REQUEST_CODE_STORAGE = 210;
    private static final int REQUEST_CODE_DOCUMENT = 211;
    private static int docIndex;

    private TextView tvDocOne, tvDocTwo, tvDocThree, tvDocFour, tvDocFive;
    private ImageButton ibtnUploadOne, ibtnUploadTwo, ibtnUploadThree, ibtnUploadFour, ibtnUploadFive;
    private RelativeLayout rlDocOne, rlDocTwo, rlDocThree, rlDocFour, rlDocFive;
    private RecyclerView rvDocument;
    private RouterFragmentSelectedListener mFragmentListener;
    private LinearLayout llRoot;
    private ProgressBar pbBar;
    private SignUpVViewModel mViewModel;
    private String mAccessToken;
    private TextView tvCancel;


    public SignUpVFragment() {
        // Required empty public constructor
    }

    public static SignUpVFragment newInstance(Object payload) {
        SignUpVFragment fragment=new SignUpVFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY_ACCESS_TOKEN", ( String ) payload);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // collect our intent
        if(getArguments()!=null){
            mAccessToken = getArguments().getString("KEY_ACCESS_TOKEN");
            Log.e(TAG,mAccessToken);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SignUpVViewModel.class);
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

        initView(v);
//
        initListener();


        v.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getActivity(), "please wait till admin approval", Toast.LENGTH_SHORT).show();
//                if (mFragmentListener != null)
//                    mFragmentListener.popTillFragment("SignInFragment", 0);
            }
        });

    }

    private void initListener() {

        tvDocOne.setOnClickListener(v -> {

            if (!isRuntimePermissionGranted()) {
                requestRuntimePermission();
                return;
            }

            openDocument(1);


        });
        tvDocTwo.setOnClickListener(v -> openDocument(2));
        tvDocThree.setOnClickListener(v -> openDocument(3));
        tvDocFour.setOnClickListener(v -> openDocument(4));
        tvDocFive.setOnClickListener(v -> openDocument(5));


        ibtnUploadOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbBar.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(1, 3000);

            }
        });

        ibtnUploadTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbBar.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(2, 3000);

            }
        });

        ibtnUploadThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbBar.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(3, 3000);

            }
        });

        ibtnUploadFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbBar.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(4, 3000);

            }
        });


        ibtnUploadFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbBar.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(5, 3000);

            }
        });



        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFragmentListener != null)
                    mFragmentListener.abortSignUpDialog();

            }
        });
    }

    private void initView(View v) {
        llRoot = v.findViewById(R.id.ll_root);
        tvCancel = v.findViewById(R.id.tv_cancel);

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

        pbBar = v.findViewById(R.id.pb_bar);
        pbBar.setVisibility(View.INVISIBLE);
        pbBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
//        pbBarTwo = v.findViewById(R.id.pb_bar_two);
//        pbBarThree = v.findViewById(R.id.pb_bar_three);
//        pbBarFour = v.findViewById(R.id.pb_bar_four);
//        pbBarFive = v.findViewById(R.id.pb_bar_five);

        ibtnUploadOne = v.findViewById(R.id.ibtn_upload_one);
        ibtnUploadTwo = v.findViewById(R.id.ibtn_upload_two);
        ibtnUploadThree = v.findViewById(R.id.ibtn_upload_three);
        ibtnUploadFour = v.findViewById(R.id.ibtn_upload_four);
        ibtnUploadFive = v.findViewById(R.id.ibtn_upload_five);


        rlDocTwo.setVisibility(View.INVISIBLE);
        rlDocThree.setVisibility(View.INVISIBLE);
        rlDocFour.setVisibility(View.INVISIBLE);
        rlDocFive.setVisibility(View.INVISIBLE);


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
    public void openDocument(int index) {
        docIndex=index;
        Intent in = new Intent(getActivity(), FilePickerActivity.class);
        in.putExtra(Constant.MAX_NUMBER, 1);
        in.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "pdf"});
        startActivityForResult(in, REQUEST_CODE_DOCUMENT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_DOCUMENT && data != null) {
            ArrayList<NormalFile> file= data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            switch (docIndex) {
                case 1:
                    tvDocOne.setText("" +file.get(0).getPath());
                    break;

                case 2:
                    tvDocTwo.setText("" +file.get(0).getPath());
                    break;

                case 3:
                    tvDocThree.setText("" +file.get(0).getPath());
                    break;

                case 4:
                    tvDocFour.setText("" +file.get(0).getPath());
                    break;

                case 5:
                    tvDocFive.setText("" +file.get(0).getPath());
                    break;

                default:
                    makeToast("something went wrong");

            }

        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    pbBar.setVisibility(View.INVISIBLE);
                    ibtnUploadOne.setImageResource(R.drawable.ic_success);
                    rlDocTwo.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    pbBar.setVisibility(View.INVISIBLE);
                    ibtnUploadTwo.setImageResource(R.drawable.ic_success);
                    rlDocThree.setVisibility(View.VISIBLE);
                    break;

                case 3:
                    pbBar.setVisibility(View.INVISIBLE);
                    ibtnUploadThree.setImageResource(R.drawable.ic_success);
                    rlDocFour.setVisibility(View.VISIBLE);
                    break;

                case 4:
                    pbBar.setVisibility(View.INVISIBLE);
                    ibtnUploadFour.setImageResource(R.drawable.ic_success);
                    rlDocFive.setVisibility(View.VISIBLE);
                    break;

                case 5:
                    pbBar.setVisibility(View.INVISIBLE);
                    ibtnUploadFive.setImageResource(R.drawable.ic_success);
                    break;
            }


//---------------------------------------------------------------

//---------------------------------------------------------------

        }
    };


    private boolean isRuntimePermissionGranted() {
        int reqOne = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        int reqTwo = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);

        return (reqOne & reqTwo) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestRuntimePermission() {
        requestPermissions(new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_STORAGE:
                if (grantResults.length > 0) {

                    boolean permOneAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permTwoAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    if (permOneAccepted && permTwoAccepted)
//                        Snackbar.make(rlRootView, "Permission Granted, Now you can access device data", Snackbar.LENGTH_LONG).show();
                        openDocument(1);
                    else {

                        Snackbar.make(llRoot, "Permission Denied, You cannot access device data", Snackbar.LENGTH_LONG).show();

//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
//                                                            PERMISSION_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }

                    }
                }


                break;
        }
    }
}

/*
content://com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3Dencoded%3DV1SSAZlixDW44x4WEOolAzFUXoGJBhg5Up0I8Uyn6gKP5n9C%2BWCyrQs%3D

content://com.android.providers.downloads.documents/document/1961
 */