package com.telemed.doctor.setting.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.AlertDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.setting.viewmodel.SettingViewModel;
import com.telemed.doctor.setting.model.TimeZone;
import com.telemed.doctor.setting.model.UserSetting;
import com.telemed.doctor.setting.model.UserSettingRequest;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private TextView tvSelectedLanguage,tvSelectedTimeZone;
    private HomeFragmentSelectedListener mFragmentListener;
    private SwitchCompat switchPushNotifi, switchEmailNotifi, switchSmsNotifi;
    private ImageButton ibtnClose, ibtnChangePassword, ibtnTermAndCondition;
    private SettingViewModel mViewModel;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;
    private Integer mSelectedLanguageId,mSelectedTimeZoneId;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private List<Language> lstOfLanguage;
    private List<TimeZone> lstOfTimeZone;


    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        mFragmentListener = (HomeFragmentSelectedListener) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initObserver();

        mViewModel.fetchUserSettings(mHeaderMap);

    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);
        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        tvAlertView = v.findViewById(R.id.tv_alert_view);

        tvSelectedLanguage = v.findViewById(R.id.tv_selected_language);
        tvSelectedTimeZone = v.findViewById(R.id.tv_selected_time_zone);
        ibtnChangePassword = v.findViewById(R.id.ibtn_change_password);
        ibtnTermAndCondition = v.findViewById(R.id.ibtn_term_and_condition);

        switchPushNotifi = v.findViewById(R.id.switch_push_notifi);
        switchEmailNotifi = v.findViewById(R.id.switch_email_notifi);
        switchSmsNotifi = v.findViewById(R.id.switch_sms_notifi);
    }

    private void initObserver() {

        mViewModel.getAllLanguages().observe(getViewLifecycleOwner(), languages -> {
            this.lstOfLanguage = languages;
        });

        mViewModel.getTimeZones().observe(getViewLifecycleOwner(), timeZones -> {
            this.lstOfTimeZone = timeZones;
        });

        mViewModel.getResultantUserSettings().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        mViewModel.setLanguageList(response.getData().getData().getAppLanguagesList());
                        mViewModel.setTimeZoneList(response.getData().getData().getTimeZonesList());
                        populateUi(response.getData().getData().getUserSetting());
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        switchPushNotifi.setOnCheckedChangeListener(mOnCheckedChangeListener);
                        switchEmailNotifi.setOnCheckedChangeListener(mOnCheckedChangeListener);
                        switchSmsNotifi.setOnCheckedChangeListener(mOnCheckedChangeListener);
                    }
                    break;

            }

        });

        mViewModel.getResultantUpdateUserSetting().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        tvAlertView.showTopAlert(response.getData().getMessage());

                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;

            }

        });


        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), isView -> {
                    switchPushNotifi.setClickable(isView);
                    switchEmailNotifi.setClickable(isView);
                    switchSmsNotifi.setClickable(isView);
                    tvSelectedLanguage.setClickable(isView);
                });
    }

    private void populateUi(UserSetting data) {
        mSelectedLanguageId = data.getCurrentLanguageId();
        mSelectedTimeZoneId= data.getTimeZoneId();
        switchPushNotifi.setChecked(data.getPushNotificationStatus() != null ? data.getPushNotificationStatus() : true);
        switchEmailNotifi.setChecked(data.getEmailNotificationStatus() != null ? data.getEmailNotificationStatus() : true);
        switchSmsNotifi.setChecked(data.getSmsNotificationStatus() != null ? data.getSmsNotificationStatus() : true);
        tvSelectedLanguage.setText(data.getCurrentLanguage() != null ? data.getCurrentLanguage() : "");
        tvSelectedTimeZone.setText(data.getTimeZone() != null ? data.getTimeZone() : "");

        switchPushNotifi.setOnCheckedChangeListener(mOnCheckedChangeListener);
        switchEmailNotifi.setOnCheckedChangeListener(mOnCheckedChangeListener);
        switchSmsNotifi.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private void initListener() {
        switchPushNotifi.setOnCheckedChangeListener(null);
        switchEmailNotifi.setOnCheckedChangeListener(null);
        switchSmsNotifi.setOnCheckedChangeListener(null);

        ibtnClose.setOnClickListener(v -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        tvSelectedLanguage.setOnClickListener(v -> {
            showLanguagePopupMenu();
        });

        tvSelectedTimeZone.setOnClickListener(v -> {
            showTimeZonePopupMenu();
        });


        ibtnChangePassword.setOnClickListener(v -> {
            if (mFragmentListener != null)
                mFragmentListener.showFragment("ChangePasswordFragment", null);
        });


        ibtnTermAndCondition.setOnClickListener(v -> {
            if (mFragmentListener != null)
                mFragmentListener.showFragment("TermAndConditionFragment", null);
        });


    }

    private void showLanguagePopupMenu() {
        if (lstOfLanguage == null)
            return;


        PopupMenu popupMenu = new PopupMenu(requireActivity(), tvSelectedLanguage);
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < lstOfLanguage.size(); i++) {
            menu.add(0, lstOfLanguage.get(i).getId(), 0, lstOfLanguage.get(i).getName());
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            mSelectedLanguageId = item.getItemId();
            tvSelectedLanguage.setText(item.getTitle());
            UserSettingRequest in = UserSettingRequest.builder().setPushNotificationStatus(switchPushNotifi.isChecked())
                    .setEmailNotificationStatus(switchEmailNotifi.isChecked())
                    .setSmsNotificationStatus(switchSmsNotifi.isChecked())
                    .setCurrentLanguageId(mSelectedLanguageId)
                    .setCurrentTimeZone(mSelectedTimeZoneId);
            mViewModel.updateUserSettings(mHeaderMap, in);
            return false;
        });
        popupMenu.show();
    }

    private void showTimeZonePopupMenu() {
        if (lstOfTimeZone == null)
            return;


        PopupMenu popupMenu = new PopupMenu(requireActivity(), tvSelectedTimeZone);
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < lstOfTimeZone.size(); i++) {
            menu.add(0, lstOfTimeZone.get(i).getId(), 0, lstOfTimeZone.get(i).getName());
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            mSelectedTimeZoneId = item.getItemId();
            tvSelectedTimeZone.setText(item.getTitle());
            UserSettingRequest in = UserSettingRequest.builder().setPushNotificationStatus(switchPushNotifi.isChecked())
                    .setEmailNotificationStatus(switchEmailNotifi.isChecked())
                    .setSmsNotificationStatus(switchSmsNotifi.isChecked())
                    .setCurrentLanguageId(mSelectedLanguageId)
                    .setCurrentTimeZone(mSelectedTimeZoneId);
            mViewModel.updateUserSettings(mHeaderMap, in);
            return false;
        });
        popupMenu.show();
    }



    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.switch_push_notifi:
                    if (switchPushNotifi.getTag() != null && ((String) switchPushNotifi.getTag()).equals("REPEAT")) {
                        switchPushNotifi.setTag(null);
                        return;
                    } else {
                        AlertDialogFragment alterDialogFragment = AlertDialogFragment.newInstance("change");
                        alterDialogFragment.show(getChildFragmentManager(), "TAG");
                        alterDialogFragment.setOnAlertDialogListener(new AlertDialogFragment.AlertDialogListener() {
                            @Override
                            public void onClickYes() {

                                alterDialogFragment.dismiss();
                                UserSettingRequest in = UserSettingRequest.builder().
                                        setPushNotificationStatus(switchPushNotifi.isChecked())
                                        .setEmailNotificationStatus(switchEmailNotifi.isChecked())
                                        .setSmsNotificationStatus(switchSmsNotifi.isChecked())
                                        .setCurrentLanguageId(mSelectedLanguageId)
                                        .setCurrentTimeZone(mSelectedTimeZoneId);
                                mViewModel.updateUserSettings(mHeaderMap, in);
                            }

                            @Override
                            public void onClickNo() {
                                switchPushNotifi.setTag("REPEAT");
                                switchPushNotifi.setChecked(!isChecked);
                            }
                        });


                    }
                    break;

                case R.id.switch_email_notifi:
                    if (switchEmailNotifi.getTag() != null && ((String) switchEmailNotifi.getTag()).equals("REPEAT")) {
                        switchEmailNotifi.setTag(null);
                        return;
                    } else {
                        AlertDialogFragment alterDialogFragment = AlertDialogFragment.newInstance("change");
                        alterDialogFragment.show(getChildFragmentManager(), "TAG");
                        alterDialogFragment.setOnAlertDialogListener(new AlertDialogFragment.AlertDialogListener() {
                            @Override
                            public void onClickYes() {
                                alterDialogFragment.dismiss();
                                UserSettingRequest in = UserSettingRequest.builder().
                                        setPushNotificationStatus(switchPushNotifi.isChecked())
                                        .setEmailNotificationStatus(switchEmailNotifi.isChecked())
                                        .setSmsNotificationStatus(switchSmsNotifi.isChecked())
                                        .setCurrentLanguageId(mSelectedLanguageId)
                                        .setCurrentTimeZone(mSelectedTimeZoneId);
                                mViewModel.updateUserSettings(mHeaderMap, in);
                            }

                            @Override
                            public void onClickNo() {
                                switchEmailNotifi.setTag("REPEAT");
                                switchEmailNotifi.setChecked(!isChecked);
                            }
                        });
                    }
                    break;

                case R.id.switch_sms_notifi:
                    if (switchSmsNotifi.getTag() != null && ((String) switchSmsNotifi.getTag()).equals("REPEAT")) {
                        switchSmsNotifi.setTag(null);
                        return;
                    } else {

                        AlertDialogFragment alterDialogFragment = AlertDialogFragment.newInstance("change");
                        alterDialogFragment.show(getChildFragmentManager(), "TAG");
                        alterDialogFragment.setOnAlertDialogListener(new AlertDialogFragment.AlertDialogListener() {
                            @Override
                            public void onClickYes() {
                                alterDialogFragment.dismiss();
                                UserSettingRequest in = UserSettingRequest.builder().
                                        setPushNotificationStatus(switchPushNotifi.isChecked())
                                        .setEmailNotificationStatus(switchEmailNotifi.isChecked())
                                        .setSmsNotificationStatus(switchSmsNotifi.isChecked())
                                        .setCurrentLanguageId(mSelectedLanguageId)
                                        .setCurrentTimeZone(mSelectedTimeZoneId);
                                mViewModel.updateUserSettings(mHeaderMap, in);
                            }

                            @Override
                            public void onClickNo() {
                                switchPushNotifi.setTag("REPEAT");
                                switchPushNotifi.setChecked(!isChecked);
                            }
                        });
                    }
                    break;
            }

        }
    };

}
