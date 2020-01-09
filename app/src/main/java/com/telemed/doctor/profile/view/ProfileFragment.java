package com.telemed.doctor.profile.view;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.FileUtil;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.network.WebUrl;
import com.telemed.doctor.profile.model.AlterProfilePicResponse;
import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.profile.viewmodel.ProfileViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private TextView tvBasicInfo, tvProfessionalInfo, tvBankInfo,tvDocSpeciality,tvDocName;
    private ImageButton ibtnClose,ibtnEditProfilePic;
    private BankInfoProfileFragment mBankInfoFragment;
    private ProfessionalInfoProfileFragment mProfessionalInfoFragment;
    private BasicInfoProfileFragment mBasicInfoFragment;
    private HomeFragmentSelectedListener mFragmentListener;
    private ProfileViewModel mViewModel;
    private CircleImageView civProfilePic;
    private String mFirstName,mLastName,mProfilePicUrl;
    private ProgressBar progressBar;
    private SharedPrefHelper mHelper;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mFirstName=mHelper.read(SharedPrefHelper.KEY_FIRST_NAME, "");
        mLastName=mHelper.read(SharedPrefHelper.KEY_LAST_NAME, "");
        mProfilePicUrl=mHelper.read(SharedPrefHelper.KEY_PROFILE_PIC, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasicInfoFragment = BasicInfoProfileFragment.newInstance();
        mProfessionalInfoFragment = ProfessionalInfoProfileFragment.newInstance();
        mBankInfoFragment = BankInfoProfileFragment.newInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        initView(v);
        initObserver();
        // @init
        setTextViewState(true,false,false);
        showFragment("TAG_BASIC");
    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getViewEnabled()
                .observe(getViewLifecycleOwner(), isView -> {
                    ibtnEditProfilePic.setEnabled(isView);
                });


        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        AlterProfilePicResponse.Data infoObj = response.getData().getData(); // adding Additional Info
//                        tvAlertView.showTopAlert(response.getData().getMessage());
//                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                          if(infoObj.getProfilePicUrl()!=null && (!infoObj.getProfilePicUrl().isEmpty())){
//                            Picasso.get().load(WebUrl.IMAGE_URL+infoObj.getProfilePicUrl()).fit().centerCrop().into(civProfilePic);
                              mHelper.write(SharedPrefHelper.KEY_PROFILE_PIC, infoObj.getProfilePicUrl());
                          }
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
//                        tvAlertView.showTopAlert(response.getErrorMsg());
                        Toast.makeText(getActivity(), response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;

            }

        });


    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);
        tvBasicInfo = v.findViewById(R.id.tv_basic_info);
        tvProfessionalInfo = v.findViewById(R.id.tv_professional_info);
        tvBankInfo = v.findViewById(R.id.tv_bank_info);
        tvDocName = v.findViewById(R.id.tv_doc_name);
        ibtnEditProfilePic = v.findViewById(R.id.ibtn_edit_profile_pic);

        //------------------------------------------------
        tvBasicInfo.setOnClickListener(mClickListener);
        tvProfessionalInfo.setOnClickListener(mClickListener);
        tvBankInfo.setOnClickListener(mClickListener);
        ibtnClose.setOnClickListener(mClickListener);
        ibtnEditProfilePic.setOnClickListener(mClickListener);
        //------------------------------------------------
        tvDocSpeciality = v.findViewById(R.id.tv_doc_speciality);
        civProfilePic = v.findViewById(R.id.civ_profile_pic);
        progressBar = v.findViewById(R.id.progress_bar);



        tvDocName.setText(mFirstName+" "+mLastName);
        tvDocSpeciality.setText("");
        if(mProfilePicUrl!=null && !mProfilePicUrl.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            Picasso.get().load(WebUrl.IMAGE_URL+mProfilePicUrl)
                    .placeholder(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
                    .fit()
                    .centerCrop()
                    .into(civProfilePic, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }else{
            Picasso.get().load("abcdef")
                    .placeholder(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
                    .fit()
                    .centerCrop()
                    .into(civProfilePic);
        }

        civProfilePic.setOnClickListener(v1 -> {

            checkRuntimePermission();
        });

    }

    private void showFragment(String tag) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        switch (tag) {

            case "TAG_BASIC":
                if (mBasicInfoFragment.isAdded()) ft.show(mBasicInfoFragment);
                else ft.add(R.id.fl_container, mBasicInfoFragment);
                if (mProfessionalInfoFragment.isAdded()) ft.hide(mProfessionalInfoFragment);
                if (mBankInfoFragment.isAdded()) ft.hide(mBankInfoFragment);
                ft.commit();

                break;
            case "TAG_PROFESSIONAL":
                if (mProfessionalInfoFragment.isAdded()) ft.show(mProfessionalInfoFragment);
                else ft.add(R.id.fl_container, mProfessionalInfoFragment);
                if (mBasicInfoFragment.isAdded()) ft.hide(mBasicInfoFragment);
                if (mBankInfoFragment.isAdded()) ft.hide(mBankInfoFragment);
                ft.commit();

                break;
            case "TAG_BANK":
                if (mBankInfoFragment.isAdded()) ft.show(mBankInfoFragment);
                else ft.add(R.id.fl_container, mBankInfoFragment);
                if (mBasicInfoFragment.isAdded()) ft.hide(mBasicInfoFragment);
                if (mProfessionalInfoFragment.isAdded()) ft.hide(mProfessionalInfoFragment);
                ft.commit();
                break;


        }

    }


    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.ibtn_close:
                    if (mFragmentListener != null)
                        mFragmentListener.popTopMostFragment();
                    break;
                case R.id.tv_basic_info:
                    setTextViewState(true,false,false);
                    showFragment("TAG_BASIC");

                    break;
                case R.id.tv_professional_info:
                    setTextViewState(false,true,false);
                    showFragment("TAG_PROFESSIONAL");
                    break;
                case R.id.tv_bank_info:
                    setTextViewState(false,false,true);
                    showFragment("TAG_BANK");
                    break;

                case R.id.ibtn_edit_profile_pic:

                    checkRuntimePermission();

                    break;
            }


        }
    };

    @Override
    public void onDestroyView() {
        releaseResource();
        super.onDestroyView();
    }

    private void releaseResource() {
        tvBasicInfo.setOnClickListener(null);
        tvProfessionalInfo.setOnClickListener(null);
        tvBankInfo.setOnClickListener(null);
        ibtnClose.setOnClickListener(null);
        mClickListener=null;
    }

    private void setTextViewState(boolean a, boolean b, boolean c){
        tvBasicInfo.setSelected(a);
        tvProfessionalInfo.setSelected(b);
        tvBankInfo.setSelected(c);
    }


    // Check storage permission first
    private void checkRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 99);
            }
        } else {
               openGallery();
        }
    }

    // Start gallery intent to get profile photo
    private void openGallery() {
        CropImage.ActivityBuilder activityBuilder = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("My Profile")
                .setAllowFlipping(false)
                .setAllowRotation(false)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(200, 200)
                .setAutoZoomEnabled(false)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonIcon(android.R.drawable.ic_menu_crop);

        Intent intent = activityBuilder.getIntent(requireActivity());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                if (imageUri != null) {
                    civProfilePic.setImageURI(imageUri);
                    String imagePath = FileUtil.getPath(getActivity(), imageUri);
                    attemptImageUpload(imagePath);
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void attemptImageUpload(String imagePath) {
        if(imagePath!=null)
            mViewModel.alterProfilePic(prepareImgFilePart(imagePath,"imgFile")); // key
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 99) {
            if (grantResults.length == 0) {
                Log.e("permission", "Permission has been denied by user");
            } else {
                Log.e("permission", "Permission has been granted by user");
                openGallery();
            }
        }
    }

    @NonNull
    private MultipartBody.Part prepareImgFilePart(String filee, String name) {
        File file = new File(filee);
        RequestBody requestFile = RequestBody.create(file,okhttp3.MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }

    public void updateUi(BasicInfoResponse.BasicDetail info) {
        tvDocSpeciality.setText(info.getSpeciality()); // 1-->MALE 2 --> FEMALE 3-->OTHER
    }
}
