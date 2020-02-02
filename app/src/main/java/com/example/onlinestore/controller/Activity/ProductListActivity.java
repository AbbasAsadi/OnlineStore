package com.example.onlinestore.controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.SingleFragmentActivity;
import com.example.onlinestore.controller.fragment.ProductListFragment;

public class ProductListActivity extends SingleFragmentActivity {

    private static final String CATEGORY_ID = "categoryId";

    public static Intent newIntent(Context context , int categoryId) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(CATEGORY_ID, categoryId);
        return intent;
    }
    @Override
    public Fragment getFragment() {
        int id = 0;
        if (getIntent().getExtras().containsKey(CATEGORY_ID))
            id = (int) getIntent().getExtras().get(CATEGORY_ID);
        return ProductListFragment.newInstance(id);
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
