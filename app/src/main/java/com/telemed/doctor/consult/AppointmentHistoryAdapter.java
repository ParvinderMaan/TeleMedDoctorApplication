package com.telemed.doctor.consult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryAdapter.ViewHolder> {

    private static final String TAG = AppointmentHistoryAdapter.class.getSimpleName();

    private List<String> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public AppointmentHistoryAdapter() {
        this.list.add("");
        this.list.add("");
        this.list.add("");
        this.list.add("");
        this.list.add("");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton ibtnMore;
        public ViewHolder(View itemView) {
            super(itemView);
            ibtnMore=itemView.findViewById(R.id.ibtn_more);


        }

        public void bind(final String model, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   listener.onItemClickDelete(getLayoutPosition());

                }
            });


            ibtnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                       listener.onItemMoreClick(getLayoutPosition());



                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_appointment_history, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = list.get(position);

        //Todo: Setup viewholder for item
        holder.bind(item, onItemClickListener);
    }
    public void  setOnItemClickListener(OnItemClickListener onItemClickListener){

        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemMoreClick(int position);
    }

}