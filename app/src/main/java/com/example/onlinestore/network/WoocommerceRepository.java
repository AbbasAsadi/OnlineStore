package com.example.onlinestore.network;


import android.util.Log;

import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.model.comment.CommentBody;
import com.example.onlinestore.model.products.ProductBody;
import com.example.onlinestore.network.interfaces.WoocommerceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoocommerceRepository {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_552ea09e65326775023b1e8969bee5ab65a3be38";
    public static final String CONSUMER_SECRET = "cs_9d3a817430202486c59662290989d5b4c5c3c837";
    public static final int AMAZING_PRODUCT_TAG = 48;
    private static WoocommerceRepository sWoocommerceRepository;
    private final String TAG = "WoocommerceRepository";
    private List<ProductBody> mAllProducts;
    private List<ProductBody> mAmazingProducts;
    private List<ProductBody> mRecentProducts;
    private List<ProductBody> mPopularProducts;
    private List<ProductBody> mTopRatedProducts;
    private List<ProductBody> mSearchedProducts;
    private List<ProductBody> mRelatedProducts;
    private List<CategoryBody> mCategoriesList;
    private List<CommentBody> mCommentList;
    private int mClickedProductId;
    private Map<String, String> mQueries = new HashMap<String, String>() {
        {
            put("consumer_key", CONSUMER_KEY);
            put("consumer_secret", CONSUMER_SECRET);
            put("per_page", "50");
        }
    };
    private WoocommerceService mWoocommerceService = RetrofitInstance.getInstance(BASE_URL)
            .getRetrofit()
            .create(WoocommerceService.class);

    private WoocommerceRepository() {
    }

    public static WoocommerceRepository getInstance() {
        if (sWoocommerceRepository == null)
            sWoocommerceRepository = new WoocommerceRepository();
        return sWoocommerceRepository;
    }

    public void setAllProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getAllProduct(CONSUMER_KEY, CONSUMER_SECRET, 100);
        mAllProducts = new ArrayList<>();
        mAllProducts = call.execute().body();
    }

    public void setAmazingProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getAmazingSuggestion(CONSUMER_KEY , CONSUMER_SECRET, AMAZING_PRODUCT_TAG);
        mAmazingProducts = new ArrayList<>();
        mAmazingProducts = call.execute().body();
    }

    public void setRecentProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getProductByOrder(CONSUMER_KEY, CONSUMER_SECRET, 10, "date");
        mRecentProducts = new ArrayList<>();
        mRecentProducts = call.execute().body();
        if (mRecentProducts == null) {
            Log.d(TAG, "setRecentProducts: list is null");
        } else {
            Log.d(TAG, "setRecentProductsSize: " + mRecentProducts.size());
        }
    }

    public void setPopularProducts() throws IOException {
        mPopularProducts = new ArrayList<>();
        Call<List<ProductBody>> call = mWoocommerceService
                .getProductByOrder(CONSUMER_KEY, CONSUMER_SECRET, 10, "popularity");
        mPopularProducts = call.execute().body();
        if (mPopularProducts == null) {
            Log.d(TAG, "setPopularProducts: list is null");
        } else {
            Log.d(TAG, "setPopularProductsSize: " + mPopularProducts.size());
        }

    }

    public void setTopRatedProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getProductByOrder(CONSUMER_KEY, CONSUMER_SECRET, 10, "rating");
        mTopRatedProducts = new ArrayList<>();
        mTopRatedProducts = call.execute().body();
        if (mTopRatedProducts == null) {
            Log.d(TAG, "setBestSellingList: list is null");
        } else {
            Log.d(TAG, "setRatedProductsSize: " + mTopRatedProducts.size());
        }
    }


    public void setCategoriesList() throws IOException {
        Call<List<CategoryBody>> call = mWoocommerceService
                .getCategories(CONSUMER_KEY, CONSUMER_SECRET , 40);
        //mCategoriesList = new ArrayList<>();
        mCategoriesList = call.execute().body();

        if (mCategoriesList == null) {
            Log.d(TAG, "setCategoriesList: list is null");
        } else {
            Log.d(TAG, "setCategoriesListSize: " + mCategoriesList.size());
        }
    }


    public List<CategoryBody> getFilteredCategoryList(int parent) {
        List<CategoryBody> subCategoryList = new ArrayList<>();
        for (CategoryBody category: mCategoriesList) {
            if (category.getParent() == parent){
                subCategoryList.add(category);
            }
        }
        return subCategoryList;
    }
    public List<CommentBody> getCommentList() {
        return mCommentList;
    }

    public void setCommentList(int id) {
        Call<List<CommentBody>> call = mWoocommerceService
                .getProductReviews(id, CONSUMER_KEY, CONSUMER_SECRET , "date");
        call.enqueue(new Callback<List<CommentBody>>() {
            @Override
            public void onResponse(Call<List<CommentBody>> call, Response<List<CommentBody>> response) {
                mCommentList = response.body();
            }

            @Override
            public void onFailure(Call<List<CommentBody>> call, Throwable t) {

            }
        });
    }

    public List<ProductBody> getAmazingProducts() {
        return mAmazingProducts;
    }

    public int getClickedProductId() {
        return mClickedProductId;
    }

    public void setClickedProductId(int clickedProductId) {
        mClickedProductId = clickedProductId;
    }

    public List<ProductBody> getNewestProductList() {
        return mRecentProducts;
    }

    public List<ProductBody> getPopularProductList() {
        return mPopularProducts;
    }

    public List<ProductBody> getTopRatedProductList() {
        return mTopRatedProducts;
    }

    public List<CategoryBody> getCategoriesList() {
        return mCategoriesList;
    }

    public List<ProductBody> getSearchedProductList() {
        return mSearchedProducts;
    }

    public List<ProductBody> getRelatedProductList() {
        return mRelatedProducts;
    }

    public ProductBody getProductById(int id) {
        for (ProductBody product : mAllProducts) {
            if (product.getId() == id)
                return product;

        }
        return null;
    }
}
