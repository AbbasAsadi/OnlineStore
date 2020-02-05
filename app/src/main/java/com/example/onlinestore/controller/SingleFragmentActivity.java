package com.example.onlinestore.controller;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.onlinestore.App;
import com.example.onlinestore.controller.Activity.NoInternetConnectionActivity;
import com.example.onlinestore.network.ConnectivityReceiver;

public abstract class SingleFragmentActivity extends AppCompatActivity
implements ConnectivityReceiver.ConnectivityReceiverListener {

    private ConnectivityReceiver connectivityReceiver;

    public abstract Fragment getFragment();

    public abstract int getContentView();

    public abstract int getResourceId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        getSupportFragmentManager().beginTransaction()
                .replace(getResourceId(), getFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected){
            Intent intent = NoInternetConnectionActivity.newIntent(SingleFragmentActivity.this);
            startActivity(intent);
        }
    }
    private void checkInternetConnection() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        App.getInstance().setConnectivityListener(this);
    }
}
