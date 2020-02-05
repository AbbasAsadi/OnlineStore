package com.example.onlinestore;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.onlinestore.network.ConnectivityReceiver;
import com.example.onlinestore.utils.green_dao.GreenDaoOpenHelper;
import com.example.onlinestore.model.products.DaoMaster;
import com.example.onlinestore.model.products.DaoSession;

import java.text.NumberFormat;
import java.util.Locale;

public class App extends Application {
    private static App sInstance;
    private DaoSession mDaoSession;

    private NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));

    public static synchronized App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GreenDaoOpenHelper daoOpenHelper = new GreenDaoOpenHelper(this , "greenDao");
        SQLiteDatabase database = daoOpenHelper.getWritableDatabase();
        mDaoSession = new DaoMaster(database).newSession();
        sInstance = this;
    }

    public String getPersianNumber(double i) {
        return nf.format(i);
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
