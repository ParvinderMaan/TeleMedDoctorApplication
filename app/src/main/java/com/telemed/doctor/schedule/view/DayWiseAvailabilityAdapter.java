package com.telemed.doctor.schedule.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.schedule.model.TimeSlotModel;

import java.util.ArrayList;
import java.util.List;

public class DayWiseAvailabilityAdapter extends RecyclerView.Adapter<DayWiseAvailabilityAdapter.ViewHolder> {

    private static final String TAG = DayWiseAvailabilityAdapter.class.getSimpleName();
    private List<TimeSlotModel> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public DayWiseAvailabilityAdapter() {


    }

    public void setItems(List<TimeSlotModel> items) {
        list.addAll(items);
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTimeSlot;
        private View viewOne;
        private TextView tvPatientName;
        private View viewTwo;
        private TextView tvFirmName;
        private LinearLayout llRoot;


        public ViewHolder(View itemView) {
            super(itemView);
            llRoot=itemView.findViewById(R.id.ll_root);
            tvTimeSlot=itemView.findViewById(R.id.tv_time_slot);
            viewOne=itemView.findViewById(R.id.view_one);
            tvPatientName=itemView.findViewById(R.id.tv_patient_name);
            viewTwo=itemView.findViewById(R.id.view_two);
            tvFirmName=itemView.findViewById(R.id.tv_firm_name);


        }

        public void bind(final TimeSlotModel model, final DayWiseAvailabilityAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });

              tvTimeSlot.setText(model.getSlotFrom()+" - "+model.getSlotTo());
/*
public enum AppointmentStatus
        {
            Open = 1,
            Pending=2,
            Confirmed=3
        }
 */
              switch (model.getAppointmentStatus()){

                  case 1:
                      viewOne.setBackgroundResource(R.color.colorBlue);
                      viewTwo.setBackgroundResource(R.color.colorBlue);
                      llRoot.setBackgroundResource(R.drawable.rounded_background_xiv);
                      tvTimeSlot.setTextColor(Color.parseColor("#395596"));
                      tvPatientName.setTextColor(Color.parseColor("#395596"));
                      tvFirmName.setTextColor(Color.parseColor("#395596"));
                      tvPatientName.setText("--");
                      tvFirmName.setText("--");
                      break;
                  case 2:
                      viewOne.setBackgroundResource(R.color.colorWhite);
                      viewTwo.setBackgroundResource(R.color.colorWhite);
                      llRoot.setBackgroundResource(R.drawable.rounded_background_xv);
                      tvTimeSlot.setTextColor(Color.parseColor("#FFFFFF"));
                      tvPatientName.setTextColor(Color.parseColor("#FFFFFF"));
                      tvFirmName.setTextColor(Color.parseColor("#FFFFFF"));
                      tvPatientName.setText(model.getPatientName());
                      tvFirmName.setText("Infinity Doctor");
                      break;
                  case 3:
                      viewOne.setBackgroundResource(R.color.colorWhite);
                      viewTwo.setBackgroundResource(R.color.colorWhite);
                      llRoot.setBackgroundResource(R.drawable.rounded_background_xiii);
                      tvTimeSlot.setTextColor(Color.parseColor("#FFFFFF"));
                      tvPatientName.setTextColor(Color.parseColor("#FFFFFF"));
                      tvFirmName.setTextColor(Color.parseColor("#FFFFFF"));
                      tvPatientName.setText(model.getPatientName());
                      tvFirmName.setText("Infinity Doctor");
                      break;
              }




        }
    }

    @NonNull
    @Override
    public DayWiseAvailabilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_day_wise_availability, parent, false);
        return new DayWiseAvailabilityAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DayWiseAvailabilityAdapter.ViewHolder holder, int position) {
        TimeSlotModel item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }




}