package com.telemed.doctor.consult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class AppointmentUpcomingAdapter extends RecyclerView.Adapter<AppointmentUpcomingAdapter.ViewHolder> {

    private static final String TAG = AppointmentUpcomingAdapter.class.getSimpleName();
    private List<String> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public AppointmentUpcomingAdapter() {
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");

    }


     static class ViewHolder extends RecyclerView.ViewHolder {

        private final Button btnMedicalRecord;

        public ViewHolder(View itemView) {
            super(itemView);
            btnMedicalRecord=itemView.findViewById(R.id.btn_medical_record);


        }

        public void bind(final String model, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }


            });
            btnMedicalRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClickMedicalRecord(0);
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_appointment_upcoming, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemClickMedicalRecord(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){

        this.onItemClickListener = onItemClickListener;
    }

}