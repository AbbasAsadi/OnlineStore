package com.example.onlinestore.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.onlinestore.R;
import com.example.onlinestore.network.WoocommerceRepository;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GetProductsAsync productsAsync = new GetProductsAsync();
        productsAsync.execute();
    }

    private class GetProductsAsync extends AsyncTask<Void , String , Void> {
        private final WoocommerceRepository Repository = WoocommerceRepository.getInstance();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Repository.setRatedProducts();
                Repository.setPopularProducts();
                Repository.setRecentProducts();
                Repository.setCategoriesList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = MainActivity.newIntent(SplashActivity.this);
            Toast.makeText(SplashActivity.this, "خوش آمدید", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}
