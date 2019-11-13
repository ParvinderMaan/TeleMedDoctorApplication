package com.telemed.doctor.miscellaneous;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;



public class SignOutDialogFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;



    public static SignOutDialogFragment newInstance() {
        return new SignOutDialogFragment();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you would like to logout ?");

        builder.setPositiveButton("YES", (dialog, which) -> {
            Intent intent=new Intent(getActivity(), RouterActivity.class);
//          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();

        });

        builder.setNegativeButton("NO", (dialog, which) -> dismiss());
        return builder.create();
    }





    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
