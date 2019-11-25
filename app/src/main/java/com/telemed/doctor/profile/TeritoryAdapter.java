package com.telemed.doctor.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class TeritoryAdapter extends RecyclerView.Adapter<TeritoryAdapter.ViewHolder> {

    private static final String TAG = TeritoryAdapter.class.getSimpleName();

    private List<String> list;
    private OnItemClickListener onItemClickListener;

    public TeritoryAdapter() {
        list=new ArrayList<>();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
       private TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_title);
        }

        public void bind(final String model, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model,getAdapterPosition());

                }
            });

            tvTitle.setText(model);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_teritory, parent, false);
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
        void onItemClick(String model, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener=listener;
    }

    public void setData(List<String> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}