package com.example.onlinestore.controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinestore.R;
import com.example.onlinestore.repository.WoocommerceRepository;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GetProductsAsync productsAsync = new GetProductsAsync();
        productsAsync.execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private class GetProductsAsync extends AsyncTask<Void, Void, Void> {
        private final WoocommerceRepository Repository = WoocommerceRepository.getInstance();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Repository.setAmazingProducts();
                Repository.setTopRatedProducts();
                Repository.setPopularProducts();
                Repository.setRecentProducts();
                Repository.setParentCategoryList();
                Repository.setSpecialSaleList();
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
