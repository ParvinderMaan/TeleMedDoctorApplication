package com.telemed.doctor.consult.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.consult.model.PastAppointment;
import com.telemed.doctor.network.WebUrl;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryAdapter.ViewHolder> {
    private static final String TAG = AppointmentHistoryAdapter.class.getSimpleName();

    private List<PastAppointment> items=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public AppointmentHistoryAdapter() {

    }
    public void addAll(List<PastAppointment> lstOfAppointments) {
        items.clear();
        items.addAll(lstOfAppointments);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civPatientPic;
        private TextView tvPatientName, tvPatientAddr,tvDateTime;
        private ImageButton ibtnMore;
        public ViewHolder(View itemView) {
            super(itemView);
            civPatientPic=itemView.findViewById(R.id.civ_patient_pic);
            tvPatientName=itemView.findViewById(R.id.tv_patient_name);
            tvPatientAddr=itemView.findViewById(R.id.tv_patient_addr);
            tvDateTime=itemView.findViewById(R.id.tv_date_time);
            ibtnMore=itemView.findViewById(R.id.ibtn_more);


        }

        public void bind(final PastAppointment model, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   listener.onItemClickDelete(getLayoutPosition());



                }
            });


            ibtnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                       listener.onItemMoreClick(getLayoutPosition());



                }
            });
            if(model.getProfilePic()!=null && !model.getProfilePic().isEmpty()){
                Picasso.get().load(WebUrl.IMAGE_URL+model.getProfilePic())
                        .placeholder(R.drawable.img_avatar)
                        .error(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civPatientPic);
            }


            civPatientPic=itemView.findViewById(R.id.civ_patient_pic);
            tvPatientName=itemView.findViewById(R.id.tv_patient_name);
            tvPatientAddr=itemView.findViewById(R.id.tv_patient_addr);
            tvDateTime=itemView.findViewById(R.id.tv_date_time);
            ibtnMore=itemView.findViewById(R.id.ibtn_more);

            if(model.getFirstName()!=null && model.getLastName()!=null){
                String fName  = model.getFirstName();
                fName = fName.substring(0,1).toUpperCase() + fName.substring(1).toLowerCase();
                String lName  = model.getLastName();
                lName = lName.substring(0,1).toUpperCase() + lName.substring(1).toLowerCase();
                tvPatientName.setText(fName);
                tvPatientName.append(" ");
                tvPatientName.append(lName);

            }

            if(model.getAppointmentDate()!=null && model.getStartTime()!=null && model.getEndTime()!=null){
                String appointmentDate  = model.getAppointmentDate();
                String startTime  = model.getStartTime();
                String endTime  = model.getEndTime();
                tvDateTime.setText(appointmentDate);
                tvDateTime.append("\n");
//              tvDateTime.append(startTime+" - "+endTime);
                tvDateTime.append("1:00 AM"+" - "+"1:30 AM"); // temp??
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_appointment_history, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PastAppointment item = items.get(position);
        holder.bind(item, onItemClickListener);
    }
    public void  setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemMoreClick(int position);
    }

}