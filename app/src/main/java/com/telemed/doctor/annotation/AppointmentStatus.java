package com.telemed.doctor.annotation;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({AppointmentStatus.CONFIRM, AppointmentStatus.DENY, AppointmentStatus.CANCEL})
@Retention(RetentionPolicy.SOURCE)
public @interface AppointmentStatus {
    int CONFIRM = 3;
    int DENY = 4;
    int CANCEL = 5;
}