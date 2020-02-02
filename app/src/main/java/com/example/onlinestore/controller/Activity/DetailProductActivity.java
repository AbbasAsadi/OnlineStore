package com.example.onlinestore.controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.fragment.DetailProductFragment;
import com.example.onlinestore.controller.SingleFragmentActivity;

public class DetailProductActivity extends SingleFragmentActivity {


    public static final String PRODUCT_ID = "productId";

    public static Intent newIntent(Context context , int productId) {
        Intent intent = new Intent(context, DetailProductActivity.class);
        intent.putExtra(PRODUCT_ID, productId);
        return intent;
    }

    @Override
    public Fragment getFragment() {
        int id = 0;
        if (getIntent().getExtras().containsKey(PRODUCT_ID)) {
            id = (int) getIntent().getExtras().get(PRODUCT_ID);
        }
        return DetailProductFragment.newInstance(id);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_detail_product;
    }

    @Override
    public int getResourceId() {
        return R.id.detail_product_Activity;
    }

}
