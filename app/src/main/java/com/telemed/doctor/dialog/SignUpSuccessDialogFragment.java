package com.telemed.doctor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;

public class SignUpSuccessDialogFragment extends DialogFragment {
    private RouterFragmentSelectedListener mFragmentListener;
    private String mMessage;

    public static SignUpSuccessDialogFragment newInstance(String msg) {
        SignUpSuccessDialogFragment fragment=new SignUpSuccessDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY_MSG",msg);
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
        if(getArguments()!=null){
            String msg =  getArguments().getString("KEY_MSG");
            if (msg != null) this.mMessage=msg;

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(mMessage);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dismiss();
        });

//        builder.setNegativeButton("NO", (dialog, which) -> {
//            TAG = "NO";
//            dismiss();
//        });
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
               if(mFragmentListener!=null)
                mFragmentListener.popTillFragment("SignInFragment", 0);
        }
    }


