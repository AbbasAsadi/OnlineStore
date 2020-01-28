package com.example.onlinestore.network.interfaces;

import com.example.onlinestore.model.WoocommerceBody;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface WoocommerceService {
    @GET(".")
    Call<List<WoocommerceBody>> getWoocommerceBody(@QueryMap Map<String , String> queries);

}
