package com.telemed.doctor.consult.view;

import android.content.Context;
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
import com.telemed.doctor.consult.model.PastAppointment;
import com.telemed.doctor.network.WebUrl;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    public static final int ITEM = 1;
    public static final int FOOTER = 2;
    private boolean isFooterAdded = false;
    private FooterViewHolder footerViewHolder;
    //----------------------------------------------
    private OnItemClickListener onItemClickListener;
    private OnReloadClickListener onReloadClickListener;
    private List<PastAppointment> items;

    public AppointmentHistoryAdapter() {
        items=new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }




    public void clearAll() {
        items.clear();
        notifyDataSetChanged();
    }

    // helpers
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void remove(PastAppointment item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }
    public PastAppointment getItem(int position) {
        return items.get(position);
    }
    public boolean isLastPosition(int position) {
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
        add(new PastAppointment());

    }
    public void removeFooter() {
        isFooterAdded = false;
        int position = items.size() - 1;
        PastAppointment item = getItem(position);

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



    public void add(PastAppointment item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<PastAppointment> items) {
        for (PastAppointment item : items) {
            add(item);
        }
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_appointment_history, parent, false);
        return new ItemViewHolder(view);
    }

    private RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_footer, parent, false);
        return new FooterViewHolder(v);
    }



    // region Inner Classes
    public enum FooterType {
        LOAD_MORE,
        ERROR
    }

    // bind viewholder
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
        PastAppointment item = items.get(pos);
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



    // inner interfaces
    public interface OnItemClickListener {
        void onItemClick(int position, PastAppointment model);
        void onItemClickMore(String tag, int pos);
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


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civPatientPic;
        private TextView tvPatientName, tvPatientAddr,tvDateTime;
        private ImageButton ibtnMore;
        public ItemViewHolder(View itemView) {
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

                //    listener.onItemMoreClick(getLayoutPosition());



                }
            });
            if(model.getProfilePic()!=null && !model.getProfilePic().isEmpty()){
                Picasso.get().load(WebUrl.IMAGE_URL+model.getProfilePic())
                        .placeholder(R.drawable.img_avatar)
                        .error(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civPatientPic);
            }else {
                Picasso.get().load(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civPatientPic);
            }




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


            if (model.getStateName() != null && model.getCountryName() != null) {
                String stateName = model.getStateName();
                stateName = stateName.substring(0, 1).toUpperCase() + stateName.substring(1).toLowerCase();
                String countryName = model.getCountryName();
                countryName = countryName.substring(0, 1).toUpperCase() + countryName.substring(1).toLowerCase();
                tvPatientAddr.setText(stateName);
                tvPatientAddr.append(",");
                tvPatientAddr.append(countryName);
            }


            if (model.getStartTime() != null && model.getEndTime() != null) {
                String startTime = model.getStartTime();
                String endTime = model.getEndTime();
                tvDateTime.setText(startTime);
                tvDateTime.append("-");
                tvDateTime.append(endTime);
            }
        }
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
}