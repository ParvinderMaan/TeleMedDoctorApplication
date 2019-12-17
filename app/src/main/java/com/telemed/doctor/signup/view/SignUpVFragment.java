package com.telemed.doctor.signup.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.filepicker.FilePickerActivity;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.model.DocumentInfo;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.signup.viewmodel.SignUpVViewModel;
import com.telemed.doctor.util.CustomAlertTextView;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class SignUpVFragment extends BaseFragment {
    private static final int REQUEST_CODE_STORAGE = 210;
    private static final int REQUEST_CODE_DOCUMENT = 211;
    private static int fileIndex;
    private final String TAG = SignUpVFragment.class.getSimpleName();
    private RecyclerView rvDocument;
    private RouterFragmentSelectedListener mFragmentListener;
    private RelativeLayout rlRoot;
    private ProgressBar progressBar;
    private SignUpVViewModel mViewModel;
    private String mAccessToken;
    private TextView tvCancel;
    private CustomAlertTextView tvAlertView;
    private Button btnContinue;
    private HashMap<String, String> mDocMap;
    private HashMap<String, String> mSignUpMap;

    private DocumentAdapter mAdapter;
    private View.OnClickListener mEventClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_continue:
                    if (mAdapter.getItemCount() == 0) {
                        tvAlertView.showTopAlert(getResources().getString(R.string.alert_upload_file));
                        return;
                    }
                    mViewModel.attemptSignUp(mSignUpMap);

                    break;

                case R.id.tv_cancel:
                    if (mFragmentListener != null)
                        mFragmentListener.abortSignUpDialog();
                    break;


            }


        }
    };
    private DocumentAdapter.OnItemClickListener mItemClickListener = new DocumentAdapter.OnItemClickListener() {
        @Override
        public void onItemTextClick(int pos, DocumentInfo info) {
            if (!isRuntimePermissionGranted()) {
                requestRuntimePermission();
                return;
            }
            openDocument(pos);

        }

        @Override
        public void onItemActionClick(int pos, DocumentInfo info, String type) {

            if (type.equals("UPLOAD")) {

                MultipartBody.Part filePath;
                String path = (String) info.getPath();
                filePath = prepareImgFilePart(path);
                mViewModel.attemptFileUpload(mDocMap, filePath, pos);
            }


            if (type.equals("DELETE")) {
                int id = info.getId();
                mViewModel.attemptDeleteFile(mDocMap, id, pos);
            }
        }
    };

    public SignUpVFragment() {
        // Required empty public constructor
    }

    public static SignUpVFragment newInstance(Object payload) {
        SignUpVFragment fragment = new SignUpVFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_ACCESS_TOKEN", (UserInfoWrapper) payload);
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
        if (getArguments() != null) {
            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_ACCESS_TOKEN");
            if (objInfo != null) mAccessToken = objInfo.getAccessToken();

            Log.e(TAG, mAccessToken);
        }

        mDocMap = new HashMap<>();
        mDocMap.put("Authorization", "Bearer " + mAccessToken);

        mSignUpMap = new HashMap<>();
        mSignUpMap.put("content-type", "application/json"); //additional
        mSignUpMap.put("Authorization", "Bearer " + mAccessToken);

        mAdapter = new DocumentAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SignUpVViewModel.class);
        return inflater.inflate(R.layout.fragment_sign_up_v, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initRecyclerView(v);


        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getViewEnabled()
                .observe(this, this::resetEnableView);

        mViewModel.getResultantFileUpload()
                .observe(this, response -> {
                    switch (response.getStatus()) {
                        case SUCCESS:
                            if (response.getData() != null) {

                                tvAlertView.showTopAlert(response.getData().getMessage());
                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                int pos = response.getData().getData().getViewIndex();
                                mAdapter.updateSingle(pos, response.getData().getData().getId());
                                mAdapter.addView(new DocumentInfo()); // addView 1 tile manually

                            }

                            break;

                        case FAILURE:
                            if (response.getErrorMsg() != null) {
                                tvAlertView.showTopAlert(response.getErrorMsg());
                            }
                            break;

                    }
                });


        mViewModel.getResultantFileDelete()
                .observe(this, response -> {
                    switch (response.getStatus()) {
                        case SUCCESS:
                            if (response.getData() != null) {
                                tvAlertView.showTopAlert(response.getData().getMessage());
                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                int pos = response.getData().getData().getViewIndex();
                                mAdapter.removeView(pos);


                            }

                            break;

                        case FAILURE:
                            if (response.getErrorMsg() != null) {
                                tvAlertView.showTopAlert(response.getErrorMsg());
                            }
                            break;

                    }
                });

        mViewModel.getResultantSignUp().observe(this, response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
//                         SignUpVResponse.Data data = response.getData().getData(); // adding Additional Info
                        // tvAlertView.showTopAlert(response.getData().getMessage());
                        // tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        String msg = response.getData().getMessage();
                        if (mFragmentListener != null)
                            mFragmentListener.showSignUpSuccessDialog(msg);

                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;

            }


        });

        mViewModel.getResultantAllDocument().observe(this, response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        AllDocumentResponse.Data data = response.getData().getData(); // adding Additional Info
                        if(!data.getDocumentList().isEmpty()){
                            mAdapter.addAll(data.getDocumentList());
                            mAdapter.addView(new DocumentInfo()); // addView 1 tile manually
                        }else {
                            mAdapter.addView(new DocumentInfo()); // addView 1 tile manually
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        mAdapter.addView(new DocumentInfo()); // addView 1 tile manually  /// remove it in !!!
                    }
                    break;

            }


        });

        mViewModel.fetchAllDocuments(mSignUpMap);


    }

    private void resetEnableView(Boolean isView) {
        btnContinue.setEnabled(isView);
        if (isView)
            mAdapter.setOnItemClickListener(mItemClickListener);
        else
            mAdapter.setOnItemClickListener(null);


    }

    private void initRecyclerView(View v) {
        rvDocument = v.findViewById(R.id.rv_document);
        rvDocument.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDocument.setHasFixedSize(true);
        rvDocument.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(mItemClickListener);
    }

    private void initListener() {
        btnContinue.setOnClickListener(mEventClickListener);
        tvCancel.setOnClickListener(mEventClickListener);
    }

    private void initView(View v) {
        rlRoot = v.findViewById(R.id.rl_root);
        btnContinue = v.findViewById(R.id.btn_continue);
        tvCancel = v.findViewById(R.id.tv_cancel);


        progressBar = v.findViewById(R.id.pb_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView = v.findViewById(R.id.tv_alert_view);

    }

    public void openDocument(int index) {
        fileIndex = index;
        Intent in = new Intent(getActivity(), FilePickerActivity.class);
        in.putExtra(Constant.MAX_NUMBER, 1);
        in.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "pdf"});
        startActivityForResult(in, REQUEST_CODE_DOCUMENT);
    }
    /*

                    pbBar.setVisibility(View.INVISIBLE);
                    ibtnUploadOne.setImageResource(R.drawable.ic_success);
                    rlDocTwo.setVisibility(View.VISIBLE);
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_DOCUMENT && data != null) {
            ArrayList<NormalFile> file = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            setFileInView(file);
        }
    }

    private void setFileInView(ArrayList<NormalFile> file) {
        String path = file.get(0).getPath();
        Log.e(TAG, path);

        if (path != null) {
            mAdapter.update(fileIndex, new DocumentInfo(fileIndex, path, 1));
        }

    }

    private boolean isRuntimePermissionGranted() {
        int reqOne = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        int reqTwo = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);

        return (reqOne & reqTwo) == PackageManager.PERMISSION_GRANTED;
    }
    /*
     String path;
        MultipartBody.Part filePath;
         path= (String) tvDocOne.getTag();
                filePath=prepareImgFilePart(path);
                mViewModel.attemptFileUpload(mMap,filePath,1);
     */

