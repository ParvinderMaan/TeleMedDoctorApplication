package com.telemed.doctor.miscellaneous;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;

public class AbortDialogFragment  extends DialogFragment {


    private RouterFragmentSelectedListener mFragmentListener;
    private static String TAG = "";

    public static AbortDialogFragment newInstance() {
        return new AbortDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
          mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you would like to abort signup ?");
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
         mFragmentListener = null;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        switch (TAG) {
            case "YES":
                mFragmentListener.popTillFragment("SignInFragment", 0);
                break;
            case "NO":
                // nothing
                break;

        }
    }

}