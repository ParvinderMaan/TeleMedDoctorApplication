package com.telemed.doctor.base;


import com.telemed.doctor.ErrorHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseCallback<T>  implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onSuccess(response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable error) {
           String errorMsg = ErrorHandler.reportError(error);
           onFailure(call,errorMsg);
    }

    public abstract void onSuccess(Response<T> response) ;
    public abstract void onFailure(Call<T> call,String errorMsg) ;

}
