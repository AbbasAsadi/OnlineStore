package com.example.onlinestore.network;


import android.util.Log;

import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.model.products.ProductBody;
import com.example.onlinestore.network.interfaces.WoocommerceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class WoocommerceRepository {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_552ea09e65326775023b1e8969bee5ab65a3be38";
    public static final String CONSUMER_SECRET = "cs_9d3a817430202486c59662290989d5b4c5c3c837";
    private static WoocommerceRepository sWoocommerceRepository;
    private final String TAG = "WoocommerceRepository";
    private List<ProductBody> mAllProducts;
    private List<ProductBody> mRecentProducts;
    private List<ProductBody> mPopularProducts;
    private List<ProductBody> mRatedProducts;
    private List<ProductBody> mSearchedProducts;
    private List<ProductBody> mRelatedProducts;
    private List<CategoryBody> mCategoriesItems;
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

    public void setRatedProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getProductByOrder(CONSUMER_KEY, CONSUMER_SECRET, 10, "rating");
        mRatedProducts = new ArrayList<>();
        mRatedProducts = call.execute().body();
        if (mRatedProducts == null) {
            Log.d(TAG, "setCategoriesList: list is null");
        } else {
            Log.d(TAG, "setRatedProductsSize: " + mRatedProducts.size());
        }
    }


    public void setCategoriesList() throws IOException {
        Call<List<CategoryBody>> call = mWoocommerceService
                .getAllCategories(CONSUMER_KEY, CONSUMER_SECRET, 50);
        mCategoriesItems = new ArrayList<>();
        mCategoriesItems = call.execute().body();
        if (mCategoriesItems == null) {
            Log.d(TAG, "setCategoriesList: list is null");
        } else {
            Log.d(TAG, "setCategoriesListSize: " + mCategoriesItems.size());
        }
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

    public List<ProductBody> getRatedProductList() {
        return mRatedProducts;
    }

    public List<CategoryBody> getCategoriesList() {
        return mCategoriesItems;
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
