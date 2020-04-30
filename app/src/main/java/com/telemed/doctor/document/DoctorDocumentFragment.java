package com.telemed.doctor.document;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.SecondaryActivity;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.document.model.Assessment;
import com.telemed.doctor.document.model.Drug;
import com.telemed.doctor.document.model.MedicalHistoryRecord;
import com.telemed.doctor.document.model.MedicalHistoryRequest;
import com.telemed.doctor.document.model.MedicalHistoryResponse;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.network.WebUrl;
import com.telemed.doctor.schedule.model.PatientDetailResponse;
import com.telemed.doctor.schedule.model.PatientProfileInfo;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDocumentFragment extends Fragment {
    private final String TAG = DoctorDocumentFragment.class.getSimpleName();
    private HomeFragmentSelectedListener mFragmentListener;
    private TextView tvPatientName, tvAddress, tvReviewCount, tvAge, tvGender, tvHeight,
            tvWeight;
    private EditText edtSymptoms,edtSubjective,edtObjective,edtAssessment,edtAddDrug;
    private TextView tvAddDrugAction;
    private ImageButton ibtnClose;
    private DoctorDocumentViewModel mViewModel;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private CircleImageView civProfilePic;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;
    private Button btnMedicalRecord;
    private String mPatientId;
    private Integer mAppointmentId;
    private Button btnSave;
    private RecyclerView rvPrescription;
    private NestedScrollView svParent;
    private PrescriptionAdapter mAdapter;
    private LinearLayout llTop;
    private Integer medicalHistoryId;


    public static DoctorDocumentFragment newInstance(Object payload) {
        DoctorDocumentFragment fragment=new DoctorDocumentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (UserInfoWrapper) payload); // SignUpIResponse.Data
        fragment.setArguments(bundle);
        return fragment;
    }


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // collect our intent
        if (getArguments() != null) {
            UserInfoWrapper out = getArguments().getParcelable("KEY_");
            if (out != null) {
                mPatientId=out.getPatientId();
            }
            if (out != null) {
                mAppointmentId = out.getAppointmentId();
            }
        }
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);

         mAdapter=new PrescriptionAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_doctor_document, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(DoctorDocumentViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initRecyclerView(v);
        initListener();
        initObserver();

        mViewModel.fetchPatientDetail(mHeaderMap,mPatientId);
        mViewModel.fetchPastMedicalHistory(mHeaderMap,mAppointmentId);

    }

    private void initRecyclerView(View v) {
        rvPrescription = v.findViewById(R.id.rv_prescription);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(requireActivity());
        rvPrescription.setLayoutManager(mLinearLayoutManager);
        rvPrescription.setAdapter(mAdapter);

    }

    private void initListener() {
        btnMedicalRecord.setOnClickListener(v1 -> {
            if (mFragmentListener != null) {
               // mFragmentListener.showFragment("MedicalRecordFragment", mPatientId);
//                UserInfoWrapper in=new UserInfoWrapper();
//                in.setPatientId(mPatientId);
//                mFragmentListener.startActivity("SecondaryActivity", "MedicalRecordFragment",in);
            }
        });


        ibtnClose.setOnClickListener(v1 -> {
            if (requireActivity() instanceof SecondaryActivity)
                requireActivity().finish();
            else
                mFragmentListener.popTopMostFragment();
        });

        btnSave.setOnClickListener(v1 -> {


            MedicalHistoryRequest in=new MedicalHistoryRequest();
            List<Assessment> lstOfAssessment=new ArrayList<>();
            Assessment assessment=new Assessment();
            assessment.setName(!edtAssessment.getText().toString().isEmpty()?edtAssessment.getText().toString():"");
            lstOfAssessment.add(assessment);
            in.setAppointmentId(mAppointmentId);
            in.setAssessmentList(lstOfAssessment);
            in.setDescription("dummy");
            in.setDrugsList(mAdapter.getItems());
            in.setNotes("");
            in.setPrecautions("");
            in.setSymptom(!edtSymptoms.getText().toString().isEmpty()?edtSymptoms.getText().toString():"");
            in.setObjective(!edtObjective.getText().toString().isEmpty()?edtObjective.getText().toString():"");
            in.setSubjective(!edtSubjective.getText().toString().isEmpty()?edtSubjective.getText().toString():"");


            if(mViewModel.getResultantMedicalHistoryDetail().getValue()!=null &&
                    mViewModel.getResultantMedicalHistoryDetail().getValue().getData()!=null
                    && mViewModel.getResultantMedicalHistoryDetail().getValue().getData().getData().getMedicalHistory()!=null){
                 // update.....
                in.setId(medicalHistoryId);
                mViewModel.updatePastMedicalHistory(mHeaderMap,in);
            }else {
                // add........
                mViewModel.addPastMedicalHistory(mHeaderMap,in);
            }

            svParent.smoothScrollTo(0, llTop.getTop());

        });
        tvAddDrugAction.setOnClickListener(v3 -> {
            String drugName=edtAddDrug.getText().toString().trim();

            if(!TextUtils.isEmpty(drugName)){
                Drug drug=new Drug();
                drug.setName(drugName);
                mAdapter.add(drug);
                edtAddDrug.setText("");
            }
        });
    }

    private void initView(View v) {
        svParent= v.findViewById(R.id.sv_parent);
        llTop= v.findViewById(R.id.ll_topp);
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        ibtnClose = v.findViewById(R.id.ibtn_close);

        btnMedicalRecord = v.findViewById(R.id.btn_medical_record);
        tvPatientName = v.findViewById(R.id.tv_patient_name);
        tvAddress = v.findViewById(R.id.tv_address);
        tvReviewCount = v.findViewById(R.id.tv_review_count);
        tvAge = v.findViewById(R.id.tv_age);
        tvGender = v.findViewById(R.id.tv_gender);
        tvHeight = v.findViewById(R.id.tv_height);
        tvWeight = v.findViewById(R.id.tv_weight);
        civProfilePic = v.findViewById(R.id.civ_profile_pic);
        btnSave = v.findViewById(R.id.btn_save);

        edtSymptoms = v.findViewById(R.id.edt_symptoms);
        edtSubjective = v.findViewById(R.id.edt_subjective);
        edtObjective = v.findViewById(R.id.edt_objective);
        edtAssessment = v.findViewById(R.id.edt_assessment);
        edtAddDrug = v.findViewById(R.id.edt_add_drug);
        tvAddDrugAction = v.findViewById(R.id.tv_add_drug_action);







    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultantPatientDetail().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        PatientDetailResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getProfileInfo() != null) {
                            setProfileView(infoObj.getProfileInfo());
                        }
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;
            }
        });

        mViewModel.getResultantMedicalHistoryDetail().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MedicalHistoryResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getMedicalHistory() != null) {
                            setMedicalHistoryView(infoObj.getMedicalHistory());
                        }
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;
            }
        });
        mViewModel.getResultantAlterMedicalHistoryDetail().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MedicalHistoryResponse infoObj = response.getData();
                        if (infoObj.getMessage() != null) {
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(infoObj.getMessage());
                        }
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

    private void setMedicalHistoryView(MedicalHistoryRecord medicalHistory) {
        this.medicalHistoryId=medicalHistory.getId();
        edtSymptoms.setText(medicalHistory.getSymptom()!=null?medicalHistory.getSymptom():"");
        edtSubjective.setText(medicalHistory.getSubjective()!=null?medicalHistory.getSubjective():"");
        edtObjective.setText(medicalHistory.getObjective()!=null?medicalHistory.getObjective():"");
        edtAssessment.setText(medicalHistory.getAssessments()!=null &&
                !medicalHistory.getAssessments().isEmpty()?medicalHistory.getAssessments().get(0).getName():"");

        if(medicalHistory.getDrug()!=null)
        mAdapter.addAll(medicalHistory.getDrug());


        //..........................

    }

    private void setProfileView(PatientProfileInfo info) {
        tvPatientName.setText(info.getFirstName() != null && info.getLastName() != null ? info.getFirstName() + " " + info.getLastName() : "");
        tvAddress.setText(info.getStateName() != null && info.getCountryName() != null ? info.getStateName() + "," + info.getCountryName() : "");
        tvReviewCount.setText(info.getRating() != null ? "" + info.getRating() : "-");
        tvAge.setText(info.getAge() != null ? info.getAge() : "");
        tvGender.setText(info.getGender() != null ? info.getGender() : "");
        tvHeight.setText(info.getHeight() != null ? "" + info.getHeight() : "");
        tvWeight.setText(info.getWeight() != null ? "" + info.getWeight() : "");
        Picasso.get().load(info.getProfilePic() != null && !info.getProfilePic().isEmpty() ? WebUrl.IMAGE_URL + info.getProfilePic() : "www.example.com")
                .placeholder(R.drawable.img_avatar)
                .error(R.drawable.img_avatar)
                .fit()
                .centerCrop()
                .into(civProfilePic);


    }

    private void resetEnableView(Boolean aBoolean) {

    }
}
