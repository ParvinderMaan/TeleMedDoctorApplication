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
import com.telemed.doctor.signup.model.DocInfo;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
    private  final String TAG = DocumentAdapter.class.getSimpleName();

    private List<DocInfo> list;
    private OnItemClickListener onItemClickListener;

    public DocumentAdapter() {
        list = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener=listener;
    }



    public void update(int updateIndex, DocInfo newValue) {
        list.set(updateIndex, newValue);
        notifyItemChanged(updateIndex);

    }

    public void updateSingle(int updateIndex, Integer id) {
        DocInfo info=list.get(updateIndex);
        info.setId(id);
        info.setStatus(2);
        info.setName("File Uploaded");
        list.set(updateIndex, info);
        notifyItemChanged(updateIndex);
    }

    public void removeView(int pos) {
            list.remove(pos);
            notifyItemRemoved(pos);

            if(list.size()==0){
                addView(new DocInfo());
            }

    }
    public void addView(DocInfo info) {
        if(list.size()<5){
            list.add(info);
            notifyItemInserted(list.size() - 1);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton ibtnAction;
        private final TextView tvDocName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDocName = itemView.findViewById(R.id.tv_doc_name);
            ibtnAction = itemView.findViewById(R.id.ibtn_action);
        }

        public void bind(final DocInfo model, final OnItemClickListener listener) {
            tvDocName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemTextClick(getLayoutPosition(),model);

                }
            });

            ibtnAction.setOnClickListener(v -> {
                if(model.getStatus()==1) {
                    listener.onItemActionClick(getLayoutPosition(), model, "UPLOAD");
                }
                if(model.getStatus()==2){
                    listener.onItemActionClick(getLayoutPosition(), model, "DELETE");
                }

            });


            switch (model.getStatus()){

                case 0:
                    ibtnAction.setVisibility(View.INVISIBLE); // no icon
                    break;
                case 1:
                    ibtnAction.setVisibility(View.VISIBLE);
                    ibtnAction.setImageResource(R.drawable.ic_upload); // about to upload icon
                    break;
                case 2:
                    ibtnAction.setVisibility(View.VISIBLE);
                    ibtnAction.setImageResource(R.drawable.ic_delete); // about to removeView icon
                    break;
            }

            if(model.getName()!=null ){
                tvDocName.setText(model.getName());
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
        DocInfo item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemTextClick(int position,DocInfo info);
        void onItemActionClick(int position,DocInfo info,String type);
    }

}