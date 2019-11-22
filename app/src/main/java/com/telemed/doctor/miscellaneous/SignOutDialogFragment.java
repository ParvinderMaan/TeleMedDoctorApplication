package com.telemed.doctor.miscellaneous;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;


public class SignOutDialogFragment extends DialogFragment {


    private HomeFragmentSelectedListener mFragmentListener;
    private static String TAG = "";

    public static SignOutDialogFragment newInstance() {
        return new SignOutDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you would like to logout ?");
        builder.setPositiveButton("YES", (dialog, which) -> {

            TAG = "YES";dismiss();
        });

        builder.setNegativeButton("NO", (dialog, which) -> {
            TAG = "NO";dismiss();
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

            switch (TAG){
                case "YES":
                    mFragmentListener.startActivity("RouterActivity");
                    break;
                case "NO":
                    // nothing
                    break;

            }
    }


}
