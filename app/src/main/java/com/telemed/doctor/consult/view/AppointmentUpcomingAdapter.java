package com.telemed.doctor.consult.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.consult.model.UpcomingAppointment;
import com.telemed.doctor.network.WebUrl;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentUpcomingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    public static final int ITEM = 1;
    public static final int FOOTER = 2;
    private boolean isFooterAdded = false;
    private FooterViewHolder footerViewHolder;
    //----------------------------------------------
    private List<UpcomingAppointment> items;
    private OnItemClickListener onItemClickListener;
    private OnReloadClickListener onReloadClickListener;



    public AppointmentUpcomingAdapter() {
        items = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case HEADER:
                viewHolder = createHeaderViewHolder(parent);
                break;
            case ITEM:
                viewHolder = createItemViewHolder(parent);
                break;
            case FOOTER:
                viewHolder = createFooterViewHolder(parent);
                break;
            default:
                break;
        }

        return viewHolder;
    }


    private RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    private RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appointment_upcoming, parent, false);
        return new ItemViewHolder(view);
    }

    private RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_footer, parent, false);
        return new FooterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        switch (getItemViewType(pos)) {
            case HEADER:
               bindHeaderViewHolder(holder);
                break;
            case ITEM:
                bindItemViewHolder(holder, pos);
                break;
            case FOOTER:
             bindFooterViewHolder(holder);
        }
    }

    private void bindHeaderViewHolder(RecyclerView.ViewHolder holder) { }

    private void bindItemViewHolder(RecyclerView.ViewHolder holder, int pos) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        UpcomingAppointment item = items.get(pos);
        viewHolder.bind(item, onItemClickListener);
    }

    private void bindFooterViewHolder(RecyclerView.ViewHolder holder) {
        final FooterViewHolder viewHolder = (FooterViewHolder) holder;
        footerViewHolder=viewHolder;
        viewHolder.ibtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onReloadClickListener != null){
                    onReloadClickListener.onReloadClick();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearAll() {
        items.clear();
        notifyDataSetChanged();
    }

    // inner interfaces
    public interface OnItemClickListener {
        void onItemClick(int position, UpcomingAppointment model);
        void onItemClickMedicalRecord(UpcomingAppointment model, int pos);

    }
    public interface OnReloadClickListener {
        void onReloadClick();
    }


    // set listeners
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        this.onReloadClickListener = onReloadClickListener;
    }


    // view holders inner classes
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civProfilePic;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvDayOfWeek;
        private TextView tvCallSpan;
        private TextView tvEstimateTime;
        private TextView tvMedicalRecord;

        public ItemViewHolder(View itemView) {
            super(itemView);
            civProfilePic = itemView.findViewById(R.id.civ_profile_pic);
            tvName = itemView.findViewById(R.id.tv_patient_name);
            tvAddress = itemView.findViewById(R.id.tv_patient_addr);
            tvDayOfWeek = itemView.findViewById(R.id.tv_day_of_week);
            tvCallSpan = itemView.findViewById(R.id.tv_call_span);
            tvEstimateTime = itemView.findViewById(R.id.tv_estimate_time);
            tvMedicalRecord = itemView.findViewById(R.id.tv_medical_record);


        }

        public void bind(final UpcomingAppointment model, final OnItemClickListener listener) {

            if (model.getProfilePic() != null && !model.getProfilePic().isEmpty()) {
                Picasso.get().load(WebUrl.IMAGE_URL + model.getProfilePic())
                        .placeholder(R.drawable.img_avatar)
                        .error(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civProfilePic);
            }else {
                Picasso.get().load(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civProfilePic);
            }

            if (model.getFirstName() != null && model.getLastName() != null) {
                String fName = model.getFirstName();
                fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
                String lName = model.getLastName();
                lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
                tvName.setText(fName);
                tvName.append(" ");
                tvName.append(lName);

            }

            if (model.getStateName() != null && model.getCountryName() != null) {
                String stateName = model.getStateName();
                stateName = stateName.substring(0, 1).toUpperCase() + stateName.substring(1).toLowerCase();
                String countryName = model.getCountryName();
                countryName = countryName.substring(0, 1).toUpperCase() + countryName.substring(1).toLowerCase();
                tvAddress.setText(stateName);
                tvAddress.append(",");
                tvAddress.append(countryName);
            }

            if (model.getStartTime() != null && model.getEndTime() != null) {
                String startTime = model.getStartTime();
                String endTime = model.getEndTime();
                tvCallSpan.setText(startTime);
                tvCallSpan.append("-");
                tvCallSpan.append(endTime);
            }

            tvEstimateTime.setOnClickListener(v -> {
                if(tvEstimateTime.getText().toString().equals("Join Call"))
                     listener.onItemClick(getAdapterPosition(), model);
            });
            tvMedicalRecord.setOnClickListener(v -> {
                listener.onItemClickMedicalRecord(model, getAdapterPosition());
            });



            tvDayOfWeek.setText(model.getDayOfWeek()!=null?""+model.getDayOfWeek().substring(0,3).toUpperCase():"");

            if (model.getEstimatedTimeInMinutes() != null ) {
                tvEstimateTime.setText("Estimate\n");
                tvEstimateTime.append(""+model.getEstimatedTimeInMinutes()+" ");
                tvEstimateTime.append("minutes");
            }
 //-----------------------------------------------------------------------------------------------------------------
            int totMinutes= model.getEstimatedTimeInMinutes();
            if(totMinutes>0){  // +ve
                if(totMinutes>=1440){  // days
                    int totDays=totMinutes/1440;
                    tvEstimateTime.setText("Estimate\n"+totDays+" days");
                }else {
                    if(totMinutes>=60){      // hours
                        int totHours=totMinutes/60;
                        tvEstimateTime.setText("Estimate\n"+totHours+" hours");
                    }else {            // mins
                        tvEstimateTime.setText("Estimate\n"+totMinutes+" minutes");
                    }
                }

            }else if(totMinutes==0){    // =0
                tvEstimateTime.setText("Join Call");
                tvEstimateTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }else {                   // -ve
                tvEstimateTime.setText("Join Call");
                tvEstimateTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

//-----------------------------------------------------------------------------------------------------------------
 }

//    public static String getWeekDay(String date) throws ParseException {
//              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
//              Date xxx= dateFormat.parse(date);
//              SimpleDateFormat simpleDateformat = new SimpleDateFormat("E", Locale.US); // the day of the week abbreviated
//              return simpleDateformat.format(xxx);
//    }


    }
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlError;
        private FrameLayout flLoading;
        private ImageButton ibtnReload;
        private TextView tvErrorMsg;

        // region Constructors
        public FooterViewHolder(View view) {
            super(view);
            rlError=view.findViewById(R.id.rl_error);
            flLoading=view.findViewById(R.id.fl_loading);
            ibtnReload=view.findViewById(R.id.ibtn_reload);
            tvErrorMsg=view.findViewById(R.id.tv_error_msg);

        }
        // endregion

    }

    // region Inner Classes
    public enum FooterType {
        LOAD_MORE,
        ERROR
    }



    // helpers
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void remove(UpcomingAppointment item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }
    private UpcomingAppointment getItem(int position) {
        return items.get(position);
    }
    private boolean isLastPosition(int position) {
        return (position == items.size() - 1);
    }





    public void updateFooter(FooterType footerType){
        switch (footerType) {
            case LOAD_MORE:
                displayLoadMoreFooter();
                break;
            case ERROR:
                displayErrorFooter();
                break;
            default:
                break;
        }
    }

    public void addFooter() {
        isFooterAdded = true;
        add(new UpcomingAppointment());

    }
    public void removeFooter() {
        isFooterAdded = false;
        int position = items.size() - 1;
        UpcomingAppointment item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }


    private void displayLoadMoreFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.rlError.setVisibility(View.GONE);
            footerViewHolder.flLoading.setVisibility(View.VISIBLE);
        }
    }


    private void displayErrorFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.flLoading.setVisibility(View.GONE);
            footerViewHolder.rlError.setVisibility(View.VISIBLE);
        }
    }



    public void add(UpcomingAppointment item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<UpcomingAppointment> items) {
        for (UpcomingAppointment item : items) {
            add(item);
        }
    }

}