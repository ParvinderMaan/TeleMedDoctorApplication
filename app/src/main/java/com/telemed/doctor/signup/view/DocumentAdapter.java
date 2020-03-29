package com.telemed.doctor.signup.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telemed.doctor.R;
import com.telemed.doctor.signup.model.DocumentInfo;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
    private  final String TAG = DocumentAdapter.class.getSimpleName();
    private  final int MAX_COUNT=5;

    private List<DocumentInfo> list;
    private OnItemClickListener onItemClickListener;

    public DocumentAdapter() {
        list = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener=listener;
    }



    public void update(int updateIndex, DocumentInfo newValue) {
        list.set(updateIndex, newValue);
        notifyItemChanged(updateIndex);

    }

    public void updateSingle(int updateIndex, Integer id) {
        DocumentInfo info=list.get(updateIndex);
        info.setId(id);
        info.setStatus(3);
        list.set(updateIndex, info);
        notifyItemChanged(updateIndex);
    }

    public void removeView(int pos) {
            list.remove(pos);
            notifyItemRemoved(pos);

            if(list.size()==0){
                addView(new DocumentInfo());
            }

    }
    public void addView(DocumentInfo info) {
        if(list.size()< MAX_COUNT){
            list.add(info);
            notifyItemInserted(list.size() - 1);
        }
    }

    public void addAll(List<DocumentInfo> documentList) {
        for(int i=0;i<documentList.size();i++){
           documentList.get(i).setStatus(3); // means already uploaded...
        }

         for(int i=0;i<documentList.size();i++){
             addView(documentList.get(i));
         }

    }

    public void update(int updateIndex, String expiryDate) {
        DocumentInfo x = list.get(updateIndex);
        x.setExpiryDate(expiryDate);
        x.setStatus(2);
        list.set(updateIndex, x);
        notifyItemChanged(updateIndex);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton ibtnAction;
        private final TextView tvDocName,tvDocExpiryDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDocName = itemView.findViewById(R.id.tv_doc_name);
            ibtnAction = itemView.findViewById(R.id.ibtn_action);
            tvDocExpiryDate = itemView.findViewById(R.id.tv_doc_expiry_date);

        }

        public void bind(final DocumentInfo model, final OnItemClickListener listener) {
            tvDocName.setOnClickListener(v -> {
                if(listener!=null)listener.onItemTextTitleClick(getAdapterPosition(), model); });

            ibtnAction.setOnClickListener(v -> {
                if(listener!=null)listener.onItemActionUploadClick(getAdapterPosition(), model); });

            tvDocExpiryDate.setOnClickListener(v -> {
                if(listener!=null)listener.onItemDatePickerActionClick(getAdapterPosition(), model); });

            itemView.setOnLongClickListener(v -> {
                if(listener!=null) listener.onItemActionDeleteClick(getAdapterPosition(), model);
                return true;
            });


            switch (model.getStatus()){

                case 0:
                    ibtnAction.setVisibility(View.INVISIBLE); // no icon
                    tvDocName.setClickable(true);
                    tvDocExpiryDate.setClickable(false);
                    break;
                case 1:
                    ibtnAction.setVisibility(View.INVISIBLE); // no icon
                    tvDocName.setClickable(true);
                    tvDocExpiryDate.setClickable(true);
                    tvDocExpiryDate.setText(model.getExpiryDate()!=null?model.getExpiryDate():"");
                    tvDocName.setText(model.getDocumentName()!=null?model.getDocumentName():"File added");
                    break;
                case 2:
                    ibtnAction.setVisibility(View.VISIBLE);
                    tvDocName.setClickable(true);
                    tvDocExpiryDate.setClickable(true);
                    ibtnAction.setImageResource(R.drawable.ic_upload); // about to upload icon
                    ibtnAction.setClickable(true);
                    tvDocExpiryDate.setText(model.getExpiryDate()!=null?model.getExpiryDate():"");
                    tvDocName.setText(model.getDocumentName()!=null?model.getDocumentName():"File added");
                    break;

                case 3:
                    ibtnAction.setVisibility(View.VISIBLE);
                    tvDocName.setClickable(false);
                    tvDocExpiryDate.setClickable(false);
                    ibtnAction.setClickable(false);
                    ibtnAction.setImageResource(R.drawable.ic_success); // about to removeView icon
                    tvDocExpiryDate.setText(model.getExpiryDate()!=null?model.getExpiryDate():"");
                    tvDocName.setText(model.getDocumentName()!=null?model.getDocumentName():"File uploaded");
                    break;
            }



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_add_file, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DocumentInfo item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemTextTitleClick(int position, DocumentInfo info);
        void onItemActionUploadClick(int position, DocumentInfo info);
        void onItemActionDeleteClick(int position, DocumentInfo info);
        void onItemDatePickerActionClick(int adapterPosition, DocumentInfo model);
    }

}