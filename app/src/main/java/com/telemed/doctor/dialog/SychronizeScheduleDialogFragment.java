package com.telemed.doctor.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.telemed.doctor.R;

public class SychronizeScheduleDialogFragment extends DialogFragment {

    private SychronizeScheduleDialogListener mDialogListener;
    private TextView tvSyncDate,tvSyncWeekday,tvCancelOptions;
    private static String TAG;

    @NonNull
    public static SychronizeScheduleDialogFragment newInstance() {
        return new SychronizeScheduleDialogFragment();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_sychronize_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog dialog = getDialog();
        Window window = null;
        if (dialog != null) {
            window = dialog.getWindow();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

        }
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        }
        tvSyncDate = view.findViewById(R.id.tv_sync_date);
        tvSyncWeekday = view.findViewById(R.id.tv_sync_weekday);
        tvCancelOptions = view.findViewById(R.id.tv_cancel_options);


        tvSyncDate.setOnClickListener(v->{
            TAG="DATE_WISE";
            dismiss();

        });
        tvSyncWeekday.setOnClickListener(v->{
            TAG="WEEK_WISE";
            dismiss();



        });

        tvCancelOptions.setOnClickListener(v->{
            TAG="CANCEL";
            dismiss();


        });

    }
    public void setOnScheduleDialogListener(SychronizeScheduleDialogListener listener) {
        mDialogListener=  listener;
    }


    public interface SychronizeScheduleDialogListener {
        void onClickWeekWise();
        void onClickDateWise();


    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        switch (TAG){
            case "DATE_WISE":
                if(mDialogListener!=null){
                    mDialogListener.onClickDateWise();
                }

                break;
            case "WEEK_WISE":
                if(mDialogListener!=null){
                    mDialogListener.onClickWeekWise();
                }
                break;

            case "CANCEL":
                // nothing
                break;

        }

    }
}
