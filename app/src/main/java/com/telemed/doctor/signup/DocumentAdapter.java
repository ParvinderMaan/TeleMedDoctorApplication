package com.telemed.doctor.signup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.telemed.doctor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pmaan on 15/6/18.
 *         Copyright © All rights reserved.
 */

public class DocumentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MAX_IMAGE_LIMIT = 5;
    private static final int ITEM = 1;
    private static final int EXTRA_ITEM = 2;
    private boolean isAddItemAdded = false;
    private List<DocumentInfo> items;
    private OnItemClickListener onItemClickListener;


    public DocumentAdapter() {
        this.items = new ArrayList<DocumentInfo>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case ITEM:
                viewHolder = createItemViewHolder(parent);
                break;
            case EXTRA_ITEM:
                viewHolder = createAddItemViewHolder(parent);
                break;

        }

        return viewHolder;
    }

    private RecyclerView.ViewHolder createAddItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_add_document, parent, false);
        final ExtraItemViewHolder holder = new ExtraItemViewHolder(v);
        return holder;

    }

    private RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_document, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {


        switch (getItemViewType(pos)) {

            case ITEM:
                bindItemViewHolder(viewHolder, pos);
                break;
            case EXTRA_ITEM:
                bindAddItemViewHolder(viewHolder);
            default:
                break;
        }

    }

    private void bindAddItemViewHolder(RecyclerView.ViewHolder viewHolder) {
        ExtraItemViewHolder holder = (ExtraItemViewHolder) viewHolder;
        holder.bind(onItemClickListener);


    }

    private void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        DocumentInfo result = items.get(pos);
        if (result != null) {
            holder.bind(result,onItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // region Helper Methods
    public DocumentInfo getItem(int position) {
        return items.get(position);
    }

    public void add(DocumentInfo item) {
        items.add(item);
        int pos = getItemCount() - 1;
        notifyItemInserted(pos);
    }

    public void addAll(List<DocumentInfo> lstOfPhoto) {
        isAddItemAdded=false;
//        if(lstOfPhoto.isEmpty()){
//            addItemView();
//            return;
//        }
//        int pos = getItemCount();
//        items.addAll(pos, lstOfPhoto);
//        notifyItemRangeInserted(pos, lstOfPhoto.size());
        items.clear();

//        for(int i=0;i<16;i++){ // 16 items
        items.addAll(lstOfPhoto);
//        }
        notifyDataSetChanged();


                if (items.size() < MAX_IMAGE_LIMIT ) {
                    addItemView();
                }else{
                    removeItemView();
                }




    }

    public List<DocumentInfo> getItems() {
        return items;
    }



    public class ExtraItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvAdd;

        // region Constructors
        ExtraItemViewHolder(View view) {
            super(view);

            tvAdd = itemView.findViewById(R.id.tv_add);
        }
        public void bind(final OnItemClickListener onItemClickListener) {


            tvAdd.setOnClickListener(view -> {

//                removeItemView(); // idf exist !!

                onItemClickListener.onItemAddClick();





//
//                if (items.size() < MAX_IMAGE_LIMIT) {
//                    addItemView();
//                }else{
//                    removeItemView();
//                }

            });
        }


        // endregion
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;
        private ImageButton ibtnDelete;
        private ProgressBar pbBar;

        public ViewHolder(View v) {
            super(v);
            tvItem = v.findViewById(R.id.tv_item);
//            ibtnDelete = v.findViewById(R.id.ibtn_delete);
            pbBar = v.findViewById(R.id.pb_bar);

        }

        public void bind(DocumentInfo result, final OnItemClickListener onItemClickListener) {
            pbBar.setVisibility(View.VISIBLE);
            // onSuccess()  ----> pbBar.setVisibility(View.GONE);






//            itemView.setOnLongClickListener(view -> {
//                if (ibtnDelete.getVisibility() == View.VISIBLE) {
//                    ibtnDelete.setVisibility(View.INVISIBLE);
//                } else {
//                    ibtnDelete.setVisibility(View.VISIBLE);
//                }
//                return true;
//            });

//            ibtnDelete.setOnClickListener(view -> {
//                ibtnDelete.setVisibility(View.INVISIBLE);
//
//                onItemClickListener.onItemDeleteClick(getAdapterPosition(),result);
//
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemImageClick(getAdapterPosition(), result);
                }
            });




        }



    }

    public void remove(int position) {

        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }

//        if (items.size() < MAX_IMAGE_LIMIT) {
//            addItemView();
//        }else{
//            removeItemView();
//        }
    }

    public boolean isLastPosition(int position) {
        return (position == items.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isAddItemAdded) ? EXTRA_ITEM : ITEM;
    }

    public  void addItemView(){
        if(!isAddItemAdded){
            isAddItemAdded = true;
            add(new DocumentInfo());
        }
    }

    // endregion
    public void removeItemView() {
        if(isAddItemAdded) {
            isAddItemAdded = false;

            int position = items.size() - 1;
            DocumentInfo item = getItem(position);

            if (item != null) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }
    }



    public interface OnItemClickListener{
        void onItemAddClick();
        void onItemImageClick(int position,DocumentInfo result);
        void onItemDeleteClick(int adapterPosition, DocumentInfo result);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}