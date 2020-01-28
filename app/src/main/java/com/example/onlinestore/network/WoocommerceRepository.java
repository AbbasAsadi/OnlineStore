package com.example.onlinestore.network;


import com.example.onlinestore.model.WoocommerceBody;
import com.example.onlinestore.network.interfaces.WoocommerceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class WoocommerceRepository {
    private static WoocommerceRepository sWoocommerceRepository;
    public static final String BASE_URL = "http://woocommerce.maktabsharif.ir/wp-json/wc/v3/products/";
    public static final String CONSUMER_KEY = "ck_552ea09e65326775023b1e8969bee5ab65a3be38";
    public static final String CONSUMER_SECRET = "cs_9d3a817430202486c59662290989d5b4c5c3c837";

    private List<WoocommerceBody> mRecentProducts;
    private List<WoocommerceBody> mPopularProducts;
    private List<WoocommerceBody> mRatedProducts;
    private List<WoocommerceBody> mSearchedProducts;
    private List<WoocommerceBody> mRelatedProducts;

    public static WoocommerceRepository getInstance() {
        if (sWoocommerceRepository == null)
            sWoocommerceRepository = new WoocommerceRepository();
        return sWoocommerceRepository;
    }

    private WoocommerceRepository() {
    }

    private Map<String , String> mQueries = new HashMap<String , String>() {
        {
            put("consumer_key" , CONSUMER_KEY);
            put("consumer_secret" , CONSUMER_SECRET);
        }
    };

    private WoocommerceService mWoocommerceService = RetrofitInstance.getInstance(BASE_URL)
            .getRetrofit()
            .create(WoocommerceService.class);


    public List<WoocommerceBody> getPopularProduct() throws IOException {
        mQueries.put("orderby" , "popularity");
        Call<List<WoocommerceBody>> call = mWoocommerceService.getWoocommerceBody(mQueries);
        return call.execute().body();
    }
    public List<WoocommerceBody> getRecentProduct() throws IOException {
        mQueries.put("orderby", "date");
        Call<List<WoocommerceBody>> call = mWoocommerceService.getWoocommerceBody(mQueries);
        return call.execute().body();
    }
    public List<WoocommerceBody> getRatedProduct() throws IOException {
        mQueries.put("orderby", "rating");
        Call<List<WoocommerceBody>> call = mWoocommerceService.getWoocommerceBody(mQueries);
        return call.execute().body();
    }

    public void setRecentProducts(List<WoocommerceBody> recentProducts) {
        mRecentProducts = new ArrayList<>();
        mRecentProducts = recentProducts;
    }

    public void setPopularProducts(List<WoocommerceBody> popularProducts) {
        mPopularProducts = new ArrayList<>();
        mPopularProducts = popularProducts;
    }

    public void setRatedProducts(List<WoocommerceBody> ratedProducts) {
        mRatedProducts = new ArrayList<>();
        mRatedProducts = ratedProducts;
    }
}
