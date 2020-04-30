package com.telemed.doctor.document;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseRecyclerAdapter;
import com.telemed.doctor.base.BaseViewHolder;
import com.telemed.doctor.base.OnItemClickListener;
import com.telemed.doctor.document.model.Drug;

public class PrescriptionAdapter extends BaseRecyclerAdapter<Drug, OnItemClickListener<Drug>, PrescriptionAdapter.PrescriptionViewModel> {


    PrescriptionAdapter() {
        super();
    }


    @NonNull
    @Override
    public PrescriptionAdapter.PrescriptionViewModel onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_prescription, parent, false);
        return new PrescriptionAdapter.PrescriptionViewModel(view);
    }

    static class PrescriptionViewModel extends BaseViewHolder<Drug, OnItemClickListener<Drug>> {
        private final TextView tvTitle;

        public PrescriptionViewModel(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void onBind(Drug item, @Nullable OnItemClickListener<Drug> listener) {
            tvTitle.setText(item.getName());
        }
    }
}

