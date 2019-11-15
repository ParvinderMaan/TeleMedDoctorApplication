package com.telemed.doctor.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.telemed.doctor.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {
    private ArrayList<String> list = new ArrayList<>();
    private Context context;
    public NotificationAdapter.OnItemClickListener onClickListener;

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_notification, parent, false);
        NotificationAdapter.Viewholder viewholder = new NotificationAdapter.Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.Viewholder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(View itemView) {
            super(itemView);

        }
    }

    public void setOnClickListener(NotificationAdapter.OnItemClickListener onClickListener){
        this.onClickListener = onClickListener;

    }
    public interface OnItemClickListener{
        void onItemClick();
    }
}
