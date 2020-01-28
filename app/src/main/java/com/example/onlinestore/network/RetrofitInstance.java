package com.example.onlinestore.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static RetrofitInstance sInstance;
    private Retrofit mRetrofit;

    public static RetrofitInstance getInstance(String baseUrl) {
        if (sInstance == null) {
            sInstance = new RetrofitInstance(baseUrl);
        }
        return sInstance;
    }

    private RetrofitInstance(String baseUrl) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
