package com.telemed.doctor.medicalrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentMedicationAdapter extends RecyclerView.Adapter<CurrentMedicationAdapter.ViewHolder> {

    private static final String TAG = CurrentMedicationAdapter.class.getSimpleName();

    private List<String> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public CurrentMedicationAdapter() {
        this.list.add("");
        this.list.add("");
        this.list.add("");

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }

        public void bind(final String model, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_current_medication, parent, false);

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
    }

}