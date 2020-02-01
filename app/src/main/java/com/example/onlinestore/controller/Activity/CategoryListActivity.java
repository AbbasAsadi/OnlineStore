package com.example.onlinestore.controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.fragment.CategoryListFragment;
import com.example.onlinestore.controller.SingleFragmentActivity;

public class CategoryListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CategoryListActivity.class);
        return intent;
    }
    @Override
    public Fragment getFragment() {
        return CategoryListFragment.newInstance();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_category_list;
    }

    @Override
    public int getResourceId() {
        return R.id.category_list_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
