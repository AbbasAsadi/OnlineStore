package com.example.onlinestore.controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.SingleFragmentActivity;
import com.example.onlinestore.controller.fragment.CategoryListFragment;

public class CategoryListActivity extends SingleFragmentActivity {
    public static final String CLICKED_CATEGORY_POSITION = "position";
    private int mClickedCategoryPosition;

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, CategoryListActivity.class);
        intent.putExtra(CLICKED_CATEGORY_POSITION, position);
        return intent;
    }

    @Override
    public Fragment getFragment() {
        if (getIntent().getExtras().containsKey(CLICKED_CATEGORY_POSITION))
            mClickedCategoryPosition = (int) getIntent().getExtras().get(CLICKED_CATEGORY_POSITION);
        return CategoryListFragment.newInstance(mClickedCategoryPosition);
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
