package com.telemed.doctor.profile.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.telemed.doctor.ErrorHandler;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WebService;
import com.telemed.doctor.password.model.ResendOtpResponse;
import com.telemed.doctor.password.model.VerificationResponse;
import com.telemed.doctor.profile.model.Country;
import com.telemed.doctor.profile.model.Gender;
import com.telemed.doctor.profile.model.Language;
import com.telemed.doctor.profile.model.OptionResponse;
import com.telemed.doctor.profile.model.Speciliaty;
import com.telemed.doctor.profile.model.State;
import com.telemed.doctor.profile.model.StateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telemed.doctor.network.Status.FAILURE;
import static com.telemed.doctor.network.Status.SUCCESS;

public class ChooseOptionViewModel extends AndroidViewModel {
    private final String TAG=ChooseOptionViewModel.class.getSimpleName();
    //@use Dagger instead
    private final WebService mWebService;
    private MutableLiveData<ApiResponse<OptionResponse>> resultantResendOtp;
    private MutableLiveData<List<Gender>> lstOfGender;
    private MutableLiveData<List<Language>> listOfLanguage;
    private MutableLiveData<List<Speciliaty>> listOfSpeciality;
    private MutableLiveData<List<State>> listOfState;



    private MutableLiveData<List<Country>> listOfCountry;
    private MutableLiveData<Boolean> isLoading;
    private Call<OptionResponse> xXx;


    public ChooseOptionViewModel(@NonNull Application application) {
        super(application);
        mWebService = ((TeleMedApplication) application).getRetrofitInstance();
        lstOfGender= new MutableLiveData<>();
        listOfLanguage= new MutableLiveData<>() ;
        listOfSpeciality = new MutableLiveData<>();
        listOfCountry = new MutableLiveData<>();
        listOfState= new MutableLiveData<>();
        isLoading=new MutableLiveData<>();
    }

    public void fetchDoctorDrills(){
        if(xXx !=null){
            Log.e(TAG,"no api call");
            return;
        }


         isLoading.setValue(true);
         xXx = mWebService.fetchDrillInfo();
         xXx.enqueue(new Callback<OptionResponse>() {
        @Override
        public void onResponse(@NonNull Call<OptionResponse> call, @NonNull Response<OptionResponse> response) {
            isLoading.setValue(false);

            if (response.isSuccessful() && response.body()!=null) {
                OptionResponse result = response.body();
                Log.e(TAG,result.toString());
                if(result.getStatus()){
                    lstOfGender.setValue(result.getData().getGender());
                    listOfLanguage.setValue(result.getData().getLanguages());
                    listOfSpeciality.setValue(result.getData().getSpeciliaties());
                    listOfCountry.setValue(result.getData().getCountries());
                }else {
//                    lstOfGender.setValue(null);
//                    listOfLanguage.setValue(null);
//                    listOfSpeciality.setValue(null);
                }
            }



        }

        @Override
        public void onFailure(@NonNull Call<OptionResponse> call, @NonNull Throwable error) {
            isLoading.setValue(false);
            String errorMsg = ErrorHandler.reportError(error);
//            lstOfGender.setValue(null);
//            listOfLanguage.setValue(null);
//            listOfSpeciality.setValue(null);
        }
    });

    }


    public MutableLiveData<Boolean> getProgress() {
        return isLoading;
    }

    public MutableLiveData<List<Gender>> getGender() {
        return lstOfGender;
    }

    public MutableLiveData<List<Language>> getLanguage() {
        return listOfLanguage;
    }

    public MutableLiveData<List<Speciliaty>> getSpeciality() {
        return listOfSpeciality;
    }

    public MutableLiveData<List<Country>> getCountries() {
        return listOfCountry;
    }

    public LiveData<List<State>> getState() {
        return listOfState;

    }


    public void fetchStateFromCountry(String countryId){
        mWebService.fetchStateInfo(countryId).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                if (response.isSuccessful() && response.body()!=null) {
                    StateResponse result = response.body();
                    Log.e(TAG,result.toString());
                    if(result.getStatus()){
                        listOfState.setValue(result.getData().getStates());

                    }else {
//                    lstOfGender.setValue(null);
//                    listOfLanguage.setValue(null);
//                    listOfSpeciality.setValue(null);
                    }
                }



            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }
        });

    }
}
