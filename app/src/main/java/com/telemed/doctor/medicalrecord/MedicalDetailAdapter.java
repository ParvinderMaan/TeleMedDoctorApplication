package com.telemed.doctor.medicalrecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.medicalrecord.model.MedicalDetailResponse;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailAdapter extends RecyclerView.Adapter<MedicalDetailAdapter.ViewHolder> {
    private static final String TAG = MedicalDetailAdapter.class.getSimpleName();
    private List<MedicalDetailResponse.MedicalHistory> items;
    private OnItemClickListener onItemClickListener;

    public MedicalDetailAdapter() {
        items=new ArrayList<>();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate,tvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tv_date);
            tvDesc=itemView.findViewById(R.id.tv_desc);
        }

        public void bind(final MedicalDetailResponse.MedicalHistory model) {
            tvDate.setText(model.getUpdatedOn()!=null?model.getUpdatedOn():"");
            tvDesc.setText(model.getDescription()!=null?model.getDescription():"");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medical_detail, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicalDetailResponse.MedicalHistory item = items.get(position);
        holder.bind(item);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public void add(MedicalDetailResponse.MedicalHistory item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<MedicalDetailResponse.MedicalHistory> items) {
        for (MedicalDetailResponse.MedicalHistory item : items) {
            add(item);
        }
    }

}