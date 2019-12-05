package com.telemed.doctor.signin;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.telemed.doctor.Post;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ServiceGenerator;
import com.telemed.doctor.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.ERROR;
import static com.telemed.doctor.network.Status.SUCCESS;

public class SignInViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading=new MutableLiveData<>();
    private MutableLiveData<Boolean> showHomeActivity=new MutableLiveData<>();
//    private MutableLiveData<ApiResponse> mApiResponse=new MutableLiveData<>();



    public SignInViewModel(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public void setProgress(boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public void attemptSignIn() {
        setProgress(true);
//        WebService webService= ServiceGenerator.createService(WebService.class);
//        webService.fetchPostInfo().enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//
//                if(response.isSuccessful()){
//
//                    Post post = response.body();
//                    mApiResponse.showTopAlert(new ApiResponse(SUCCESS, post, null));
//
////                    makeToast(postList.toString());
//
//                }else {
//                    // data.showTopAlert(ApiResponse.error());
////                    makeToast("not succesfull");
//                    mApiResponse.showTopAlert(new ApiResponse(SUCCESS, response.body(), null));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
////                makeToast(" onFailure");
//                mApiResponse.showTopAlert(new ApiResponse(ERROR, null, t));
//
//            }
//        });



    }

    public MutableLiveData<Boolean> getShowHomeActivity() {
        return showHomeActivity;
    }








}
