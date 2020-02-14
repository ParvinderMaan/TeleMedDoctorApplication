package com.telemed.doctor.profile.view;

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
import com.telemed.doctor.signup.view.DocumentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileDocumentAdapter extends RecyclerView.Adapter<ProfileDocumentAdapter.ViewHolder> {
    private  final String TAG = ProfileDocumentAdapter.class.getSimpleName();
    private  final int MAX_COUNT=5;

    private List<DocumentInfo> list;
    private ProfileDocumentAdapter.OnItemClickListener onItemClickListener;

    public ProfileDocumentAdapter() {
        list = new ArrayList<>();
    }

    public void setOnItemClickListener(ProfileDocumentAdapter.OnItemClickListener listener) {
        onItemClickListener=listener;
    }



    public void update(int updateIndex, DocumentInfo newValue) {
        list.set(updateIndex, newValue);
        notifyItemChanged(updateIndex);

    }

    public void updateSingle(int updateIndex, Integer id) {
        DocumentInfo info=list.get(updateIndex);
        info.setId(id);
        info.setStatus(2);
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
            documentList.get(i).setStatus(2);
        }


        for(int i=0;i<documentList.size();i++){
            addView(documentList.get(i));
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

        public void bind(final DocumentInfo model, final ProfileDocumentAdapter.OnItemClickListener listener) {
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
                    tvDocName.setText("Add document or pdf");
                    break;
                case 1:
                    ibtnAction.setVisibility(View.VISIBLE);
                    ibtnAction.setImageResource(R.drawable.ic_upload); // about to upload icon
                    tvDocName.setText("File added");
                    break;
                case 2:
                    ibtnAction.setVisibility(View.VISIBLE);
                    ibtnAction.setImageResource(R.drawable.ic_delete); // about to removeView icon
                    tvDocName.setText("File uploaded");
                    tvDocName.setEnabled(false);  // to avoid fetching file
                    break;
            }

        }
    }

    @NonNull
    @Override
    public ProfileDocumentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_add_file, parent, false);
        ProfileDocumentAdapter.ViewHolder viewHolder = new ProfileDocumentAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ProfileDocumentAdapter.ViewHolder holder, int position) {
        DocumentInfo item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemTextClick(int position, DocumentInfo info);
        void onItemActionClick(int position, DocumentInfo info, String type);
    }

}