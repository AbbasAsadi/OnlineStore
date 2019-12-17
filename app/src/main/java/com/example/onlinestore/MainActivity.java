package com.example.onlinestore;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class MainActivity extends SingleFragmentActivity {
    private Toolbar mTopToolbar;


    @Override
    public Fragment getFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public int getResourceId() {
        return R.id.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopToolbar = findViewById(R.id.my_toolbar);
        mTopToolbar.setTitle(" ");
        setSupportActionBar(mTopToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
