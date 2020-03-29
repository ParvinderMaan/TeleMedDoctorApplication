package com.telemed.doctor.profile.view;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.AlertDialogFragment;
import com.telemed.doctor.signup.model.AllDocumentResponse;
import com.telemed.doctor.signup.view.DocumentAdapter;
import com.telemed.doctor.util.FilePickerActivity;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.profile.viewmodel.ProfileDocumentViewModel;
import com.telemed.doctor.signup.model.DocumentInfo;
import com.telemed.doctor.util.CustomAlertTextView;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileDocumentFragment extends Fragment {
    private final String TAG = ProfileDocumentFragment.class.getSimpleName();
    private static final int REQUEST_CODE_STATE_LICENCE = 211;
    private static final int REQUEST_CODE_GOVT_ISSUE_ID = 213;
    private static int fileIndex;
    private RecyclerView rvDocument;
    private HomeFragmentSelectedListener mFragmentListener;
    private ProgressBar progressBar;
    private ProfileDocumentViewModel mViewModel;
    private String mAccessToken;
    private CustomAlertTextView tvAlertView;
    private Button btnBack;
    private HashMap<String, String> mDocMap;
    private AppCompatTextView tvTitleGovtIssueId;
    private ImageButton ibtnActionGovtIssueId;
    private DocumentAdapter mAdapter;
    private String pathOfFile;
    private HashMap<String, String> mSignUpMap;


    public ProfileDocumentFragment() {
        // Required empty public constructor
    }

    public static ProfileDocumentFragment newInstance() {
        ProfileDocumentFragment fragment = new ProfileDocumentFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDocMap = new HashMap<>();
        mDocMap.put("Authorization", "Bearer " + mAccessToken);
        mAdapter=new DocumentAdapter();
        mSignUpMap = new HashMap<>();
        mSignUpMap.put("content-type", "application/json"); // additional
        mSignUpMap.put("Authorization", "Bearer " + mAccessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_document, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ProfileDocumentViewModel.class);
        super.onViewCreated(v, savedInstanceState);

        initView(v);
        initListener();
        initRecyclerView(v);
        initObserver();

        mViewModel.fetchAllDocuments(mSignUpMap);


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
                .observe(getViewLifecycleOwner(), response -> {
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



        mViewModel.getResultantAllDocument().observe(getViewLifecycleOwner(), response -> {
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

                        if(data.getOtherDocList()!=null){
                            setDocumentInView(data.getOtherDocList());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        //  mAdapter.addView(new DocumentInfo()); // addView 1 tile manually  /// remove it in !!!
                    }
                    break;
            }

        });






        mViewModel.getResultantGovtIdFileUpload()
                .observe(getViewLifecycleOwner(), response -> {
                    switch (response.getStatus()) {
                        case SUCCESS:
                            if (response.getData() != null) {
                                tvAlertView.showTopAlert(response.getData().getMessage());
                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                // process....
                                tvTitleGovtIssueId.setText("Government Id uploaded");
                                ibtnActionGovtIssueId.setVisibility(View.VISIBLE);
                                ibtnActionGovtIssueId.setImageResource(R.drawable.ic_success);
                                ibtnActionGovtIssueId.setTag("SUCCESS");
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

    private void setDocumentInView(AllDocumentResponse.ImportantDocument otherDocList) {
        if(otherDocList.getGovtIssueId()!=null && !otherDocList.getGovtIssueId().isEmpty()){
            tvTitleGovtIssueId.setText(otherDocList.getGovtIssueId());
            ibtnActionGovtIssueId.setVisibility(View.VISIBLE);
            ibtnActionGovtIssueId.setImageResource(R.drawable.ic_success);
            ibtnActionGovtIssueId.setTag("SUCCESS");
        }
    }

    private void resetEnableView(Boolean isView) {
        btnBack.setEnabled(isView);
        if (isView)
            mAdapter.setOnItemClickListener(mItemClickListener);
        else
            mAdapter.setOnItemClickListener(null);

    }

    private void initRecyclerView(View v) {
        rvDocument = v.findViewById(R.id.rv_document);
        rvDocument.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvDocument.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(mItemClickListener);

        rvDocument.setNestedScrollingEnabled(false);


    }

    private void initListener() {
        btnBack.setOnClickListener(mEventClickListener);
        ibtnActionGovtIssueId.setOnClickListener(mEventClickListener);
        tvTitleGovtIssueId.setOnClickListener(mEventClickListener);
    }

    private void initView(View v) {
        btnBack = v.findViewById(R.id.btn_back);


        progressBar = v.findViewById(R.id.pb_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView = v.findViewById(R.id.tv_alert_view);

        tvTitleGovtIssueId = v.findViewById(R.id.tv_title_govt_issue_id);
        ibtnActionGovtIssueId = v.findViewById(R.id.ibtn_action_govt_issue_id);

    }



    public void openGovtIssueIdDocument() {
        Intent in = new Intent(requireActivity(), FilePickerActivity.class);
        in.putExtra(Constant.MAX_NUMBER, 1);
        in.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "pdf"});
        startActivityForResult(in, REQUEST_CODE_GOVT_ISSUE_ID);
    }

    public void openStateLicenceDocument(int index) {
        fileIndex = index;
        Intent in = new Intent(requireActivity(), FilePickerActivity.class);
        in.putExtra(Constant.MAX_NUMBER, 1);
        in.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "pdf"});
        startActivityForResult(in, REQUEST_CODE_STATE_LICENCE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_STATE_LICENCE && data != null) {
            ArrayList<NormalFile> file = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            setFileInView(file);
            return;
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GOVT_ISSUE_ID && data != null) {
            ArrayList<NormalFile> file = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            setFileInGovtIdView(file);
            return;
        }
    }

    private void setFileInGovtIdView(ArrayList<NormalFile> file) {
        pathOfFile= file.get(0).getPath();
        if (pathOfFile != null) {
            tvTitleGovtIssueId.setText(""+pathOfFile); // path
            Log.e(TAG, pathOfFile);
            ibtnActionGovtIssueId.setVisibility(View.VISIBLE);
            ibtnActionGovtIssueId.setImageResource(R.drawable.ic_upload);
            ibtnActionGovtIssueId.setTag("UPLOAD");
        }
    }


    private void setFileInView(ArrayList<NormalFile> file) {
        pathOfFile = file.get(0).getPath();


        if (pathOfFile != null) {
            //  DocumentInfo doc=new DocumentInfo(fileIndex, pathOfFile, 1);
            DocumentInfo object=new DocumentInfo();
            object.setStatus(1);
            object.setDocumentName(pathOfFile);
            mAdapter.update(fileIndex,object);
            Log.e(TAG, pathOfFile);

        }


    }

    private boolean isRuntimePermissionGranted() {
        int reqOne = ContextCompat.checkSelfPermission(requireActivity(), READ_EXTERNAL_STORAGE);
        int reqTwo = ContextCompat.checkSelfPermission(requireActivity(), WRITE_EXTERNAL_STORAGE);

        return (reqOne & reqTwo) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestRuntimePermission(int requestCode) {
        requestPermissions(new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_STATE_LICENCE:
                if (grantResults.length > 0) {

                    boolean permOneAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permTwoAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (permOneAccepted && permTwoAccepted)
                        openStateLicenceDocument(0);
                    else {
                        tvAlertView.showTopAlert(getResources().getString(R.string.alert_access_denied));
                    }
                }
                break;

            case REQUEST_CODE_GOVT_ISSUE_ID:
                if (grantResults.length > 0) {

                    boolean permOneAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permTwoAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (permOneAccepted && permTwoAccepted)
                        openGovtIssueIdDocument();
                    else {
                        tvAlertView.showTopAlert(getResources().getString(R.string.alert_access_denied));
                    }
                }
                break;

        }
    }

    @Override
    public void onDestroyView() {
        btnBack.setOnClickListener(null);
        ibtnActionGovtIssueId.setOnClickListener(null);
        tvTitleGovtIssueId.setOnClickListener(null);
        super.onDestroyView();
    }

    @NonNull
    private MultipartBody.Part prepareImgFilePart(String filee,String key) {
        File file = new File(filee);
        // create RequestBody instance from videoFile
        RequestBody requestFile = RequestBody.create(file, okhttp3.MediaType.parse("image/*"));  // new
        // MultipartBody.Part is used to send also the actual docFile name
        return MultipartBody.Part.createFormData(key, file.getName(), requestFile);
    }

    @NonNull
    private RequestBody prepareRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }





    /*     MM/dd/yyyy     */
    private void showDatePicker(int pos) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(requireActivity(), R.style.PickerStyle, (view1, year, monthOfYear, dayOfMonth) -> {
            String x = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
            x=formatDate(x);
            mAdapter.update(pos,x);
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis()+86400000);  // todays + 1day
        datePickerDialog.show();
    }

    private View.OnClickListener mEventClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {


                case R.id.btn_back:
                    if (mFragmentListener != null)
                        mFragmentListener.popTopMostFragment();
                    break;


                case R.id.tv_title_govt_issue_id:
                    if (!isRuntimePermissionGranted()) {
                        requestRuntimePermission(REQUEST_CODE_GOVT_ISSUE_ID);
                        return;
                    }
                    openGovtIssueIdDocument();

                    break;



                case R.id.ibtn_action_govt_issue_id:
                    if(ibtnActionGovtIssueId.getTag()!=null && ((String)ibtnActionGovtIssueId.getTag()).equals("SUCCESS")){
                        return;
                    }
                    MultipartBody.Part filePath2 = prepareImgFilePart(pathOfFile,"GovtIssueId");
                    mViewModel.attemptGovtRegisteredUploadFile(mDocMap, filePath2,null);

                    break;

            }


        }
    };
    private DocumentAdapter.OnItemClickListener mItemClickListener = new DocumentAdapter.OnItemClickListener() {
        @Override
        public void onItemTextTitleClick(int pos, DocumentInfo info) {
            if (!isRuntimePermissionGranted()) {
                requestRuntimePermission(REQUEST_CODE_STATE_LICENCE);
                return;
            }
            openStateLicenceDocument(pos);

        }

        @Override
        public void onItemActionUploadClick(int pos, DocumentInfo info) {
            MultipartBody.Part filePath;
            String path = (String) info.getDocumentName(); // contains the file path .Strange, but that's true !!!
            filePath = prepareImgFilePart(path,"docFile");
            RequestBody dateOfExpiry = prepareRequestBody(info.getExpiryDate());
            mViewModel.attemptFileUpload(mDocMap, filePath, pos,dateOfExpiry);

        }

        @Override
        public void onItemActionDeleteClick(int pos, DocumentInfo info) {
            showDeleteDialog(pos,info);
        }


        @Override
        public void onItemDatePickerActionClick(int pos, DocumentInfo model) {
            showDatePicker(pos);
        }
    };

    private void showDeleteDialog(int pos, DocumentInfo info) {
        AlertDialogFragment deleteDialogFragment = AlertDialogFragment.newInstance("delete");
        deleteDialogFragment.show(getChildFragmentManager(), "TAG");
        deleteDialogFragment.setOnAlertDialogListener(new AlertDialogFragment.AlertDialogListener() {
            @Override
            public void onClickYes() {
                deleteDialogFragment.dismiss();
                int id = info.getId();
                mViewModel.attemptDeleteFile(mDocMap, id, pos);
            }

            @Override
            public void onClickNo() { }
        });
    }

    public static String formatDate(String oldDate)  {
        DateFormat sdFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }
}
