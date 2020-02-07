package com.example.onlinestore.controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.SingleFragmentActivity;
import com.example.onlinestore.controller.fragment.ProductListFragment;

public class ProductListActivity extends SingleFragmentActivity {

    private static final String CATEGORY_ID = "categoryId";
    private static final String LIST_TYPE = "listType";
    public static final  String SPECIAL_SALE = "specialSale";
    public static final  String NEWEST_PRODUCT = "newestProduct";
    public static final  String TOP_RATED_PRODUCT = "topRatedProduct";
    public static final  String POPULAR_PRODUCT = "popularProduct";

    public static Intent newIntent(Context context , int categoryId) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(CATEGORY_ID, categoryId);
        return intent;
    }

    public static Intent newIntent(Context context , String listType) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(LIST_TYPE, listType);
        return intent;
    }

    @Override
    public Fragment getFragment() {
        if (getIntent().getExtras().containsKey(CATEGORY_ID)){
            int id = (int) getIntent().getExtras().get(CATEGORY_ID);
            return ProductListFragment.newInstance(id);
        }else if(getIntent().getExtras().containsKey(LIST_TYPE)) {
            String listType = (String) getIntent().getExtras().get(LIST_TYPE);
            return ProductListFragment.newInstance(listType);
        }
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_product_list;
    }

    @Override
    public int getResourceId() {
        return R.id.product_list_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
