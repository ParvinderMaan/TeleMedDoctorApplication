package com.telemed.doctor.notification.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.telemed.doctor.R;
import com.telemed.doctor.network.WebUrl;
import com.telemed.doctor.notification.model.NotificationModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER = 0;
    public static final int ITEM = 1;
    public static final int FOOTER = 2;
    private boolean isFooterAdded = false;
    private NotificationAdapter.FooterViewHolder footerViewHolder;
    //----------------------------------------------
    private NotificationAdapter.OnItemClickListener onItemClickListener;
    private NotificationAdapter.OnReloadClickListener onReloadClickListener;
    private List<NotificationModel> items;

    public NotificationAdapter() {
        items=new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }




    public void clearAll() {
        items.clear();
        notifyDataSetChanged();
    }

    // helpers
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void remove(NotificationModel item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }
    public NotificationModel getItem(int position) {
        return items.get(position);
    }
    public boolean isLastPosition(int position) {
        return (position == items.size() - 1);
    }





    public void updateFooter(NotificationAdapter.FooterType footerType){
        switch (footerType) {
            case LOAD_MORE:
                displayLoadMoreFooter();
                break;
            case ERROR:
                displayErrorFooter();
                break;
            default:
                break;
        }
    }

    public void addFooter() {
        isFooterAdded = true;
        add(new NotificationModel());

    }
    public void removeFooter() {
        isFooterAdded = false;
        int position = items.size() - 1;
        NotificationModel item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }


    private void displayLoadMoreFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.rlError.setVisibility(View.GONE);
            footerViewHolder.flLoading.setVisibility(View.VISIBLE);
        }
    }


    private void displayErrorFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.flLoading.setVisibility(View.GONE);
            footerViewHolder.rlError.setVisibility(View.VISIBLE);
        }
    }



    public void add(NotificationModel item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<NotificationModel> items) {
        for (NotificationModel item : items) {
            add(item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case HEADER:
                viewHolder = createHeaderViewHolder(parent);
                break;
            case ITEM:
                viewHolder = createItemViewHolder(parent);
                break;
            case FOOTER:
                viewHolder = createFooterViewHolder(parent);
                break;
            default:
                break;
        }

        return viewHolder;
    }

    private RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    private RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false);
        return new NotificationAdapter.ItemViewHolder(view);
    }

    private RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_footer, parent, false);
        return new NotificationAdapter.FooterViewHolder(v);
    }

    public void update(int index) {
        NotificationModel x = items.get(index);
        x.setIsRead(true);
        this.items.set(index,x);

    }


    // region Inner Classes
    public enum FooterType {
        LOAD_MORE,
        ERROR
    }

    // bind viewholder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        switch (getItemViewType(pos)) {
            case HEADER:
                bindHeaderViewHolder(holder);
                break;
            case ITEM:
                bindItemViewHolder(holder, pos);
                break;
            case FOOTER:
                bindFooterViewHolder(holder);
        }
    }
    private void bindHeaderViewHolder(RecyclerView.ViewHolder holder) { }
    private void bindItemViewHolder(RecyclerView.ViewHolder holder, int pos) {
        final NotificationAdapter.ItemViewHolder viewHolder = (NotificationAdapter.ItemViewHolder) holder;
        NotificationModel item = items.get(pos);
        viewHolder.bind(item, onItemClickListener);
    }
    private void bindFooterViewHolder(RecyclerView.ViewHolder holder) {
        final NotificationAdapter.FooterViewHolder viewHolder = (NotificationAdapter.FooterViewHolder) holder;
        footerViewHolder=viewHolder;
        viewHolder.ibtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onReloadClickListener != null){
                    onReloadClickListener.onReloadClick();
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }



    // inner interfaces
    public interface OnItemClickListener {
        void onItemClick(int position, NotificationModel model);

    }
    public interface OnReloadClickListener {
        void onReloadClick();
    }


    // set listeners
    public void setOnItemClickListener(NotificationAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnReloadClickListener(NotificationAdapter.OnReloadClickListener onReloadClickListener) {
        this.onReloadClickListener = onReloadClickListener;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civNotiImage;
        private TextView tvNotifMessage, tvTime;
        private ConstraintLayout clRoot;
        public ItemViewHolder(View itemView) {
            super(itemView);
            clRoot=itemView.findViewById(R.id.cl_root);
            civNotiImage=itemView.findViewById(R.id.iv_noti_image);
            tvNotifMessage=itemView.findViewById(R.id.tv_notif_message);
            tvTime=itemView.findViewById(R.id.tv_time);


        }

        public void bind(final NotificationModel model, final NotificationAdapter.OnItemClickListener listener) {

            clRoot.setBackgroundColor(model.getIsRead()? Color.parseColor("#Eb5966"):Color.parseColor("#40FFFFFF"));


            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition(),model));



            if(model.getFromUserProfilePic()!=null && !model.getFromUserProfilePic().isEmpty()){
                Picasso.get().load(WebUrl.IMAGE_URL+model.getFromUserProfilePic())
                        .placeholder(R.drawable.img_avatar)
                        .error(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civNotiImage);
            }else {
                Picasso.get().load(R.drawable.img_avatar)
                        .fit()
                        .centerCrop()
                        .into(civNotiImage);
            }




            if(model.getText()!=null ){
                String msg  = model.getText();
                String totMsg = msg.substring(0,1).toUpperCase() + msg.substring(1).toLowerCase();
                tvNotifMessage.setText(totMsg);
            }

            if (model.getTimeInMinutes()!=null ) {
                Integer remainingMinutes  = model.getTimeInMinutes();
                tvTime.setText(""+remainingMinutes +" "+"minutes ago");
            }

        }
    }
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlError;
        private FrameLayout flLoading;
        private ImageButton ibtnReload;
        private TextView tvErrorMsg;

        // region Constructors
        public FooterViewHolder(View view) {
            super(view);
            rlError=view.findViewById(R.id.rl_error);
            flLoading=view.findViewById(R.id.fl_loading);
            ibtnReload=view.findViewById(R.id.ibtn_reload);
            tvErrorMsg=view.findViewById(R.id.tv_error_msg);

        }
        // endregion

    }
}
