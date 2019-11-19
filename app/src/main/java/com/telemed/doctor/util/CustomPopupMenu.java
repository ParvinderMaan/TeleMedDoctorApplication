package com.telemed.doctor.util;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.telemed.doctor.interfacor.OnPopUpMenuItemClickListener;

public  class CustomPopupMenu extends PopupMenu  {
    private OnPopUpMenuItemClickListener menuItemClickListener;

    public CustomPopupMenu(@NonNull Context context, @NonNull View anchor) {
        super(context, anchor);
    }
    public void  setOnPopUpMenuItemClickListener(OnPopUpMenuItemClickListener listener){
        this.menuItemClickListener = listener;
    }


     public void onMenuClick(int pos, String tag){
         menuItemClickListener.onMenuItemClick(pos,tag);
     }



}
