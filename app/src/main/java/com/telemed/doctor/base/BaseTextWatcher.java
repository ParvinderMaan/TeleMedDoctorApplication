package com.telemed.doctor.base;

import android.text.Editable;
import android.text.TextWatcher;
/**
 * @author Pmaan on 4/6/18.
 *         Copyright © All rights reserved.
 */
public abstract class BaseTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }   // not used ....

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
               onTextChanged(start,before,count,s);
    }

    @Override
    public void afterTextChanged(Editable s) { }    // not used ....

    public abstract void onTextChanged(int start, int before, int count, CharSequence s);
}
