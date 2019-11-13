package com.telemed.doctor.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class WeeklyScheduleAdapter extends RecyclerView.Adapter<WeeklyScheduleAdapter.ViewHolder> {

    private static final String TAG = WeeklyScheduleAdapter.class.getSimpleName();
    private List<String> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public WeeklyScheduleAdapter() {
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);


        }

        public void bind(final String model, final WeeklyScheduleAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @NonNull
    @Override
    public WeeklyScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_schedule, parent, false);
        return new WeeklyScheduleAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(WeeklyScheduleAdapter.ViewHolder holder, int position) {
        String item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(WeeklyScheduleAdapter.OnItemClickListener onItemClickListener){

        this.onItemClickListener = onItemClickListener;
    }




}