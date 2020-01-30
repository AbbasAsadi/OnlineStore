package com.example.onlinestore.network.interfaces;

import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.model.products.ProductBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WoocommerceService {
    @GET("products")
    Call<List<ProductBody>> getAllProduct(@Query("consumer_key") String consumerKey,
                                          @Query("consumer_secret") String consumerSecret,
                                          @Query("per_page") int perPage);

    @GET("products")
    Call<List<ProductBody>> getProductByOrder(@Query("consumer_key") String consumerKey,
                                              @Query("consumer_secret") String consumerSecret,
                                              @Query("per_page") int perPage,
                                              @Query("orderby") String orderBy);

    @GET("products/categories")
    Call<List<CategoryBody>> getAllCategories(@Query("consumer_key") String consumerKey,
                                              @Query("consumer_secret") String consumerSecret,
                                              @Query("per_page") int perPage);
}
