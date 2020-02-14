package com.telemed.doctor.profile.view;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.filepicker.FilePickerActivity;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.profile.viewmodel.ChooseOptionViewModel;
import com.telemed.doctor.profile.viewmodel.ProfileDocumentViewModel;
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.model.DocumentInfo;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.signup.view.DocumentAdapter;
import com.telemed.doctor.signup.view.SignUpVFragment;
import com.telemed.doctor.util.CustomAlertTextView;
import com.telemed.doctor.util.DividerItemDecoration;
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

public class ProfileDocumentFragment extends Fragment {

    private static final int REQUEST_CODE_STORAGE = 210;
    private static final int REQUEST_CODE_DOCUMENT = 211;
    private static int fileIndex;
    private final String TAG = ProfileDocumentFragment.class.getSimpleName();
    private RecyclerView rvDocument;
    private RelativeLayout rlRoot;
    private ProgressBar progressBar;
    private String mAccessToken;
    private TextView tvCancel;
    private CustomAlertTextView tvAlertView;
    private Button btnContinue;
    private HashMap<String, String> mDocMap;

    //    private HashMap<String, String> mDocMap;
//    private HashMap<String, String> mSignUpMap;
    //@adapter
    private ProfileDocumentAdapter mProfileDocumentAdapter;
    private ProfileDocumentViewModel mViewModel;
    private HomeFragmentSelectedListener mFragmentListener;


    private View.OnClickListener mEventClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_continue:
//                    if (mProfileDocumentAdapter.getItemCount() == 0) {
//                        tvAlertView.showTopAlert(getResources().getString(R.string.alert_upload_file));
//                        return;
//                    }
                 //   mViewModel.attemptSignUp(mSignUpMap);
                    mFragmentListener.popTopMostFragment();

                    break;

                case R.id.tv_cancel:
//                    if (mFragmentListener != null)
//                        mFragmentListener.abortSignUpDialog();
                    break;

            }
        }
    };

    private ProfileDocumentAdapter.OnItemClickListener mItemClickListener = new ProfileDocumentAdapter.OnItemClickListener() {
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
        if (getArguments() != null) {
//            UserInfoWrapper objInfo = getArguments().getParcelable("KEY_ACCESS_TOKEN");
//            if (objInfo != null) mAccessToken = objInfo.getAccessToken();
//
//            Log.e(TAG, mAccessToken);
        }
        SharedPrefHelper mHelper = ((TeleMedApplication) requireActivity().getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
       if(mAccessToken!=null){
           mDocMap = new HashMap<>();
           mDocMap.put("Authorization", "Bearer " + mAccessToken);
       }

//
//        mSignUpMap = new HashMap<>();
//        mSignUpMap.put("content-type", "application/json"); //additional
//        mSignUpMap.put("Authorization", "Bearer " + mAccessToken);

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
        initListener();
        initRecyclerView(v);
        initObserver();


    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getViewEnabled()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultantFileUpload()
                .observe(getViewLifecycleOwner(), response -> {
                    switch (response.getStatus()) {
                        case SUCCESS:
                            if (response.getData() != null) {

                                tvAlertView.showTopAlert(response.getData().getMessage());
                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                int pos = response.getData().getData().getViewIndex();
                                mProfileDocumentAdapter.updateSingle(pos, response.getData().getData().getId());
                                mProfileDocumentAdapter.addView(new DocumentInfo()); // addView 1 tile manually

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
                .observe(getViewLifecycleOwner(), response -> {
                    switch (response.getStatus()) {
                        case SUCCESS:
                            if (response.getData() != null) {
                                tvAlertView.showTopAlert(response.getData().getMessage());
                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                int pos = response.getData().getData().getViewIndex();
                                mProfileDocumentAdapter.removeView(pos);


                            }

                            break;

                        case FAILURE:
                            if (response.getErrorMsg() != null) {
                                tvAlertView.showTopAlert(response.getErrorMsg());
                            }
                            break;

                    }
                });






    }

    private void resetEnableView(Boolean isView) {
        btnContinue.setEnabled(isView);
        if (isView)
            mProfileDocumentAdapter.setOnItemClickListener(mItemClickListener);
        else
            mProfileDocumentAdapter.setOnItemClickListener(null);


    }

    private void initRecyclerView(View v) {
        rvDocument = v.findViewById(R.id.rv_document);
        rvDocument.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDocument.setHasFixedSize(true);
        rvDocument.setAdapter(mProfileDocumentAdapter);

        mProfileDocumentAdapter.setOnItemClickListener(mItemClickListener);
        mProfileDocumentAdapter.addView(new DocumentInfo()); // addView 1 tile manually
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
            mProfileDocumentAdapter.update(fileIndex, new DocumentInfo(fileIndex, path, 1));
        }

    }

    private boolean isRuntimePermissionGranted() {
        int reqOne = ContextCompat.checkSelfPermission(requireActivity(), READ_EXTERNAL_STORAGE);
        int reqTwo = ContextCompat.checkSelfPermission(requireActivity(), WRITE_EXTERNAL_STORAGE);

        return (reqOne & reqTwo) == PackageManager.PERMISSION_GRANTED;
    }

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


                    }
                }


                break;
        }
    }

    @Override
    public void onDestroyView() {
        btnContinue.setOnClickListener(null);
        tvCancel.setOnClickListener(null);
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
