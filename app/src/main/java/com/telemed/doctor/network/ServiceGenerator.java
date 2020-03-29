package com.telemed.doctor.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.telemed.doctor.BuildConfig;
import com.telemed.doctor.exception.NoConnectivityException;
import com.telemed.doctor.TeleMedApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Pmaan on 23/1/18.
 */

public class ServiceGenerator {
    // region Constants
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;  // 10MB
    // endregion

    private static Retrofit.Builder retrofitBuilder
            = new Retrofit.Builder()
//            .addConverterFactory(new WrapperConverterFactory(GsonConverterFactory.create()));
            .addConverterFactory(GsonConverterFactory.create());
    // .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static OkHttpClient defaultOkHttpClient
            = new OkHttpClient.Builder()
            .cache(getCache())
            .build();

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        return createService(serviceClass, baseUrl, null);
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, WebUrl.BASE_URL, null);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, Interceptor networkInterceptor) {
        OkHttpClient.Builder okHttpClientBuilder = defaultOkHttpClient.newBuilder();

        if (networkInterceptor != null) {
            okHttpClientBuilder.addNetworkInterceptor(networkInterceptor);
        }


        OkHttpClient modifiedOkHttpClient = okHttpClientBuilder
                .addInterceptor(getHttpLoggingInterceptor())
//               .addInterceptor(getBasicInterceptor())
                .addInterceptor(getNetworkInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofitBuilder.client(modifiedOkHttpClient);
        retrofitBuilder.baseUrl(baseUrl);
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

    /**
     * @return
     */
    private static Cache getCache() {

        Cache cache = null;
        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(TeleMedApplication.getCacheDirectory(), "http");
            cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        } catch (Exception e) {
            Log.e(e.toString(), "Unable to install disk cache.");
        }
        return cache;
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }


    public static Interceptor getBasicInterceptor() {
        return new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        .addHeader("parameter", "value")
                        .build();

                return chain.proceed(request);
            }
        };
    }

    public static Interceptor getNetworkInterceptor() {
        return new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                boolean isConnected = TeleMedApplication.getInstance().isNetAvail();
                if (!isConnected) {
                    throw new NoConnectivityException();
                    // Throwing our custom exception 'NoConnectivityException'
                }

                Request.Builder builder = chain.request().newBuilder();
                return chain.proceed(builder.build());
            }
        };
    }


}
