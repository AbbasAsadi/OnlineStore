package com.example.onlinestore.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.onlinestore.R;
import com.example.onlinestore.network.ConnectivityReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoInternetConnectionActivity extends AppCompatActivity {
    private static long sBackPressed;

    @BindView(R.id.check_network_txt)
    LinearLayout checkNetworkTxt;
    @BindView(R.id.try_again_btn)
    Button tryAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);
        ButterKnife.bind(this);
        checkNetworkTxtFunction();
        tryAgainBtnFunction();
        //should be repair
    }

    private void tryAgainBtnFunction() {
        tryAgainBtn.setOnClickListener(view -> {
            if (ConnectivityReceiver.isConnected(this)){
                finish();
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context , NoInternetConnectionActivity.class);
    }
    public void openWifiSettings() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private void checkNetworkTxtFunction() {
        checkNetworkTxt.setOnClickListener(view -> openWifiSettings());
    }
    @Override
    public void onBackPressed() {

// Handle back click to close menu
        if (sBackPressed + 1000 > System.currentTimeMillis()) {
            finishAffinity();
        }
        else
            Toast.makeText(getBaseContext(),
                    "برای خروج یکبار دیگر کلید بازگشت را بفشارید",
                    Toast.LENGTH_SHORT).show();
        sBackPressed = System.currentTimeMillis();    }

}
