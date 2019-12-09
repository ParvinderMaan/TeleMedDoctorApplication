package com.telemed.doctor.profile.view;

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
import com.telemed.doctor.profile.model.Gender;

public class GenderOptionAdapter extends BaseRecyclerAdapter<Gender, OnItemClickListener<Gender>, GenderOptionAdapter.OptionViewModel> {


    GenderOptionAdapter() {
        super();
    }


    @NonNull
    @Override
    public OptionViewModel onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_choose_option, parent, false);
        return new OptionViewModel(view);
    }

    static class OptionViewModel extends BaseViewHolder<Gender, OnItemClickListener<Gender>> {

        private final TextView tvTitle;

        public OptionViewModel(View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_title);
        }


        @Override
        public void onBind(Gender item, @Nullable OnItemClickListener<Gender> listener) {
            tvTitle.setText(item.getName());
            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClicked(item));
            }
        }
    }
}
