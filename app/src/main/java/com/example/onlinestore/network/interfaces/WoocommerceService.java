package com.example.onlinestore.network.interfaces;

import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.model.comment.CommentBody;
import com.example.onlinestore.model.products.ProductBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WoocommerceService {

    @GET("products")
    Call<List<ProductBody>> getAmazingSuggestion(@Query("consumer_key") String consumerKey,
                                                 @Query("consumer_secret") String consumerSecret,
                                                 @Query("tag") int tagId);

    @GET("products")
    Call<List<ProductBody>> getProductByOrder(@Query("consumer_key") String consumerKey,
                                              @Query("consumer_secret") String consumerSecret,
                                              @Query("per_page") int perPage,
                                              @Query("orderby") String orderBy);

    @GET("products")
    Call<List<ProductBody>> getProductsOfSpecificCategory(
            @Query("consumer_key") String consumerKey,
            @Query("consumer_secret") String consumerSecret,
            @Query("category") int categoryId,
            @Query("per_page") int perPage,
            @Query("orderby") String orderBy);

    @GET("products/{id}")
    Call<ProductBody> getProductById(@Path("id") int id,
                                     @Query("consumer_key") String consumerKey,
                                     @Query("consumer_secret") String consumerSecret);

    @GET("products/categories")
    Call<List<CategoryBody>> getFilteredCategory(@Query("consumer_key") String consumerKey,
                                                 @Query("consumer_secret") String consumerSecret,
                                                 @Query("parent") int parentId);

    @GET("products/reviews")
    Call<List<CommentBody>> getProductReviews(@Query("product") int productId,
                                              @Query("consumer_key") String consumerKey,
                                              @Query("consumer_secret") String consumerSecret,
                                              @Query("orderby") String orderBy);

}
