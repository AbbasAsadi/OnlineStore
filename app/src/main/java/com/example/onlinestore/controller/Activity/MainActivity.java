package com.example.onlinestore.controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.onlinestore.R;
import com.example.onlinestore.controller.SingleFragmentActivity;
import com.example.onlinestore.controller.fragment.MainFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static long sBackPressed;
    private Toolbar mTopToolbar;
    private DrawerLayout mDrawerLayout;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public Fragment getFragment() {
        return MainFragment.newInstance(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public int getResourceId() {
        return R.id.main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (sBackPressed + 1000 > System.currentTimeMillis()) super.onBackPressed();
        else
            Toast.makeText(getBaseContext(),
                    "برای خروج یکبار دیگر کلید بازگشت را بفشارید",
                    Toast.LENGTH_SHORT).show();
        sBackPressed = System.currentTimeMillis();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_home:
                //do nothing
                break;
            case R.id.activity_main_drawer_category:
                startActivity(CategoryListActivity.newIntent(this, 0));
                break;
            case R.id.activity_main_drawer_shopping_basket:
                break;
            case R.id.activity_main_drawer_Bestselling:
                //should ask
                break;
            case R.id.activity_main_drawer_special_sale:
                startActivity(ProductListActivity
                        .newIntent(this,
                                ProductListActivity.SPECIAL_SALE));
                break;
            case R.id.activity_main_drawer_Most_visited:
                startActivity(ProductListActivity
                        .newIntent(this,
                                ProductListActivity.POPULAR_PRODUCT));
                break;
            case R.id.activity_main_drawer_newest:
                startActivity(ProductListActivity
                        .newIntent(this,
                                ProductListActivity.NEWEST_PRODUCT));
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // Configure Toolbar
    private void configureToolBar() {
        this.mTopToolbar = findViewById(R.id.my_toolbar);
        mTopToolbar.setTitle(" ");
        setSupportActionBar(mTopToolbar);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout() {
        this.mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mTopToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView() {
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

}
