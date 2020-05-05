package com.telemed.doctor.schedule.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.telemed.doctor.R;
import com.telemed.doctor.schedule.model.DayAvailabilityModel;

import java.util.ArrayList;
import java.util.List;

public class DayAvailabilityAdapter extends RecyclerView.Adapter<DayAvailabilityAdapter.ViewHolder> {
    private static final String TAG = DayAvailabilityAdapter.class.getSimpleName();
    private List<DayAvailabilityModel> list;
    private OnItemClickListener onItemClickListener;

    public DayAvailabilityAdapter() {
        this.list = new ArrayList<>();
    }


    public void setItems(List<DayAvailabilityModel> lstOfAllDaySchedule) {
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

        public void bind(final DayAvailabilityModel model, final OnItemClickListener listener) {
            ibtnEdit.setOnClickListener(v -> listener.onItemEditClick(model,getAdapterPosition()));
            ibtnDelete.setOnClickListener(v -> listener.onItemDeleteClick(model,getAdapterPosition()));

            tvStartTime.setText(model.getTimeFrom());
            tvEndTime.setText(model.getTimeTo());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_day_availability, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DayAvailabilityModel item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemEditClick(DayAvailabilityModel model, int position);
        void onItemDeleteClick(DayAvailabilityModel model, int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}