package com.telemed.doctor.consult;

import android.app.Activity;
import android.content.Context;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.util.CustomPopupMenu;
import com.telemed.doctor.interfacor.OnPopUpMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentUpcomingAdapter extends RecyclerView.Adapter<AppointmentUpcomingAdapter.ViewHolder> {

    private static final String TAG = AppointmentUpcomingAdapter.class.getSimpleName();
    private List<String> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Activity activity;
    private android.view.ActionMode actionMode;

    public AppointmentUpcomingAdapter(Activity activity) {
        this.activity = activity;
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");
        list.add("Abc");

    }


    static class ViewHolder extends RecyclerView.ViewHolder  {

        private final Button btnMore;


        public ViewHolder(View itemView) {
            super(itemView);
            btnMore = itemView.findViewById(R.id.btn_more);


        }

        public void bind(final String model, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                listener.onItemClick(getLayoutPosition());

            });
            btnMore.setOnClickListener(v -> {

                listener.onItemClickMore("",1);


            });




        }




    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_appointment_upcoming, parent, false);
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

        void onItemClickMore(String tag, int pos);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }




}