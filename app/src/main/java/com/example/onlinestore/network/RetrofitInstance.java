package com.example.onlinestore.network;

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
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
