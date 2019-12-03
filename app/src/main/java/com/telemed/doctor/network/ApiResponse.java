package com.telemed.doctor.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.telemed.doctor.Post;

import static com.telemed.doctor.network.Status.ERROR;
import static com.telemed.doctor.network.Status.LOADING;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ApiResponse {

    public final Status status;
    public final Post data;
    public final Throwable error;

    public ApiResponse(Status status, @Nullable Post data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull Post data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }


}
