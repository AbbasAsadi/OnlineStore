package com.example.onlinestore;

import android.app.Application;

import java.text.NumberFormat;
import java.util.Locale;

public class App extends Application {
    private static App mInstance;
    private NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public String getPersianNumber(double i) {
        return nf.format(i);
    }

}
