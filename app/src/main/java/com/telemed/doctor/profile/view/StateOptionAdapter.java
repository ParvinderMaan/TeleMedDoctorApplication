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
import com.telemed.doctor.profile.model.State;

public class StateOptionAdapter extends BaseRecyclerAdapter<State, OnItemClickListener<State>, StateOptionAdapter.OptionViewModel> {


    StateOptionAdapter() {
        super();
    }


    @NonNull
    @Override
    public StateOptionAdapter.OptionViewModel onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_choose_option, parent, false);
        return new StateOptionAdapter.OptionViewModel(view);
    }

    static class OptionViewModel extends BaseViewHolder<State, OnItemClickListener<State>> {

        private final TextView tvTitle;

        public OptionViewModel(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);

        }


        @Override
        public void onBind(State item, @Nullable OnItemClickListener<State> listener) {
            tvTitle.setText(item.getName());
            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClicked(item));
            }
        }
    }

}
