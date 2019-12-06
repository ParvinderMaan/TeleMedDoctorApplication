package com.telemed.doctor.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ApiResponse<T> {

    private final Status status;
    private final T data;
    private final String errorMsg;

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ApiResponse(Status status, @Nullable T data, @Nullable String errorMsg) {
        this.status = status;
        this.data = data;
        this.errorMsg = errorMsg;
    }






}
