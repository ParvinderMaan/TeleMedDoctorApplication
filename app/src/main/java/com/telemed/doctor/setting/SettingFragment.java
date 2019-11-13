package com.telemed.doctor.setting;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private TextView tvSelectedLanguage;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        v.findViewById(R.id.ibtn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null)
                    ((HomeActivity)getActivity()).popTopMostFragment();
            }
        });
        tvSelectedLanguage=v.findViewById(R.id.tv_selected_language);
        tvSelectedLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null){
                    showLanguageDialog();

                }

            }
        });



        v.findViewById(R.id.ibtn_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null)
                    ((HomeActivity)getActivity()).showChangePasswordFragment();
            }
        });


        v.findViewById(R.id.ibtn_term_and_condition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null)
                    ((HomeActivity)getActivity()).showTermAndConditionFragment();
            }
        });


    }

    private void showLanguageDialog() {
        final String[] mProvinceItems = getResources().getStringArray(R.array.array_language);
        final ArrayList itemsSelected = new ArrayList();
        Dialog dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        // builder.setTitle("Select Your Region");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        builder.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setText("Select your Language");

        builder.setSingleChoiceItems(mProvinceItems, 0, null)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        try {
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            tvSelectedLanguage.setText(mProvinceItems[selectedPosition]);
                        } catch (Exception io) {
                            io.printStackTrace();
                        }

                        // Do something useful withe the position of the selected radio button
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }
}
