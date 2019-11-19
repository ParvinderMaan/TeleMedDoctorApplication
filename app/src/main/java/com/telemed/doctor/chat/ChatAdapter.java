package com.telemed.doctor.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.helper.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // region Constants
    private static final int ITEM_SENDER = 1;
    private static final int ITEM_RECEIVER = 2;
    private static String userIdYour;

    // region Member Variables
    private List<ChatModel> items;

    private OnItemClickListener onItemClickListener;


    public ChatAdapter() {
        items = new ArrayList<>();
    }

    public static void setUser(String userIdYour) {
        ChatAdapter.userIdYour = userIdYour;
    }


    // region Interfaces
    public interface OnItemClickListener {
        void onItemClick(int position, View view, ChatModel chat);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_SENDER:
                viewHolder = createSenderViewHolder(parent);
                break;
            case ITEM_RECEIVER:
                viewHolder = createReceiverViewHolder(parent);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_SENDER:
                bindSenderViewHolder(viewHolder, position);
                break;
            case ITEM_RECEIVER:
                bindReceiverViewHolder(viewHolder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // region Abstract Methods
    private RecyclerView.ViewHolder createSenderViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sender, parent, false);
        return new SenderViewHolder(v);
    }

    private RecyclerView.ViewHolder createReceiverViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_receiver, parent, false);
        return new ReceiverViewHolder(v);
    }


    private void bindSenderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SenderViewHolder holder = (SenderViewHolder) viewHolder;
        final ChatModel result = getItem(position);
        if (result != null) {
            holder.bind(result, onItemClickListener);
        }



    }

    private void bindReceiverViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ReceiverViewHolder holder = (ReceiverViewHolder) viewHolder;

        final ChatModel result = getItem(position);
        if (result != null) {
            holder.bind(result, onItemClickListener);
        }

    }


    // region Helper Methods
    public ChatModel getItem(int position) {
        return items.get(position);
    }

    public void add(ChatModel item) {
//        items.add(item);
//        int pos = getItemCount() - 1;             // previous code
//        notifyItemInserted(pos);

        items.add(0,item);
        // new  code
        notifyItemInserted(0);

    }



    public void update(ChatModel item) {
//        Integer pos = keyAtPos.get(item.getKey());
//        items.set(pos, item);
//        notifyItemChanged(pos);
    }



    public void clear() {
        if (!isEmpty()) items.clear();
//       notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private boolean getViewType(int pos) {
        return getItem(pos).getSenderId().equals(userIdYour);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemViewType(int position) {
        return (getViewType(position)) ? ITEM_SENDER : ITEM_RECEIVER;
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage, tvDate, tvSeen;


        SenderViewHolder(View v) {
            super(v);
            tvMessage = v.findViewById(R.id.tv_out_message);
            tvDate = v.findViewById(R.id.tv_out_seen);
//            tvSeen = v.findViewById(R.id.tv_out_seen);
        }


        private void bind(final ChatModel result, OnItemClickListener onItemClickListener) {
            tvMessage.setText(result.getMessage());
            // static
//            Date currentDate = new Date((result.getTimeStamp()*1000));
//            DateFormat df = new SimpleDateFormat("HH:mm a", Locale.US);
            tvMessage.setText(result.getMessage());
            tvDate.setText(TimeUtil.getTime(result.getTimeStamp() * 1000));
//            tvSeen.setVisibility(result.isSeen() ? View.VISIBLE : View.INVISIBLE);


            itemView.setOnLongClickListener(v -> {

                if (result.isSelected()) {
                    itemView.setSelected(false);
                    result.setSelected(false);
                } else {
                    itemView.setSelected(true);
                    result.setSelected(true);
                }

                onItemClickListener.onItemClick(getAdapterPosition(), itemView,result);
                return true;
            });


            itemView.setSelected(result.isSelected());

        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage, tvDate;

        ReceiverViewHolder(View v) {
            super(v);
            tvMessage = v.findViewById(R.id.tv_in_message);
            tvDate = v.findViewById(R.id.tv_in_date);
        }


        private void bind(final ChatModel result, OnItemClickListener onItemClickListener) {
//            Date currentDate = new Date((result.getTimeStamp()*1000));
//            DateFormat df = new SimpleDateFormat("HH:mm a", Locale.US);
            tvMessage.setText(result.getMessage());
            tvDate.setText(TimeUtil.getTime((result.getTimeStamp() * 1000)));

            itemView.setOnLongClickListener(v -> {

                if (result.isSelected()) {
                    itemView.setSelected(false);
                    result.setSelected(false);
                } else {
                    itemView.setSelected(true);
                    result.setSelected(true);
                }

                onItemClickListener.onItemClick(getAdapterPosition(), itemView,result);

                return true;
            });


            itemView.setSelected(result.isSelected());
        }

    }




}


// endregion