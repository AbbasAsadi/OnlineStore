package com.example.onlinestore.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;

public class DetailProductActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DetailProductActivity.class);
        return intent;
    }

    @Override
    public Fragment getFragment() {
        return DetailProductFragment.newInstance();
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
