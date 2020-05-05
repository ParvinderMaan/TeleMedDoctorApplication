
package com.telemed.doctor.schedule.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.schedule.model.AllWeekSchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeekDayScheduleAdapter extends RecyclerView.Adapter<WeekDayScheduleAdapter.Viewholder> {
    private List<AllWeekSchedule> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;


    public WeekDayScheduleAdapter() {

    }


    public void setItems(List<AllWeekSchedule> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_week_days, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        AllWeekSchedule item = items.get(position);
        holder.bind(item, onItemClickListener);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deleteItem(int pos) {
        this.items.remove(pos);
        notifyItemRemoved(pos);


    }

    public void addItem(Map<Integer, AllWeekSchedule> itemMap) {


    }


    class Viewholder extends RecyclerView.ViewHolder {
        private ImageButton ibtnDelete;
        private TextView tvSun, tvMon, tvTue, tvWed, tvThu, tvFri, tvSat;
        private AppCompatTextView tvTime;

        Viewholder(View itemView) {
            super(itemView);
            tvSun = itemView.findViewById(R.id.tv_sun);
            tvMon = itemView.findViewById(R.id.tv_mon);
            tvTue = itemView.findViewById(R.id.tv_tue);
            tvWed = itemView.findViewById(R.id.tv_wed);
            tvThu = itemView.findViewById(R.id.tv_thu);
            tvFri = itemView.findViewById(R.id.tv_fri);
            tvSat = itemView.findViewById(R.id.tv_sat);
            tvTime = itemView.findViewById(R.id.tv_time);
            ibtnDelete = itemView.findViewById(R.id.ibtn_delete);

        }

        public void bind(AllWeekSchedule item, OnItemClickListener onItemClickListener) {

            tvTime.setText(item.getFromTime() != null && item.getToTime() != null ? item.getFromTime() + " - " + item.getToTime() : "");

            if (item.getWeekDays() != null && !item.getWeekDays().isEmpty()) {
                List<AllWeekSchedule.WeekDay> lstOfWeek = item.getWeekDays();

                for (AllWeekSchedule.WeekDay model : lstOfWeek) {
                    switch (model.getWeekDay()) {

                        case 1:
                            tvSun.setSelected(true);
                            tvSun.setTextColor(Color.WHITE);
                            break;
                        case 2:
                            tvMon.setSelected(true);
                            tvMon.setTextColor(Color.WHITE);
                            break;
                        case 3:
                            tvTue.setSelected(true);
                            tvTue.setTextColor(Color.WHITE);
                            break;
                        case 4:
                            tvWed.setSelected(true);
                            tvWed.setTextColor(Color.WHITE);
                            break;
                        case 5:
                            tvThu.setSelected(true);
                            tvThu.setTextColor(Color.WHITE);
                            break;
                        case 6:
                            tvFri.setSelected(true);
                            tvFri.setTextColor(Color.WHITE);
                            break;
                        case 7:
                            tvSat.setSelected(true);
                            tvSat.setTextColor(Color.WHITE);
                            break;

                    }
                }

            }


            ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickDelete(item, getAdapterPosition());
                    }
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClickDelete(AllWeekSchedule item, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
