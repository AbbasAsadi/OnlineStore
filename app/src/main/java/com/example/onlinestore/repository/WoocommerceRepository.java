package com.example.onlinestore.repository;


import android.util.Log;

import com.example.onlinestore.App;
import com.example.onlinestore.model.categories.CategoryBody;
import com.example.onlinestore.model.comment.CommentBody;
import com.example.onlinestore.model.products.DaoSession;
import com.example.onlinestore.model.products.ProductBody;
import com.example.onlinestore.model.products.ShoppingBasketProduct;
import com.example.onlinestore.model.products.ShoppingBasketProductDao;
import com.example.onlinestore.network.RetrofitInstance;
import com.example.onlinestore.network.interfaces.WoocommerceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class WoocommerceRepository {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_552ea09e65326775023b1e8969bee5ab65a3be38";
    public static final String CONSUMER_SECRET = "cs_9d3a817430202486c59662290989d5b4c5c3c837";
    private static final int SPECIAL_SALE_CATEGORY_ID = 119;
    private static final int AMAZING_PRODUCT_TAG = 48;
    private static WoocommerceRepository sWoocommerceRepository;
    private final String TAG = "WoocommerceRepository";
    private List<ProductBody> mAmazingProducts;
    private List<ProductBody> mRecentProducts;
    private List<ProductBody> mPopularProducts;
    private List<ProductBody> mTopRatedProducts;
    private List<ProductBody> mSpecialSaleList;
    private List<ProductBody> mSearchedProducts;
    private List<ProductBody> mRelatedProducts;
    private List<CategoryBody> mParentCategoryList;
    private DaoSession mDaoSession;
    private ShoppingBasketProductDao mProductDao;


    private WoocommerceService mWoocommerceService = RetrofitInstance.getInstance(BASE_URL)
            .getRetrofit()
            .create(WoocommerceService.class);

    private WoocommerceRepository() {
        mDaoSession = App.getInstance().getDaoSession();
        mProductDao = mDaoSession.getShoppingBasketProductDao();
    }

    public static WoocommerceRepository getInstance() {
        if (sWoocommerceRepository == null)
            sWoocommerceRepository = new WoocommerceRepository();
        return sWoocommerceRepository;
    }

    public void setAmazingProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getAmazingSuggestion(CONSUMER_KEY, CONSUMER_SECRET, AMAZING_PRODUCT_TAG);
        mAmazingProducts = new ArrayList<>();
        mAmazingProducts = call.execute().body();
    }

    public void setRecentProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getProductByOrder(CONSUMER_KEY, CONSUMER_SECRET, 20, "date");
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
                .getProductByOrder(CONSUMER_KEY, CONSUMER_SECRET, 20, "popularity");
        mPopularProducts = call.execute().body();
        if (mPopularProducts == null) {
            Log.d(TAG, "setPopularProducts: list is null");
        } else {
            Log.d(TAG, "setPopularProductsSize: " + mPopularProducts.size());
        }

    }

    public void setTopRatedProducts() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getProductByOrder(CONSUMER_KEY, CONSUMER_SECRET, 20, "rating");
        mTopRatedProducts = new ArrayList<>();
        mTopRatedProducts = call.execute().body();
        if (mTopRatedProducts == null) {
            Log.d(TAG, "setBestSellingList: list is null");
        } else {
            Log.d(TAG, "setRatedProductsSize: " + mTopRatedProducts.size());
        }
    }

    public List<CategoryBody> getFilteredCategoryList(int parentId) throws IOException {
        Call<List<CategoryBody>> call = mWoocommerceService
                .getFilteredCategory(CONSUMER_KEY, CONSUMER_SECRET, parentId);

        return call.execute().body();
    }

    public void setParentCategoryList() throws IOException {
        Call<List<CategoryBody>> call = mWoocommerceService
                .getFilteredCategory(CONSUMER_KEY, CONSUMER_SECRET, 0);
        mParentCategoryList = call.execute().body();
    }

    public void setSpecialSaleList() throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService.
                getProductsOfSpecificCategory(CONSUMER_KEY,
                        CONSUMER_SECRET,
                        SPECIAL_SALE_CATEGORY_ID,
                        25,
                        "date");
        mSpecialSaleList = call.execute().body();

    }

    public int getBadgeNumber() {
        int badgeNumber = 0;
        for (ShoppingBasketProduct product : getShoppingBasketProducts()) {
            badgeNumber += product.getNumber();
        }
        return badgeNumber;
    }

    public List<ProductBody> getSpecialSaleList() {
        return mSpecialSaleList;
    }

    public List<CategoryBody> getParentCategoryList() {
        return mParentCategoryList;
    }

    public List<CommentBody> getCommentList(int id) throws IOException {
        Call<List<CommentBody>> call = mWoocommerceService
                .getProductReviews(id, CONSUMER_KEY, CONSUMER_SECRET, "date");
        return call.execute().body();
    }


    public List<ProductBody> getAmazingProducts() {
        return mAmazingProducts;
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

    public ProductBody getProductById(int id) throws IOException {
        Call<ProductBody> call = mWoocommerceService
                .getProductById(id, CONSUMER_KEY, CONSUMER_SECRET);
        return call.execute().body();
    }

    public List<ProductBody> getProductByCategoryId(int categoryId) throws IOException {
        Call<List<ProductBody>> call = mWoocommerceService
                .getProductsOfSpecificCategory(CONSUMER_KEY,
                        CONSUMER_SECRET,
                        categoryId,
                        20,
                        "date");

        return call.execute().body();
    }

    public List<ShoppingBasketProduct> getShoppingBasketProducts() {
        return mProductDao
                .loadAll();
    }

    public ShoppingBasketProduct getSpecificProductOfShoppingBasket(int productId) {
        return mProductDao
                .queryBuilder()
                .where(ShoppingBasketProductDao.Properties.ProductId.eq(productId))
                .unique();
    }

    public void insertProductInShoppingBasket(ShoppingBasketProduct product) {
        ShoppingBasketProduct existProduct =
                getSpecificProductOfShoppingBasket(product.getProductId());
        if (existProduct != null) {
            existProduct.setNumber(existProduct.getNumber());
            updateShoppingBasketProductList(existProduct);
        } else
            mProductDao.insert(product);
    }

    public void deleteFromShoppingBasket(ShoppingBasketProduct product) {
        mProductDao.delete(product);
    }

    public void updateShoppingBasketProductList(ShoppingBasketProduct updatedProduct) {
        mProductDao.update(updatedProduct);
    }
}
