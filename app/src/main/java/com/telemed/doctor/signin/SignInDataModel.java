package com.telemed.doctor.signin;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.Post;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.ServiceGenerator;
import com.telemed.doctor.network.Status;
import com.telemed.doctor.network.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.ERROR;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignInDataModel {

    MutableLiveData<ApiResponse> data=new MutableLiveData<>();
    public static SignInDataModel create() {
        return new SignInDataModel();
    }


    public MutableLiveData<ApiResponse> fetchPostFromServer(){


        WebService webService= ServiceGenerator.createService(WebService.class);
        webService.fetchPostInfo().enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()){

                    Post post = response.body();
                    data.setValue(new ApiResponse(SUCCESS, post, null));

//                    makeToast(postList.toString());

                }else {
                   // data.setValue(ApiResponse.error());
//                    makeToast("not succesfull");
                    data.setValue(new ApiResponse(SUCCESS, response.body(), null));
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
//                makeToast(" onFailure");
                data.postValue(new ApiResponse(ERROR, null, t));

            }
        });


        return data;
    }
}
