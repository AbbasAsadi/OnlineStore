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
import com.example.onlinestore.controller.fragment.MainFragment;
import com.example.onlinestore.controller.SingleFragmentActivity;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends SingleFragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mTopToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (back_pressed + 1000 > System.currentTimeMillis()) super.onBackPressed();
        else
            Toast.makeText(getBaseContext(),
                    "برای خروج یکبار دیگر کلید بازگشت را بفشارید",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
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
                startActivity(CategoryListActivity.newIntent(this));
                break;
            case R.id.activity_main_drawer_settings:
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar() {
        this.mTopToolbar = findViewById(R.id.my_toolbar);
        mTopToolbar.setTitle(" ");
        setSupportActionBar(mTopToolbar);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mTopToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView() {
        this.navigationView =  findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

}