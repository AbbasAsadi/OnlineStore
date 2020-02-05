package com.example.onlinestore.utils.green_dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.onlinestore.model.products.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class GreenDaoOpenHelper extends DaoMaster.DevOpenHelper {
    public GreenDaoOpenHelper(Context context, String name) {
        super(context, name);
    }

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