/*
 if (!isRuntimePermissionGranted()) {
                    requestRuntimePermission();
                    return;
                }
                openDocument(1);
 */

    private void requestRuntimePermission() {
        requestPermissions(new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_STORAGE:
                if (grantResults.length > 0) {

                    boolean permOneAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permTwoAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;


                    if (permOneAccepted && permTwoAccepted)
                        openDocument(0);
                    else {
                        tvAlertView.showTopAlert(getResources().getString(R.string.alert_access_denied));

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

    @Override
    public void onDestroyView() {
        mEventClickListener = null;
        super.onDestroyView();
    }

    @NonNull
    private MultipartBody.Part prepareImgFilePart(String filee) {
        File file = new File(filee);
        // create RequestBody instance from videoFile
        RequestBody requestFile = RequestBody.create(file, okhttp3.MediaType.parse("image/*"));  // new
        // MultipartBody.Part is used to send also the actual docFile name
        return MultipartBody.Part.createFormData("docFile", file.getName(), requestFile);
    }
}

/*
content://com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3Dencoded%3DV1SSAZlixDW44x4WEOolAzFUXoGJBhg5Up0I8Uyn6gKP5n9C%2BWCyrQs%3D

content://com.android.providers.downloads.documents/document/1961
 */


/*

 {"status":true,"message":"Doctor Document List Succeeded","data":{"documentList":[{"id":71,"documentName":"12017020190121391160PM596Sampark0Chandigarh.pdf","userId":"14e9f5b3-c331-4b58-94d8-d99923377333"},{"id":73,"documentName":"12017020190121391460PM623CTET_AdmitCard.pdf","userId":"14e9f5b3-c331-4b58-94d8-d99923377333"}]}}
 */