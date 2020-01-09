package com.telemed.doctor.consult;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.consult.model.Appointment;
import com.telemed.doctor.network.WebUrl;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentUpcomingAdapter extends RecyclerView.Adapter<AppointmentUpcomingAdapter.ViewHolder> {
    private static final String TAG = AppointmentUpcomingAdapter.class.getSimpleName();
    private List<Appointment> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public AppointmentUpcomingAdapter() {
//        items.add("Abc");
//        items.add("Abc");
//        items.add("Abc");
//        items.add("Abc");
//        items.add("Abc");
    }

    public void addAll(List<Appointment> lstOfAppointments) {
         items.clear();
         items.addAll(lstOfAppointments);
         notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder  {
        private CircleImageView civProfilePic;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvDayOfWeek;
        private TextView tvCallSpan;
        private Button btnEstimateTime;
        private final Button btnMore;

        public ViewHolder(View itemView) {
            super(itemView);
            civProfilePic=itemView.findViewById(R.id.civ_profile_pic);
            tvName=itemView.findViewById(R.id.tv_name);
            tvAddress=itemView.findViewById(R.id.tv_address);
            tvDayOfWeek=itemView.findViewById(R.id.tv_day_of_week);
            tvCallSpan=itemView.findViewById(R.id.tv_call_span);
            btnEstimateTime=itemView.findViewById(R.id.btn_estimate_time);
            btnMore = itemView.findViewById(R.id.btn_more);


        }

        public void bind(final Appointment model, final OnItemClickListener listener) {

            if(model.getProfilePic()!=null && !model.getProfilePic().isEmpty()){
                Picasso.get().load(WebUrl.IMAGE_URL+model.getProfilePic())
                        .placeholder(R.drawable.img_avatar)
                        .error(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civProfilePic);
            }

            if(model.getFirstName()!=null && model.getLastName()!=null){
                String fName  = model.getFirstName();
                fName = fName.substring(0,1).toUpperCase() + fName.substring(1).toLowerCase();
                String lName  = model.getLastName();
                lName = lName.substring(0,1).toUpperCase() + lName.substring(1).toLowerCase();
                tvName.setText(fName);
                tvName.append(" ");
                tvName.append(lName);

            }

//          tvAddress=itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(v -> {
           //     listener.onItemClick(getAdapterPosition(), model);

            });
            btnMore.setOnClickListener(v -> {
                listener.onItemClickMore("",1);

            });

            btnEstimateTime.setOnClickListener(v ->{
                listener.onItemClick(getAdapterPosition(), model);
            });

        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_appointment_upcoming, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appointment item = items.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Appointment model);
        void onItemClickMore(String tag, int pos);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}