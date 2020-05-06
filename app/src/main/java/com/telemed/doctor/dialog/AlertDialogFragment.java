package com.telemed.doctor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;

public class AlertDialogFragment extends DialogFragment {
    private  String TAG;
    private AlertDialogListener alertDialogListener;
    private String suffix;

    public static AlertDialogFragment newInstance(String suffix) {
        AlertDialogFragment fragment=new AlertDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY_",suffix);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null)
            suffix=getArguments().getString("KEY_");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AppCompatAlertDialogStyleII);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Are you sure you would like to "+suffix+" ?");
        builder.setPositiveButton("YES", (dialog, which) -> {
            TAG = "YES";
            dismiss();
        });

        builder.setNegativeButton("NO", (dialog, which) -> {
            TAG = "NO";
            dismiss();
        });
        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        switch (TAG){
            case "YES":
//              mFragmentListener.startActivity("RouterActivity");
                alertDialogListener.onClickYes();
                break;
            case "NO":
                // nothing
                alertDialogListener.onClickNo();
                break;

        }
    }


    public void setOnAlertDialogListener(AlertDialogListener alertDialogListener){
        this.alertDialogListener = alertDialogListener;
    }

    public interface AlertDialogListener {
        void  onClickYes();
        void  onClickNo();

    }



}