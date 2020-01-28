package com.example.onlinestore.network.interfaces;

import com.example.onlinestore.model.CategoriesItem;
import com.example.onlinestore.model.WoocommerceBody;
import com.example.onlinestore.model.categories.CategoryBody;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WoocommerceService {
    @GET("products")
    Call<List<WoocommerceBody>> getWoocommerceBody(@Query("consumer_key") String consumerKey ,
                                                   @Query("consumer_secret") String consumerSecret,
                                                   @Query("per_page") int perPage,
                                                   @Query("orderby") String orderBy);

    @GET("products/categories")
    Call<List<CategoryBody>> getAllCategories(@Query("consumer_key") String consumerKey ,
                                              @Query("consumer_secret") String consumerSecret,
                                              @Query("per_page") int perPage);
}
