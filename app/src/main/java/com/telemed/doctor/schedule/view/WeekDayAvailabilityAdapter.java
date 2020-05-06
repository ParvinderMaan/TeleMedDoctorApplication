package com.telemed.doctor.schedule.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.schedule.model.WeekDayAvailabilityResponse;

import java.util.ArrayList;
import java.util.List;

public class WeekDayAvailabilityAdapter extends RecyclerView.Adapter<WeekDayAvailabilityAdapter.ViewHolder> {
    private static final String TAG = WeekDayAvailabilityAdapter.class.getSimpleName();
    private List<WeekDayAvailabilityResponse.AvailableTime> list;
    private WeekDayAvailabilityAdapter.OnItemClickListener onItemClickListener;

    public WeekDayAvailabilityAdapter() {
        this.list = new ArrayList<>();
    }


    public void setItems(List<WeekDayAvailabilityResponse.AvailableTime> lstOfAllDaySchedule) {
        this.list.clear();
        this.list.addAll(lstOfAllDaySchedule);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvStartTime,tvEndTime;
        private final ImageButton ibtnEdit,ibtnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStartTime=itemView.findViewById(R.id.tv_start_time);
            tvEndTime=itemView.findViewById(R.id.tv_end_time);
            ibtnEdit=itemView.findViewById(R.id.ibtn_edit);
            ibtnDelete=itemView.findViewById(R.id.ibtn_delete);
        }

        public void bind(final WeekDayAvailabilityResponse.AvailableTime model, final OnItemClickListener listener) {
            ibtnEdit.setOnClickListener(v -> listener.onItemEditClick(model,getAdapterPosition()));
            ibtnDelete.setOnClickListener(v -> listener.onItemDeleteClick(model,getAdapterPosition()));

            tvStartTime.setText(model.getTimeFrom());
            tvEndTime.setText(model.getTimeTo());
        }
    }

    @Override
    public WeekDayAvailabilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_day_availability, parent, false);
        WeekDayAvailabilityAdapter.ViewHolder viewHolder = new WeekDayAvailabilityAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(WeekDayAvailabilityAdapter.ViewHolder holder, int position) {
        WeekDayAvailabilityResponse.AvailableTime item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemEditClick(WeekDayAvailabilityResponse.AvailableTime model, int position);
        void onItemDeleteClick(WeekDayAvailabilityResponse.AvailableTime model, int position);

    }

    public void setOnItemClickListener(WeekDayAvailabilityAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}