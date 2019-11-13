package com.telemed.doctor.appointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class PatientGalleryAdapter extends RecyclerView.Adapter<PatientGalleryAdapter.ViewHolder> {
    private static final String TAG = PatientGalleryAdapter.class.getSimpleName();

    private List<String> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public PatientGalleryAdapter() {

        this.list.add("");
        this.list.add("");
        this.list.add("");
        this.list.add("");
        this.list.add("");

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);


        }

        void bind(final String model, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_gallery, parent, false);

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