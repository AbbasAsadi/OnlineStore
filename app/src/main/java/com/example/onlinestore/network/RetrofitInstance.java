package com.example.onlinestore.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static RetrofitInstance instance;
    private Retrofit mRetrofit;
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";


    public static final  String CONSUMER_KEY = "ck_552ea09e65326775023b1e8969bee5ab65a3be38";
    public static final String CONSUMER_SECRET = "cs_9d3a817430202486c59662290989d5b4c5c3c837";

    public static final String WOOCOMMERCE_REST_AUTHENTICATION_KEY = "?consumer_key=" + RetrofitInstance.CONSUMER_KEY + "&consumer_secret=" + RetrofitInstance.CONSUMER_SECRET ;

    public static RetrofitInstance getInstance() {
        if (instance == null) {
            instance = new RetrofitInstance();
        }

        return instance;
    }

    private RetrofitInstance() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
